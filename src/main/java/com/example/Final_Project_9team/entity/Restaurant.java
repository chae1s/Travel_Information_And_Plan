package com.example.Final_Project_9team.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Restaurant extends Item{
    private RestaurantCategory restaurantCategory;

}
