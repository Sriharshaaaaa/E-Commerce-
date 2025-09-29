# Multi-Vendor E-Commerce Platform

## Overview

This is a robust, scalable e-commerce platform backend developed using Spring Boot. This project provides a complete Java-based solution for managing an online marketplace, including user authentication, product catalog, order processing, payment integrations, seller management, and administrative controls. The application leverages modern Java technologies to ensure high performance, security, and maintainability.

The project started on 9/7/2025 and is designed to support a full-fledged e-commerce ecosystem with features like JWT-based authentication, multi-role user management (customers, sellers, admins), real-time payment processing via Razorpay and Stripe, and comprehensive order tracking.

## Project Structure

```
project/
├── .gitattributes
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── zee/
│   │   │           ├── Application.java
│   │   │           ├── comfig/
│   │   │           │   ├── AppConfig.java
│   │   │           │   ├── JWT_CONSTANT.java
│   │   │           │   ├── JwtProvider.java
│   │   │           │   └── JwtTokenValidator.java
│   │   │           ├── controller/
│   │   │           │   ├── AdminController.java
│   │   │           │   ├── AdminCouponController.java
│   │   │           │   ├── AuthController.java
│   │   │           │   ├── CartController.java
│   │   │           │   ├── DealController.java
│   │   │           │   ├── HomeCategoryController.java
│   │   │           │   ├── HomeController.java
│   │   │           │   ├── OrderController.java
│   │   │           │   ├── PaymentController.java
│   │   │           │   ├── ProductController.java
│   │   │           │   ├── ReviewController.java
│   │   │           │   ├── SellerController.java
│   │   │           │   ├── SellerOrderController.java
│   │   │           │   ├── SellerProductController.java
│   │   │           │   ├── TransactionController.java
│   │   │           │   ├── UserController.java
│   │   │           │   └── WishListController.java
│   │   │           ├── dto/
│   │   │           │   ├── AccountStatus.java
│   │   │           │   ├── HomeCatagorySection.java
│   │   │           │   ├── OrderStatus.java
│   │   │           │   ├── PaymentMethod.java
│   │   │           │   ├── PaymentOrderStatus.java
│   │   │           │   ├── PaymentStatus.java
│   │   │           │   └── UserRole.java
│   │   │           ├── exceptions/
│   │   │           │   ├── ErrorDetails.java
│   │   │           │   ├── GlobalException.java
│   │   │           │   ├── ProductException.java
│   │   │           │   └── SellerException.java
│   │   │           ├── model/
│   │   │           │   ├── Address.java
│   │   │           │   ├── BankDetails.java
│   │   │           │   ├── BuisnessDetails.java
│   │   │           │   ├── Cart.java
│   │   │           │   ├── CartItem.java
│   │   │           │   ├── Category.java
│   │   │           │   ├── Coupon.java
│   │   │           │   ├── Deal.java
│   │   │           │   ├── Home.java
│   │   │           │   ├── HomeCatagory.java
│   │   │           │   ├── Order.java
│   │   │           │   ├── OrderItem.java
│   │   │           │   ├── PaymentDetails.java
│   │   │           │   ├── PaymentOrder.java
│   │   │           │   ├── Product.java
│   │   │           │   ├── Review.java
│   │   │           │   ├── Seller.java
│   │   │           │   ├── SellerReport.java
│   │   │           │   ├── Transaction.java
│   │   │           │   ├── User.java
│   │   │           │   └── VarificationCode.java
│   │   │           ├── repository/
│   │   │           │   ├── AddressRepository.java
│   │   │           │   ├── CartItemRepository.java
│   │   │           │   ├── CartRespository.java
│   │   │           │   ├── CategoryRepository.java
│   │   │           │   ├── CouponRepository.java
│   │   │           │   ├── DealRepository.java
│   │   │           │   ├── HomeCategoryRepository.java
│   │   │           │   ├── OrderItemRepository.java
│   │   │           │   ├── OrderRepository.java
│   │   │           │   ├── PaymentOrderRepository.java
│   │   │           │   ├── ProductRepository.java
│   │   │           │   ├── ReviewRepository.java
│   │   │           │   ├── SellerReportRepository.java
│   │   │           │   ├── SellerRepository.java
│   │   │           │   ├── TransactionRepository.java
│   │   │           │   ├── UserRepository.java
│   │   │           │   └── VerificationCodeRepository.java
│   │   │           ├── request/
│   │   │           │   ├── AddItemRequest.java
│   │   │           │   ├── CreateProductRequest.java
│   │   │           │   ├── CreateReviewRequest.java
│   │   │           │   ├── LoginOtpRequest.java
│   │   │           │   ├── LonginRequest.java
│   │   │           │   └── SighnupRequest.java
│   │   │           ├── response/
│   │   │           │   ├── ApiResponse.java
│   │   │           │   ├── AuthResponse.java
│   │   │           │   └── PaymentLinkResponse.java
│   │   │           ├── service/
│   │   │           │   ├── AuthService.java
│   │   │           │   ├── CartItemService.java
│   │   │           │   ├── CartService.java
│   │   │           │   ├── CouponService.java
│   │   │           │   ├── DealService.java
│   │   │           │   ├── HomeCategorService.java
│   │   │           │   ├── HomeService.java
│   │   │           │   ├── OrderService.java
│   │   │           │   ├── PaymentService.java
│   │   │           │   ├── ProductService.java
│   │   │           │   ├── ReviewService.java
│   │   │           │   ├── SellerReportService.java
│   │   │           │   ├── SellerService.java
│   │   │           │   ├── TransactionService.java
│   │   │           │   └── UserService.java
│   │   │           ├── service/impl/
│   │   │           │   ├── authServiceImpl.java
│   │   │           │   ├── CartItemServiceImpl.java
│   │   │           │   ├── CartServiceImpl.java
│   │   │           │   ├── CouponServiceImpl.java
│   │   │           │   ├── CustomUserServiceImpl.java
│   │   │           │   ├── DealServiceImpl.java
│   │   │           │   ├── EmailService.java
│   │   │           │   ├── HomeCategoryServiceImpl.java
│   │   │           │   ├── HomeServiceImpl.java
│   │   │           │   ├── OrderServiceImpl.java
│   │   │           │   ├── PaymentServiceImpl.java
│   │   │           │   ├── ProductServiceImpl.java
│   │   │           │   ├── ReviewServiceImpl.java
│   │   │           │   ├── SellerReportServiceImpl.java
│   │   │           │   ├── SellerServiceImpl.java
│   │   │           │   ├── TransactionServiceImpl.java
│   │   │           │   └── UserServiceImpl.java
│   │   │           ├── util/
│   │   │           │   └── OtpUtil.java
│   │   │           └── Wishlist.java
│   │   └── resources/
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/
│           └── com/
│               └── zee/
│                   └── ApplicationTests.java
└── target/
```

