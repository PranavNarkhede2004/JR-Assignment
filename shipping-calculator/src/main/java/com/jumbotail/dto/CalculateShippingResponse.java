package com.jumbotail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculateShippingResponse {
    private double shippingCharge;
    private WarehouseResponse nearestWarehouse;
}
