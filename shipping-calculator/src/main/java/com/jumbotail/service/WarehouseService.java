package com.jumbotail.service;

import com.jumbotail.entity.Product;
import com.jumbotail.entity.Seller;
import com.jumbotail.entity.Warehouse;
import com.jumbotail.repository.ProductRepository;
import com.jumbotail.repository.SellerRepository;
import com.jumbotail.repository.WarehouseRepository;
import com.jumbotail.util.DistanceUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;

    public WarehouseService(WarehouseRepository warehouseRepository,
                            SellerRepository sellerRepository,
                            ProductRepository productRepository) {
        this.warehouseRepository = warehouseRepository;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
    }

    public Warehouse findNearestWarehouse(String sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found"));

        List<Warehouse> warehouses = warehouseRepository.findAll();
        if (warehouses.isEmpty()) {
            throw new IllegalStateException("No warehouses available");
        }

        Warehouse nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Warehouse w : warehouses) {
            double distance = DistanceUtils.calculateDistanceInKm(seller.getLat(), seller.getLng(), w.getLat(), w.getLng());
            if (distance < minDistance) {
                minDistance = distance;
                nearest = w;
            }
        }

        return nearest;
    }

    public Warehouse findNearestWarehouse(String sellerId, String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
                
        if (product.getSeller() == null || !product.getSeller().getId().equals(sellerId)) {
            throw new IllegalArgumentException("Product does not belong to the given seller");
        }

        return findNearestWarehouse(sellerId);
    }
}
