package com.example.demo.like.repository;

import com.example.demo.like.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByAccountIdAndRestaurantId(Long accountId, Long restaurantId);
}
