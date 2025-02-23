package com.example.demo.restaurant.controller.form.business;

import com.example.demo.restaurant.entity.Restaurant;
import com.example.demo.restaurant.entity.RestaurantImages;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BusinessRestaurantReadResponseForm {

    final private Long id;
    final private String restaurantName;
    final private String restaurantInfo;
    final private List<String> restaurantImagesPathList = new ArrayList<>();

    public BusinessRestaurantReadResponseForm(Restaurant restaurant, List<RestaurantImages> restaurantImagesList) {
        this.id = restaurant.getId();
        this.restaurantName = restaurant.getRestaurantName();
        this.restaurantInfo = restaurant.getRestaurantInfo();

        for (RestaurantImages images: restaurantImagesList) {
            this.restaurantImagesPathList.add(images.getImageResourcePath());
        }
    }
}
