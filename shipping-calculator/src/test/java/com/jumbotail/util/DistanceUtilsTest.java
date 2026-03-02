package com.jumbotail.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DistanceUtilsTest {

    @Test
    void testCalculateDistanceInKm() {
        // approximate distance between Bangalore (12.9716, 77.5946) and Mumbai (19.0760, 72.8777)
        double distance = DistanceUtils.calculateDistanceInKm(12.9716, 77.5946, 19.0760, 72.8777);
        // Distance should be around 845 km
        assertTrue(distance > 800 && distance < 900, "Distance should be approx 845km");
    }
}
