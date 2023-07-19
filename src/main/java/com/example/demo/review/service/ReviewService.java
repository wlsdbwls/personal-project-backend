package com.example.demo.review.service;

import com.example.demo.review.controller.form.response.ReviewListResponseForm;
import com.example.demo.review.service.request.ReviewRegisterRequest;

import java.util.List;

public interface ReviewService {
    Boolean register(ReviewRegisterRequest reviewRegisterRequest);
    List<ReviewListResponseForm> list();
}
