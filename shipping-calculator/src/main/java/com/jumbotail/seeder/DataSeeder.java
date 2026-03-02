package com.jumbotail.seeder;

import com.jumbotail.entity.Customer;
import com.jumbotail.entity.Product;
import com.jumbotail.entity.Seller;
import com.jumbotail.entity.Warehouse;
import com.jumbotail.repository.CustomerRepository;
import com.jumbotail.repository.ProductRepository;
import com.jumbotail.repository.SellerRepository;
import com.jumbotail.repository.WarehouseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final WarehouseRepository warehouseRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;

    public DataSeeder(CustomerRepository customerRepository,
                      WarehouseRepository warehouseRepository,
                      SellerRepository sellerRepository,
                      ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.warehouseRepository = warehouseRepository;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Customers
        Customer c1 = new Customer("Cust-123", "Shree Kirana Store", "9847123456", 11.232, 23.445495);
        Customer c2 = new Customer("Cust-124", "Andheri Mini Mart", "9101123456", 17.232, 33.445495);
        customerRepository.save(c1);
        customerRepository.save(c2);

        // Warehouses
        Warehouse w1 = new Warehouse("BLR_Warehouse", "BLR Warehouse", 12.99999, 37.923273);
        Warehouse w2 = new Warehouse("MUMB_Warehouse", "MUMB Warehouse", 11.99999, 27.923273);
        warehouseRepository.save(w1);
        warehouseRepository.save(w2);

        // Sellers
        Seller s1 = new Seller("Seller-1", "Nestle Seller", 12.0, 30.0);
        Seller s2 = new Seller("Seller-2", "Rice Seller", 15.0, 35.0);
        Seller s3 = new Seller("Seller-3", "Sugar Seller", 18.0, 40.0);
        
        sellerRepository.save(s1);
        sellerRepository.save(s2);
        sellerRepository.save(s3);

        // Products
        Product p1 = new Product("Prod-1", "Maggie 500g Packet", 10.0, 0.5, 10.0, 10.0, 10.0, s1);
        Product p2 = new Product("Prod-2", "Rice Bag 10Kg", 500.0, 10.0, 1000.0, 800.0, 500.0, s2);
        Product p3 = new Product("Prod-3", "Sugar Bag 25kg", 700.0, 25.0, 1000.0, 900.0, 600.0, s3);
        
        productRepository.save(p1);
        productRepository.save(p2);
        productRepository.save(p3);
        
        System.out.println("Sample Data Seeded Successfully!");
    }
}
