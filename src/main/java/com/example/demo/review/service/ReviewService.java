package com.example.demo.review.service;

import com.example.demo.review.service.request.ReviewRegisterRequest;

public interface ReviewService {
    Boolean register(ReviewRegisterRequest reviewRegisterRequest);
}
