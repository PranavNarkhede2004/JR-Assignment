package com.jumbotail.controller;

import com.jumbotail.dto.LocationDto;
import com.jumbotail.dto.WarehouseResponse;
import com.jumbotail.entity.Warehouse;
import com.jumbotail.service.WarehouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/warehouse")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping("/nearest")
    public ResponseEntity<WarehouseResponse> getNearestWarehouse(
            @RequestParam String sellerId,
            @RequestParam String productId) {
        
        Warehouse nearest = warehouseService.findNearestWarehouse(sellerId, productId);
        
        WarehouseResponse response = new WarehouseResponse(
                nearest.getId(),
                new LocationDto(nearest.getLat(), nearest.getLng())
        );
        
        return ResponseEntity.ok(response);
    }
}
