package com.example.demo.review.controller;

import com.example.demo.review.controller.form.ReviewRegisterForm;
import com.example.demo.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    final private ReviewService reviewService;

    // 리뷰 등록
    @PostMapping("/register")
    public Boolean registerReview (@RequestBody ReviewRegisterForm registerForm) {
        log.info("registerReview()");

        return reviewService.register(registerForm.toReviewRegisterRequest());
    }
}
