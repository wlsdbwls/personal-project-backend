package com.example.demo.restaurant.repository;

import com.example.demo.restaurant.entity.RestaurantFood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantFoodRepository extends JpaRepository<RestaurantFood, Long> {
}
