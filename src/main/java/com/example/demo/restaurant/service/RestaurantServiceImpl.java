package com.example.demo.restaurant.service;

import com.example.demo.restaurant.controller.form.RestaurantListResponseForm;
import com.example.demo.restaurant.entity.Restaurant;
import com.example.demo.restaurant.entity.RestaurantImages;
import com.example.demo.restaurant.repository.RestaurantImagesRepository;
import com.example.demo.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    final private RestaurantRepository restaurantRepository;
    final private RestaurantImagesRepository restaurantImagesRepository;

    @Override
    public List<RestaurantListResponseForm> list() {
        List<RestaurantListResponseForm> tmpList = new ArrayList<>();
        List<Restaurant> restaurants = restaurantRepository.findAll();

        for (Restaurant restaurant:restaurants){
            List<RestaurantImages> maybeImages = restaurantImagesRepository.findByRestaurantId(restaurant.getId());
            RestaurantListResponseForm responseForm = new RestaurantListResponseForm(restaurant, maybeImages.get(0).getImageResourcePath());
            tmpList.add(responseForm);
        }

        return tmpList;
    }
}