## Features

This platform offers a wide range of features essential for an e-commerce platform:

- **User Authentication and Authorization**: Secure login/signup with OTP verification, JWT token-based authentication, and role-based access control (Customer, Seller, Admin).
- **User Management**: Profile management, address handling, and user verification.
- **Product Catalog Management**: CRUD operations for products, advanced search and filtering (by category, brand, price, discount, etc.), pagination, and inventory tracking.
- **Shopping Cart and Wishlist**: Add/remove items, manage cart contents, and save favorite products.
- **Order Processing and Management**: Place orders, track order status, handle order items, and manage order history.
- **Payment Integration**: Secure payment processing using Razorpay and Stripe, with support for multiple payment methods and status tracking.
- **Seller Management**: Seller onboarding, product management for sellers, seller reports, and order handling for sellers.
- **Review and Rating System**: User reviews and ratings for products.
- **Coupon and Deal Management**: Admin-controlled coupons and deals for promotions.
- **Admin Controls**: Administrative oversight for users, products, orders, coupons, and platform analytics.
- **Transaction Tracking**: Detailed transaction logs for payments and orders.
- **Email Notifications**: Automated emails for order confirmations, OTPs, and updates using Spring Boot Mail.
- **Home Categories and Deals**: Dynamic home page content management for categories and featured deals.

## Tech Stack

- **Java**: Version 17
- **Spring Boot**: Version 3.5.5 (for rapid application development and microservices support)
- **Spring Data JPA**: For ORM and database interactions
- **Spring Security**: For authentication and authorization
- **Spring Web**: For building RESTful APIs
- **Database**: MySQL (with MySQL Connector/J)
- **Lombok**: For reducing boilerplate code
- **JWT (JJWT)**: For secure token-based authentication
- **Payment Gateways**: Razorpay (v1.4.6) and Stripe (v26.12.0) for payment processing
- **Validation**: Spring Boot Starter Validation for input validation
- **Mail**: Spring Boot Starter Mail for email services
- **Build Tool**: Maven
- **Testing**: Spring Boot Starter Test with JUnit

## Prerequisites

Before running the application, ensure you have the following installed:

- **Java**: JDK 17 or higher
- **Maven**: Version 3.6 or higher
- **MySQL**: Version 8.0 or higher (for database)
- **Git**: For cloning the repository
- **IDE**: IntelliJ IDEA, Eclipse, or VS Code (recommended for development)

