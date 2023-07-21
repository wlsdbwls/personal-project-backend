package com.example.demo.like.service;

import com.example.demo.account.entity.Account;
import com.example.demo.account.repository.AccountRepository;
import com.example.demo.account.repository.UserTokenRepository;
import com.example.demo.account.repository.UserTokenRepositoryImpl;
import com.example.demo.like.controller.form.LikeRestaurantForm;
import com.example.demo.like.entity.LikeEntity;
import com.example.demo.like.repository.LikeRepository;
import com.example.demo.like.service.request.LikeRestaurantRequest;
import com.example.demo.restaurant.entity.Restaurant;
import com.example.demo.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{

    final private UserTokenRepository userTokenRepository = UserTokenRepositoryImpl.getInstance();
    final private AccountRepository accountRepository;
    final private RestaurantRepository restaurantRepository;
    final private LikeRepository likeRepository;

    @Override
    public Boolean likeRestaurant(LikeRestaurantForm likeRestaurantForm) {

        String userToken = likeRestaurantForm.getUserToken();
        Long accountId = userTokenRepository.findAccountIdByUserToken(userToken);
        Optional<Account> maybeAccount = accountRepository.findById(accountId);

        if (maybeAccount.isPresent()) {
            Account account = maybeAccount.get();

            Optional<Restaurant> maybeRestaurant = restaurantRepository.findById(likeRestaurantForm.getRestaurantId());

            if (maybeRestaurant.isPresent()) {
                Restaurant restaurant = maybeRestaurant.get();

                LikeEntity like = likeRestaurantForm.toLike(account, restaurant);
                likeRepository.save(like);

                return true;
            }
        }

        return false;
    }
}
