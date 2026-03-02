package com.jumbotail.service;

import com.jumbotail.enums.DeliverySpeed;
import com.jumbotail.enums.TransportMode;
import com.jumbotail.strategy.TransportPricingStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingService {

    private final List<TransportPricingStrategy> strategies;

    public PricingService(List<TransportPricingStrategy> strategies) {
        this.strategies = strategies;
    }

    public double calculateBaseShippingCost(double distanceInKm, double weightInKg) {
        TransportPricingStrategy strategy = getStrategyForDistance(distanceInKm);
        return strategy.calculateCost(distanceInKm, weightInKg);
    }

    public TransportMode getTransportMode(double distanceInKm) {
        return getStrategyForDistance(distanceInKm).getTransportMode();
    }

    private TransportPricingStrategy getStrategyForDistance(double distanceInKm) {
        return strategies.stream()
                .filter(s -> s.isApplicable(distanceInKm))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No pricing strategy found for distance: " + distanceInKm));
    }

    public double applyDeliverySpeedMarkup(double baseShippingCost, double weightInKg, DeliverySpeed speed) {
        double total = baseShippingCost + 10.0; // Rs 10 standard courier charge
        
        if (speed == DeliverySpeed.EXPRESS) {
            total += (1.2 * weightInKg); // Rs 1.2 per Kg Extra for express charge
        }
        
        return Math.round(total * 100.0) / 100.0; // Round to 2 decimal places
    }
}
