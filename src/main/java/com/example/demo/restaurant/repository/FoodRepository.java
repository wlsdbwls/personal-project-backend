package com.example.demo.restaurant.repository;

import com.example.demo.restaurant.entity.Food;
import com.example.demo.restaurant.entity.FoodType;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Map;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {
    Optional<Food> findByFoodType(FoodType foodType);
}
