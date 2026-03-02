# Shipping Charge Estimator

A robust Spring Boot application for calculating delivery shipping charges in a B2B e-commerce marketplace (like Jumbotail).

## Tech Stack
- **Java 17**
- **Spring Boot 3.4** (Web, Data JPA)
- **H2 In-Memory Database**
- **Lombok**
- **JUnit 5**

## Requirements Addressed
1. **API 1**: `GET /api/v1/warehouse/nearest` - Find nearest warehouse given a `sellerId` and optionally a `productId`.
2. **API 2**: `GET /api/v1/shipping-charge` - Calculate shipping cost given `warehouseId`, `customerId`, `deliverySpeed`, and optional `weight`.
3. **API 3**: `POST /api/v1/shipping-charge/calculate` - Calculate cost given just `sellerId` and `customerId`. Finds nearest warehouse automatically.

### Application Design Principles
- **Strategy Pattern**: Used for pricing strategies based on transport mode (`MiniVanPricingStrategy`, `TruckPricingStrategy`, `AeroplanePricingStrategy`).
- **Global Exception Handler**: `@RestControllerAdvice` translates bad queries or absent records into clean JSON responses.
- **Service Layering**: Business logic separated from controllers using `PricingService`, `WarehouseService`, and `ShippingService`.
- **In-Memory Seeding**: Pre-seeds realistic dummy data mapped exactly from assignment constraints on application startup using a `CommandLineRunner`.

### Extra "Creative" B2B Enhancements
To align perfectly with the context of a Kirana B2B marketplace, the following non-breaking properties were added directly to the models:
- **`Customer`**: `storeType` (e.g., Grocery), `loyaltyPoints`
- **`Seller`**: `rating` (0 to 5.0), `isVerified`
- **`Product`**: `category` (e.g., FMCG, Grains), `minimumOrderQuantity` (for wholesale constraints)
- **`Warehouse`**: `isColdStorageAvailable`

## How To Run
```powershell
.\mvnw.cmd spring-boot:run
```

The database is populated automatically upon startup. You can test the endpoints straight after:

### Test Requests

#### 1. Nearest Warehouse API
```bash
curl -G "http://localhost:8080/api/v1/warehouse/nearest" -d "sellerId=Seller-1" -d "productId=Prod-1"
```

#### 2. Get Shipping Charge API
```bash
curl -G "http://localhost:8080/api/v1/shipping-charge" -d "warehouseId=BLR_Warehouse" -d "customerId=Cust-123" -d "deliverySpeed=standard" -d "weight=2.0"
```

#### 3. Complete Calculate API
```bash
curl -X POST http://localhost:8080/api/v1/shipping-charge/calculate \
  -H "Content-Type: application/json" \
  -d '{"sellerId":"Seller-1","customerId":"Cust-123","deliverySpeed":"express","weight":5.0}'
```

## Running Tests
Unit tests exist for Distance Calculations and Pricing Logic.
```bash
./mvnw test
```
