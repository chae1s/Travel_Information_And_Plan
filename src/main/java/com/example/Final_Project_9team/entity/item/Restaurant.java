package com.example.Final_Project_9team.entity.item;

import com.example.Final_Project_9team.entity.enums.RestaurantCategory;
import jakarta.persistence.Entity;

@Entity
public class Restaurant extends Item {
    private RestaurantCategory restaurantCategory;

}
