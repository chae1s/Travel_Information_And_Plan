package com.example.Final_Project_9team.entity.item;

import com.example.Final_Project_9team.entity.item.Item;
import jakarta.persistence.Entity;

@Entity
public class Accommodation extends Item {
    private String info;
    private Integer roomArea;
    private Integer roomCapacity;

}