## Installation

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/your-username/your-repo.git
   cd your-repo
   ```

2. **Configure the Database**:

   - Create a MySQL database (e.g., `ecommerce_db`).
   - Update the database configuration in `src/main/resources/application.properties` (or `application.yml` if using YAML):
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
     spring.datasource.username=your-username
     spring.datasource.password=your-password
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.show-sql=true
     ```

3. **Configure Payment Gateways** (Optional for full functionality):

   - Obtain API keys from Razorpay and Stripe.
   - Add them to `application.properties`:
     ```properties
     razorpay.api.key=your-razorpay-key
     razorpay.api.secret=your-razorpay-secret
     stripe.api.key=your-stripe-key
     ```

4. **Install Dependencies**:
   ```bash
   mvn clean install
   ```

## Running the Application

1. **Build the Project**:

   ```bash
   mvn clean compile
   ```

2. **Run the Application**:

   - Using Maven:
     ```bash
     mvn spring-boot:run
     ```
   - Or, package and run the JAR:
     ```bash
     mvn clean package
     java -jar target/ecommerce-platform-0.0.1-SNAPSHOT.jar
     ```

3. **Access the Application**:
   - The application will start on `http://localhost:8080` by default.
   - Use tools like Postman or Swagger UI (if configured) to interact with the APIs.

## API Documentation

The application exposes a comprehensive RESTful API for all e-commerce operations. Below is a detailed list of all endpoints grouped by functionality. Most endpoints require authentication via JWT token in the `Authorization` header. Roles include CUSTOMER, SELLER, and ADMIN, with access restricted based on roles.

### Authentication

- `POST /auth/signup`: Register a new user (body: SighnupRequest)
- `POST /auth/signin`: User login (body: LonginRequest)
- `POST /auth/sent/login-signup-otp`: Send OTP for login/signup (body: LoginOtpRequest)

### User Management

- `GET /users/profile`: Get authenticated user's profile (header: Authorization)

### Product Management

- `GET /products/{productId}`: Get product by ID
- `GET /products/search`: Search products (query param: query)
- `GET /products`: Get all products with filters (query params: category, brand, color, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber)

### Cart Management

- `GET /api/cart`: Get authenticated user's cart (header: Authorization)
- `PUT /api/cart/add`: Add item to cart (body: AddItemRequest, header: Authorization)
- `DELETE /api/cart/item/{cartItemId}`: Remove item from cart (header: Authorization)
- `PUT /api/cart/item/{cartItemId}`: Update cart item quantity (body: CartItem, header: Authorization)

### Wishlist Management

- `GET /api/wishlist`: Get authenticated user's wishlist (header: Authorization)
- `POST /api/wishlist/add-product/{productId}`: Add product to wishlist (header: Authorization)

### Order Management

- `POST /api/orders`: Create order (body: Address, query param: paymentMethod, header: Authorization)
- `GET /api/orders/user`: Get authenticated user's order history (header: Authorization)
- `GET /api/orders/{orderId}`: Get order by ID (header: Authorization)
- `GET /api/orders/item/{orderItemId}`: Get order item by ID (header: Authorization)
- `PUT /api/orders/{orderId}/cancel`: Cancel order (header: Authorization)

### Payment Management

- `GET /api/payment/{paymentId}`: Handle payment success (query params: paymentLinkedId, header: Authorization)

### Seller Management

- `POST /sellers/login`: Seller login (body: LonginRequest)
- `PATCH /sellers/verify/{otp}`: Verify seller email with OTP
- `POST /sellers`: Create new seller (body: Seller)
- `GET /sellers/{id}`: Get seller by ID
- `GET /sellers/profile`: Get authenticated seller's profile (header: Authorization)
- `GET /sellers/report`: Get authenticated seller's report (header: Authorization)
- `GET /sellers`: Get all sellers (query param: status)
- `PATCH /sellers`: Update authenticated seller's details (body: Seller, header: Authorization)
- `DELETE /sellers/{id}`: Delete seller by ID

### Seller Product Management

- `GET /sellers/products`: Get products by authenticated seller (header: Authorization)
- `POST /sellers/products`: Create new product (body: CreateProductRequest, header: Authorization)
- `DELETE /sellers/products/{productId}`: Delete product by ID
- `PUT /sellers/products/{productId}`: Update product (body: Product)

### Seller Order Management

- `GET /api/seller/orders`: Get orders for authenticated seller (header: Authorization)
- `PATCH /api/seller/orders/{orderId}/status/{orderStatus}`: Update order status (header: Authorization)

### Review Management

