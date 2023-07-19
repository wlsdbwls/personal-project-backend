package com.example.demo.review.controller.form.response;

import com.example.demo.restaurant.entity.Restaurant;
import com.example.demo.review.entity.Review;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewListResponseForm {

    final private Long id;
    final private Float ratings;
    final private String comment;
    // Todo: 이미지 추가하기
//    private List<String> imageUrls;

    public ReviewListResponseForm(Review review) {
        this.id = review.getId();
        this.ratings = review.getRatings();
        this.comment = review.getComment();
    }
}
