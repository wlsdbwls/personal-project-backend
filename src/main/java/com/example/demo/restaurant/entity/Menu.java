package com.example.demo.restaurant.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String menuItem;
    private Integer menuPrice;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @Setter
    private Restaurant restaurant;

    public Menu(String menuItem, Integer menuPrice) {
        this.menuItem = menuItem;
        this.menuPrice = menuPrice;
    }
}
