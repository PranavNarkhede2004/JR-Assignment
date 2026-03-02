package com.jumbotail.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String id;
    private String name;
    private double price;
    private double weightInKg;
    private double widthInCm;
    private double lengthInCm;
    private double heightInCm;
    
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;
}
