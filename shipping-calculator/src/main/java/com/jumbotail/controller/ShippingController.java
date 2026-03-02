package com.jumbotail.controller;

import com.jumbotail.dto.CalculateShippingRequest;
import com.jumbotail.dto.CalculateShippingResponse;
import com.jumbotail.dto.LocationDto;
import com.jumbotail.dto.ShippingChargeResponse;
import com.jumbotail.dto.WarehouseResponse;
import com.jumbotail.entity.Warehouse;
import com.jumbotail.enums.DeliverySpeed;
import com.jumbotail.service.ShippingService;
import com.jumbotail.service.WarehouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shipping-charge")
public class ShippingController {

    private final ShippingService shippingService;
    private final WarehouseService warehouseService;

    public ShippingController(ShippingService shippingService, WarehouseService warehouseService) {
        this.shippingService = shippingService;
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public ResponseEntity<ShippingChargeResponse> getShippingCharge(
            @RequestParam String warehouseId,
            @RequestParam String customerId,
            @RequestParam String deliverySpeed,
            @RequestParam(required = false, defaultValue = "1.0") Double weight) {

        DeliverySpeed speedEnum = DeliverySpeed.valueOf(deliverySpeed.toUpperCase());
        double charge = shippingService.calculateShippingCharge(warehouseId, customerId, speedEnum, weight);

        return ResponseEntity.ok(new ShippingChargeResponse(charge));
    }

    @PostMapping("/calculate")
    public ResponseEntity<CalculateShippingResponse> calculateCombinedShippingCharge(
            @RequestBody CalculateShippingRequest request) {

        DeliverySpeed speedEnum = DeliverySpeed.valueOf(request.getDeliverySpeed().toUpperCase());
        Double weight = request.getWeight() != null ? request.getWeight() : 1.0;

        Warehouse nearestWarehouse = request.getProductId() != null 
                ? warehouseService.findNearestWarehouse(request.getSellerId(), request.getProductId())
                : warehouseService.findNearestWarehouse(request.getSellerId());

        double charge = shippingService.calculateShippingCharge(nearestWarehouse.getId(), request.getCustomerId(), speedEnum, weight);

        WarehouseResponse warehouseResponse = new WarehouseResponse(
                nearestWarehouse.getId(),
                new LocationDto(nearestWarehouse.getLat(), nearestWarehouse.getLng())
        );

        CalculateShippingResponse response = new CalculateShippingResponse(charge, warehouseResponse);
        return ResponseEntity.ok(response);
    }
}
