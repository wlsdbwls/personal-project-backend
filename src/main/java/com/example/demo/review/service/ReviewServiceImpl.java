package com.example.demo.review.service;

import com.example.demo.account.entity.Account;
import com.example.demo.account.repository.AccountRepository;
import com.example.demo.account.repository.UserTokenRepository;
import com.example.demo.account.repository.UserTokenRepositoryImpl;
import com.example.demo.restaurant.controller.form.RestaurantListResponseForm;
import com.example.demo.restaurant.entity.Restaurant;
import com.example.demo.restaurant.entity.RestaurantImages;
import com.example.demo.restaurant.repository.RestaurantRepository;
import com.example.demo.review.controller.form.response.ReviewListResponseForm;
import com.example.demo.review.entity.Review;
import com.example.demo.review.repository.ReviewRepository;
import com.example.demo.review.service.request.ReviewRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    final private UserTokenRepository userTokenRepository = UserTokenRepositoryImpl.getInstance();
    final private AccountRepository accountRepository;
    final private ReviewRepository reviewRepository;
    final private RestaurantRepository restaurantRepository;

    @Override
    public Boolean register(ReviewRegisterRequest request) {

        String userToken = request.getUserToken();
        Long accountId = userTokenRepository.findAccountIdByUserToken(userToken);
        Optional<Account> maybeAccount = accountRepository.findById(accountId);


        if (maybeAccount.isPresent()) {
            Account account = maybeAccount.get();

            Optional<Restaurant> maybeRestaurant = restaurantRepository.findByRestaurantName(request.getRestaurantName());
            if (maybeRestaurant.isPresent()) {

                Restaurant restaurant = maybeRestaurant.get();

                Float ratings = request.getRatings();
                String comment = request.getComment();

                Review review = new Review(ratings, comment);
                review.setAccount(account);
                review.setRestaurant(restaurant);
                reviewRepository.save(review);

                return true;
            }
        }

        return false;
    }

    @Override
    public List<ReviewListResponseForm> list(Long restaurantId) {
        List<ReviewListResponseForm> tmpList = new ArrayList<>();
        List<Review> reviews = reviewRepository.findAllByRestaurantId(restaurantId);

        for (Review review : reviews) {
//            List<RestaurantImages> maybeImages = restaurantImagesRepository.findByRestaurantId(restaurant.getId());
//            if (!maybeImages.isEmpty()) {
            ReviewListResponseForm responseForm = new ReviewListResponseForm(review);
            tmpList.add(responseForm);
//            }
        }

        return tmpList;
    }
}
