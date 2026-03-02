package com.jumbotail.service;

import com.jumbotail.enums.DeliverySpeed;
import com.jumbotail.enums.TransportMode;
import com.jumbotail.strategy.AeroplanePricingStrategy;
import com.jumbotail.strategy.MiniVanPricingStrategy;
import com.jumbotail.strategy.TransportPricingStrategy;
import com.jumbotail.strategy.TruckPricingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PricingServiceTest {

    private PricingService pricingService;

    @BeforeEach
    void setUp() {
        List<TransportPricingStrategy> strategies = Arrays.asList(
                new MiniVanPricingStrategy(),
                new TruckPricingStrategy(),
                new AeroplanePricingStrategy()
        );
        pricingService = new PricingService(strategies);
    }

    @Test
    void testMiniVanPricing() {
        assertEquals(TransportMode.MINI_VAN, pricingService.getTransportMode(50));
        // 50km * 2kg * 3Rs = 300
        assertEquals(300.0, pricingService.calculateBaseShippingCost(50, 2));
    }

    @Test
    void testTruckPricing() {
        assertEquals(TransportMode.TRUCK, pricingService.getTransportMode(200));
        // 200km * 5kg * 2Rs = 2000
        assertEquals(2000.0, pricingService.calculateBaseShippingCost(200, 5));
    }

    @Test
    void testAeroplanePricing() {
        assertEquals(TransportMode.AEROPLANE, pricingService.getTransportMode(600));
        // 600km * 10kg * 1Rs = 6000
        assertEquals(6000.0, pricingService.calculateBaseShippingCost(600, 10));
    }

    @Test
    void testDeliverySpeedMarkup_Standard() {
        double cost = pricingService.applyDeliverySpeedMarkup(300.0, 2.0, DeliverySpeed.STANDARD);
        // 300 + 10 = 310
        assertEquals(310.0, cost);
    }

    @Test
    void testDeliverySpeedMarkup_Express() {
        double cost = pricingService.applyDeliverySpeedMarkup(300.0, 2.0, DeliverySpeed.EXPRESS);
        // 300 + 10 + (2 * 1.2) = 312.4
        assertEquals(312.4, cost);
    }
}
