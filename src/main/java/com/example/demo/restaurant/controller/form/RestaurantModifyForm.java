package com.example.demo.restaurant.controller.form;

import com.example.demo.restaurant.service.request.RestaurantModifyRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantModifyForm {

    private String restaurantName;
    private String restaurantInfo;
    private String userToken;

    public RestaurantModifyRequest toRestaurantModifyRequest() {

        return new RestaurantModifyRequest(restaurantName, restaurantInfo, userToken);
    }
}
