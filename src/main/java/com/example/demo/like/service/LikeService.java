package com.example.demo.like.service;

import com.example.demo.like.controller.form.LikeDeleteForm;
import com.example.demo.like.controller.form.LikeRestaurantForm;
import com.example.demo.like.entity.LikeEntity;
import com.example.demo.like.service.request.LikeRestaurantRequest;

public interface LikeService {
    Boolean likeRestaurant(LikeRestaurantForm likeRestaurantForm);
    void delete(Long id, LikeDeleteForm likeDeleteForm);
}
