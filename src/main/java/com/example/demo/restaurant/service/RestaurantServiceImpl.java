package com.example.demo.restaurant.service;

import com.example.demo.account.entity.Account;
import com.example.demo.account.repository.AccountRepository;
import com.example.demo.account.repository.AccountRoleRepository;
import com.example.demo.account.repository.UserTokenRepository;
import com.example.demo.account.repository.UserTokenRepositoryImpl;
import com.example.demo.restaurant.controller.form.MenuData;
import com.example.demo.restaurant.controller.form.RestaurantListResponseForm;
import com.example.demo.restaurant.controller.form.RestaurantModifyForm;
import com.example.demo.restaurant.controller.form.RestaurantReadResponseForm;
import com.example.demo.restaurant.controller.form.business.BusinessRestaurantListResponseForm;
import com.example.demo.restaurant.controller.form.business.BusinessRestaurantReadResponseForm;
import com.example.demo.restaurant.entity.*;
import com.example.demo.restaurant.repository.*;
import com.example.demo.restaurant.service.request.RestaurantRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.account.entity.RoleType.BUSINESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    final private RestaurantRepository restaurantRepository;
    final private RestaurantImagesRepository restaurantImagesRepository;
    final private UserTokenRepository userTokenRepository = UserTokenRepositoryImpl.getInstance();
    final private AccountRepository accountRepository;
    final private AccountRoleRepository accountRoleRepository;
    final private MenuRepository menuRepository;
    final private FoodRepository foodRepository;
    final private RestaurantFoodRepository restaurantFoodRepository;

    @Override
    public List<RestaurantListResponseForm> list() {
        List<RestaurantListResponseForm> tmpList = new ArrayList<>();
        List<Restaurant> restaurants = restaurantRepository.findAll();
        for (Restaurant restaurant : restaurants) {
            List<RestaurantImages> maybeImages = restaurantImagesRepository.findByRestaurantId(restaurant.getId());
            if (!maybeImages.isEmpty()) {
                RestaurantListResponseForm responseForm = new RestaurantListResponseForm(restaurant, maybeImages.get(0).getImageResourcePath());
                tmpList.add(responseForm);
            } else {
                tmpList.add(new  RestaurantListResponseForm(restaurant, ""));
            }
        }
        return tmpList;
    }


    @Override
    public RestaurantReadResponseForm read(Long id) {

        final Optional<Restaurant> maybeRestaurant = restaurantRepository.findById(id);

        if (maybeRestaurant.isEmpty()) {
            log.info("존재하지 않는 맛집입니다.");
            return null;
        }
        final Restaurant restaurant = maybeRestaurant.get();
        log.info("restaurant:" + restaurant);

        final List<RestaurantImages> restaurantImagesList = restaurantImagesRepository.findByRestaurantId(restaurant.getId());
        log.info("restaurantImagesList: " + restaurantImagesList);

        return new RestaurantReadResponseForm(restaurant, restaurantImagesList);
    }

    @Override
    public List<BusinessRestaurantListResponseForm> businessRegisterRestaurantList(Long accountId) {
        List<BusinessRestaurantListResponseForm> businessRegisterRestaurantList = new ArrayList<>();
        List<Restaurant> restaurantList = restaurantRepository.findAllByAccountId(accountId) ;

        for (Restaurant restaurant: restaurantList ){
            List<RestaurantImages> maybeImages = restaurantImagesRepository.findByRestaurantId(restaurant.getId());

            if (!maybeImages.isEmpty()) {
                BusinessRestaurantListResponseForm responseForm = new BusinessRestaurantListResponseForm(
                        restaurant, maybeImages.get(0).getImageResourcePath());
                businessRegisterRestaurantList.add(responseForm);
            }
        }
        return businessRegisterRestaurantList;
    }

    @Override
    public BusinessRestaurantReadResponseForm businessRead(Long id) {
        final Optional<Restaurant> maybeRestaurant = restaurantRepository.findById(id);

        if (maybeRestaurant.isEmpty()) {
            log.info("존재하지 않는 맛집입니다.");
            return null;
        }
        final Restaurant restaurant = maybeRestaurant.get();
        log.info("restaurant:" + restaurant);

        final List<RestaurantImages> restaurantImagesList = restaurantImagesRepository.findByRestaurantId(restaurant.getId());
        log.info("productImagesList: " + restaurantImagesList);

        return new BusinessRestaurantReadResponseForm(restaurant, restaurantImagesList);
    }

    @Override
    public Restaurant modify(Long id, RestaurantModifyForm modifyForm) {
        Optional<Restaurant> maybeRestaurant = restaurantRepository.findById(id);

        if (maybeRestaurant.isEmpty()) {
            log.info("존재하지 않는 맛집입니다.");
            return null;
        }

        Restaurant restaurant = maybeRestaurant.get();
        restaurant.setRestaurantName(modifyForm.getRestaurantName());
        restaurant.setRestaurantInfo(modifyForm.getRestaurantInfo());

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Long findRestaurantId(String restaurantName) {
        final Optional<Restaurant> maybeRestaurant = restaurantRepository.findByRestaurantName(restaurantName);
        if (maybeRestaurant.isPresent()) {
            return maybeRestaurant.get().getId();
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        restaurantImagesRepository.deleteAllByRestaurantId(id);
        restaurantRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Boolean register(RestaurantRegisterRequest request, List<String> restaurantImageUrls) {

        String userToken = request.getUserToken();
        Long accountId = userTokenRepository.findAccountIdByUserToken(userToken);
        log.info("accountId: " + accountId);
        Optional<Account> maybeAccount = accountRepository.findById(accountId);

        if (maybeAccount.isEmpty()) {
            return false;
        }

        Account account = maybeAccount.get();
        boolean isBusinessAccount = accountRoleRepository.findRoleByAccount(account).getRoleType() == BUSINESS;
        if (!isBusinessAccount) {
            return false;
        }

        Restaurant restaurant = request.toRestaurant();
        restaurant.setAccount(account);
        restaurantRepository.save(restaurant); // 레스토랑 엔티티 저장

        // 메뉴 담아주기
        List<MenuData> menus = request.getMenus();
        for (MenuData menuData : menus) {
            Menu menu = new Menu(menuData.getMenuItem(), menuData.getMenuPrice());
            menu.setRestaurant(restaurant);
            menuRepository.save(menu);
        }

        final List<RestaurantImages> restaurantImagesList = new ArrayList<>();

        for (String imageUrl : restaurantImageUrls) {
            RestaurantImages restaurantImage = new RestaurantImages(imageUrl);
            restaurantImage.setRestaurant(restaurant); // 레스토랑 엔티티와의 관계 설정
            restaurantImagesList.add(restaurantImage);
        }

        restaurantImagesRepository.saveAll(restaurantImagesList); // 레스토랑 이미지 엔티티들 저장

        Optional<Food> maybeFood = foodRepository.findByFoodType(request.getFoodType());
        if (maybeFood.isPresent()) {
            final Food food = maybeFood.get();
            final RestaurantFood restaurantFood = new RestaurantFood(food, restaurant);
            restaurantFoodRepository.save(restaurantFood);
        }

        return true;
    }
}