- `GET /api/products/{productId}/review`: Get reviews for a product
- `POST /api/products/{productId}/review`: Write a review (body: CreateReviewRequest, header: Authorization)
- `PATCH /api/reviews/{reviewId}`: Update review (body: CreateReviewRequest, header: Authorization)
- `DELETE /api/revviews/{reviewId}`: Delete review (header: Authorization) [Note: Typo in endpoint, should be /reviews]

### Coupon Management

- `POST /api/coupon/apply`: Apply or remove coupon (query params: apply, code, orderValue, header: Authorization)
- `POST /api/coupon/admin/create`: Create coupon (admin only, body: Coupon)
- `DELETE /api/coupon/admin/delete/{id}`: Delete coupon (admin only)
- `GET /api/coupon/admin/all`: Get all coupons (admin only)

### Deal Management

- `POST /admin/deals`: Create deal (admin only, body: Deal)
- `PATCH /admin/deals/{id}`: Update deal (admin only, body: Deal)
- `DELETE /admin/deals/{id}`: Delete deal (admin only)

### Home Category Management

- `POST /home/categories`: Create home categories (body: List<HomeCatagory>)
- `GET /admin/home-category`: Get all home categories (admin only)
- `PATCH /admin/home-category/{id}`: Update home category (admin only, body: HomeCatagory)

### Transaction Management

- `GET /api/transactions/seller`: Get transactions for authenticated seller (header: Authorization)
- `GET /api/transactions`: Get all transactions (admin only)

### Admin Management

- `PATCH /api/seller/{id}/status/{status}`: Update seller account status (admin only)

### Home

- `GET /`: Welcome message for the application

**Notes**:

- Authentication: Use JWT tokens in the `Authorization` header for protected endpoints.
- Roles: Access is role-based (CUSTOMER, SELLER, ADMIN).
- Request/Response: Most endpoints use JSON for request bodies and responses.
- Pagination: Product listing supports pagination with `pageNumber`.
- Filters: Product search supports multiple filters like category, price range, etc.
- Payment Methods: Supported methods include RAZORPAY, STRIPE, and COD (Cash on Delivery).
- For full API specs, use Postman or integrate Swagger UI if configured.

## Database Configuration

- **Database**: MySQL (configured via MySQL Connector/J)
- **ORM**: JPA with Hibernate (for object-relational mapping and schema generation)
- **Schema**: Auto-generated based on JPA entities in the `com.zee.model` package. Tables are created/updated automatically using `spring.jpa.hibernate.ddl-auto=update` in development. In production, use `validate` or `none` and manage migrations manually (e.g., via Flyway or Liquibase).

### Key Entities and Relationships

The database schema is derived from the following main entities, which represent the core data models for the e-commerce platform. Relationships are defined using JPA annotations (e.g., @OneToMany, @ManyToOne, @ManyToMany). Below is a summary of key entities, their primary fields, and relationships:

#### User

- **Table**: `user`
- **Fields**:
  - `id` (Long, @Id, auto-generated)
  - `password` (String, write-only for security)
  - `email` (String, unique)
  - `fullName` (String)
  - `mobile` (String)
  - `role` (UserRole enum: ROLE_CUSTOMER, ROLE_SELLER, ROLE_ADMIN)
- **Relationships**:
  - One-to-Many: `addresses` (Set<Address>) – User's shipping/billing addresses
  - Many-to-Many: `usedCoupons` (Set<Coupon>) – Coupons applied by the user (self-referential for usage tracking)
- **Purpose**: Represents customers, sellers, and admins with role-based access.

#### Product

- **Table**: `product`
- **Fields**:
  - `id` (Long, @Id, auto-generated)
  - `title` (String)
  - `description` (String)
  - `mrpPrice` (int) – Maximum Retail Price
  - `sellingPrice` (int)
  - `discountPercent` (int)
  - `quantity` (int) – Stock availability
  - `color` (String)
  - `images` (List<String>, @ElementCollection) – Product images stored as a separate table (`product_images`)
  - `numRatings` (int)
  - `createdAt` (LocalDateTime)
  - `sizes` (String, comma-separated e.g., "S,M,L")
- **Relationships**:
  - Many-to-One: `category` (Category) – Product category
  - Many-to-One: `seller` (Seller) – Owning seller (cascade delete on seller removal)
  - One-to-Many: `reviews` (List<Review>, bidirectional, cascade all, orphan removal) – Product reviews
- **Purpose**: Core product catalog with inventory and pricing details.

#### Order

