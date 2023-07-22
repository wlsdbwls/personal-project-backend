package com.example.demo.like.controller;

import com.example.demo.account.service.AccountService;
import com.example.demo.like.controller.form.LikeDeleteForm;
import com.example.demo.like.controller.form.LikeRestaurantForm;
import com.example.demo.like.entity.LikeEntity;
import com.example.demo.like.service.LikeService;
import com.example.demo.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    // 찜 해제
    @DeleteMapping("/{id}")
    public void deleteLikedRestaurant (@PathVariable("id") Long restaurantId,
                                       @RequestBody LikeDeleteForm likeDeleteForm) {
        log.info("deleteLikedRestaurant()");

        likeService.delete(restaurantId, likeDeleteForm);
    }
}
