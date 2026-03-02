package com.jumbotail.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seller {
    @Id
    private String id;
    private String name;
    private double lat;
    private double lng;

    private double rating;
    private boolean isVerified;
}
