package com.jumbotail.service;

import com.jumbotail.entity.Customer;
import com.jumbotail.entity.Warehouse;
import com.jumbotail.enums.DeliverySpeed;
import com.jumbotail.repository.CustomerRepository;
import com.jumbotail.repository.WarehouseRepository;
import com.jumbotail.util.DistanceUtils;
import org.springframework.stereotype.Service;

@Service
public class ShippingService {
    private final WarehouseRepository warehouseRepository;
    private final CustomerRepository customerRepository;
    private final PricingService pricingService;

    public ShippingService(WarehouseRepository warehouseRepository,
                           CustomerRepository customerRepository,
                           PricingService pricingService) {
        this.warehouseRepository = warehouseRepository;
        this.customerRepository = customerRepository;
        this.pricingService = pricingService;
    }

    public double calculateShippingCharge(String warehouseId, String customerId, DeliverySpeed speed, double weightInKg) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found"));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        double distance = DistanceUtils.calculateDistanceInKm(warehouse.getLat(), warehouse.getLng(), customer.getLat(), customer.getLng());
        
        double baseCost = pricingService.calculateBaseShippingCost(distance, weightInKg);
        return pricingService.applyDeliverySpeedMarkup(baseCost, weightInKg, speed);
    }
}
