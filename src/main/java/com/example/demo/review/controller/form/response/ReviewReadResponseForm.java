package com.example.demo.review.controller.form.response;

import com.example.demo.account.entity.Account;
import com.example.demo.restaurant.entity.Restaurant;
import com.example.demo.restaurant.entity.RestaurantImages;
import com.example.demo.review.entity.Review;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ReviewReadResponseForm {

    final private Long id;
    final private Float ratings;
    final private String comment;
//    final private List<String> reviewImagesPathList = new ArrayList<>();
    final private Long accountId;

    public ReviewReadResponseForm(Review review) {
        this.id = review.getId();
        this.ratings = review.getRatings();
        this.comment = review.getComment();
        this.accountId = review.getAccount().getId();

//        for (ReviewImages images: reviewImagesList) {
//            this.reviewImagesPathList.add(images.getImageResourcePath());
//        }
    }
}
