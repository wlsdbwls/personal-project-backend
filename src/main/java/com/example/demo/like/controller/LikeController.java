package com.example.demo.like.controller;

import com.example.demo.like.controller.form.LikeRestaurantForm;
import com.example.demo.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    final private LikeService likeService;

    // 찜 등록
    @PostMapping("/restaurant")
    public Boolean likeRestaurant (@RequestBody LikeRestaurantForm likeRestaurantForm) {
        log.info("likeRestaurant()");

        return likeService.likeRestaurant(likeRestaurantForm);
    }
}
