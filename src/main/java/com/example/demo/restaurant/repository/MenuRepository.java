package com.example.demo.restaurant.repository;

import com.example.demo.restaurant.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Menu m WHERE m.restaurant.id = :restaurantId")
    void deleteByRestaurantId(@Param("restaurantId") Long restaurantId);
}
