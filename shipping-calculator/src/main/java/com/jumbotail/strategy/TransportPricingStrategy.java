package com.jumbotail.strategy;

import com.jumbotail.enums.TransportMode;

public interface TransportPricingStrategy {
    boolean isApplicable(double distanceInKm);
    double calculateCost(double distanceInKm, double weightInKg);
    TransportMode getTransportMode();
}
