package com.jumbotail.strategy;

import com.jumbotail.enums.TransportMode;
import org.springframework.stereotype.Component;

@Component
public class MiniVanPricingStrategy implements TransportPricingStrategy {
    @Override
    public boolean isApplicable(double distanceInKm) {
        return distanceInKm >= 0 && distanceInKm <= 100;
    }

    @Override
    public double calculateCost(double distanceInKm, double weightInKg) {
        return 3.0 * distanceInKm * weightInKg;
    }

    @Override
    public TransportMode getTransportMode() {
        return TransportMode.MINI_VAN;
    }
}
