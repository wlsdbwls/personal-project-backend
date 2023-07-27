package com.example.demo.restaurant.repository;

import com.example.demo.restaurant.entity.RestaurantFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RestaurantFoodRepository extends JpaRepository<RestaurantFood, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM RestaurantFood rf WHERE rf.restaurant.id = :restaurantId")
    void deleteByRestaurantId(@Param("restaurantId") Long restaurantId);
}
