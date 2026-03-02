package com.jumbotail.strategy;

import com.jumbotail.enums.TransportMode;
import org.springframework.stereotype.Component;

@Component
public class TruckPricingStrategy implements TransportPricingStrategy {
    @Override
    public boolean isApplicable(double distanceInKm) {
        return distanceInKm > 100 && distanceInKm <= 500;
    }

    @Override
    public double calculateCost(double distanceInKm, double weightInKg) {
        return 2.0 * distanceInKm * weightInKg;
    }

    @Override
    public TransportMode getTransportMode() {
        return TransportMode.TRUCK;
    }
}
