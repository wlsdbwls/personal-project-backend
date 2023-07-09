package com.example.demo.restaurant.service;

import com.example.demo.restaurant.controller.form.RestaurantListResponseForm;

import java.util.List;

public interface RestaurantService {
    List<RestaurantListResponseForm> list();
}
