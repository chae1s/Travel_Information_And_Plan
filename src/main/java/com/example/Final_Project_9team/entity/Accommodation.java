package com.example.Final_Project_9team.entity;

import jakarta.persistence.Entity;

@Entity
public class Accommodation extends Item{
    private String info;
    private Integer roomArea;
    private Integer roomCapacity;

}