- **Table**: `order` (inferred from OrderService and controllers; full entity details include order items, status, etc.)
- **Fields** (based on usage):
  - `id` (Long, @Id)
  - `orderItems` (Set<OrderItem>)
  - `totalSellingPrice` (calculated)
  - `orderStatus` (OrderStatus enum)
  - `shippingAddress` (Address)
  - `paymentOrder` (PaymentOrder)
  - `sellerId` (Long, reference to Seller)
- **Relationships**:
  - One-to-Many: `orderItems` (OrderItem)
  - Many-to-One: `user` (User)
  - Many-to-One: `seller` (Seller)
  - One-to-One: `paymentOrder` (PaymentOrder)
- **Purpose**: Represents user purchases, linking products, payments, and shipping.

#### Cart

- **Table**: `cart`
- **Fields**:
  - `id` (Long, @Id)
  - `user` (User)
- **Relationships**:
  - One-to-Many: `cartItems` (CartItem)
  - Many-to-One: `user` (User)
- **Purpose**: Temporary shopping basket for users.

#### CartItem

- **Table**: `cart_item`
- **Fields**:
  - `id` (Long, @Id)
  - `quantity` (int)
  - `size` (String)
- **Relationships**:
  - Many-to-One: `cart` (Cart)
  - Many-to-One: `product` (Product)
- **Purpose**: Individual items in a user's cart.

#### Seller

- **Table**: `seller`
- **Fields**:
  - `id` (Long, @Id)
  - `email` (String)
  - `fullName` (String)
  - `mobile` (String)
  - `status` (AccountStatus enum)
  - `businessDetails` (BuisnessDetails, embedded)
  - `bankDetails` (BankDetails, embedded)
- **Relationships**:
  - One-to-Many: `products` (Product, bidirectional)
  - One-to-One: `sellerReport` (SellerReport)
- **Purpose**: Vendor accounts for product sellers.

#### PaymentOrder

- **Table**: `payment_order`
- **Fields**:
  - `id` (Long, @Id)
  - `amount` (double)
  - `paymentLinkId` (String)
  - `paymentStatus` (PaymentStatus enum)
- **Relationships**:
  - One-to-Many: `orders` (Order)
  - Many-to-One: `user` (User)
- **Purpose**: Handles payment transactions via Razorpay/Stripe/COD.

#### Other Key Entities

- **Address**: Shipping/billing details (street, city, state, zipCode, country, type enum).
- **Category**: Product categories (id, name).
- **Review**: User ratings/reviews (id, reviewText, reviewRating, createdAt; linked to User and Product).
- **Coupon**: Promo codes (id, code, discountAmount, expiryDate; linked to users via usedCoupons).
- **Deal**: Promotional deals (id, title, description, discountPercent, expiryDate).
- **Transaction**: Payment logs (id, amount, status; linked to Order and Seller).
- **HomeCatagory**: Homepage sections (id, title, products list).
- **SellerReport**: Seller analytics (totalOrders, totalSales, totalEarnings, cancelledOrders, totalRefunds).
- **VerificationCode**: OTP storage (id, otp, email).

**Notes**:

- All entities use Lombok for getters/setters and JPA annotations for persistence.
- Relationships ensure referential integrity (e.g., cascading deletes for products on seller removal).
- Indexes: Auto-generated on foreign keys; add custom indexes for performance (e.g., on email, product title).
- Ensure the database is running and accessible. Update `application.properties` with your MySQL credentials:
  ```
  spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db?createDatabaseIfNotExist=true
  spring.datasource.username=your-username
  spring.datasource.password=your-password
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=true
  spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
  ```
- For production, export the schema using tools like MySQL Workbench and manage versions.

## Security

- **Authentication**: JWT-based with custom provider (JwtProvider.java).
- **Authorization**: Role-based access control using Spring Security.
- **Password Handling**: Secure password encoding.
- **OTP Verification**: For signup/login via email.
- **Constants**: JWT configuration in JwtConstant.java.

## Testing

- Run unit and integration tests using Maven:
  ```bash
  mvn test
  ```
- Tests are located in `src/test/java/` and cover services, controllers, and repositories.

## Contributing

We welcome contributions! To contribute:

1. Fork the repository.
2. Create a feature branch: `git checkout -b feature/your-feature`.
3. Commit your changes: `git commit -m 'Add your feature'`.
4. Push to the branch: `git push origin feature/your-feature`.
5. Open a Pull Request.

Please follow Java coding standards and include tests for new features.

## License

This project is licensed under the MIT License. See the LICENSE file for details (if available).

## Contact

For questions or support, please contact the development team or open an issue on GitHub.

---

**Note**: This README is based on the current state of the codebase as of the analysis. Ensure to update configurations and dependencies as needed for production deployment.
# E-Commerce-
