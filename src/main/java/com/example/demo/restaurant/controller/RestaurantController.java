package com.example.demo.restaurant.controller;

import com.example.demo.restaurant.controller.form.RestaurantListResponseForm;
import com.example.demo.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    final private RestaurantService restaurantService;

    // 음식점 리스트 띄우기
    @GetMapping("/list")
    public List<RestaurantListResponseForm> restaurantList() {
        log.info("restaurantList()");

        List<RestaurantListResponseForm> restaurantList = restaurantService.list();

        return restaurantList;
    }
}
