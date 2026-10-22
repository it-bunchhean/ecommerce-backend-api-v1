#### Monolith Ecommerce Backend Development . 
project structure:

```
ecommerce/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/it_chhean/ecommerce/
│   │   │       ├── BackendEcommerceApplication.java
│   │   │       │
│   │   │       ├── common/
│   │   │       │   ├── config/
│   │   │       │   ├── exception/
│   │   │       │   ├── response/
│   │   │       │   ├── validation/
│   │   │       │   └── util/
│   │   │       │
│   │   │       ├── security/
│   │   │       │   ├── config/
│   │   │       │   ├── jwt/
│   │   │       │   ├── filter/
│   │   │       │   └── authorization/
│   │   │       │
│   │   │       ├── identity/
│   │   │       │   ├── controller/
│   │   │       │   ├── dto/
│   │   │       │   ├── service/
│   │   │       │   ├── repository/
│   │   │       │   ├── entity/
│   │   │       │   └── mapper/
│   │   │       │
│   │   │       ├── catalog/
│   │   │       │   ├── controller/
│   │   │       │   ├── dto/
│   │   │       │   ├── service/
│   │   │       │   ├── repository/
│   │   │       │   ├── entity/
│   │   │       │   └── mapper/
│   │   │       │
│   │   │       ├── cart/
│   │   │       │   ├── controller/
│   │   │       │   ├── dto/
│   │   │       │   ├── service/
│   │   │       │   ├── repository/
│   │   │       │   ├── entity/
│   │   │       │   └── mapper/
│   │   │       │
│   │   │       ├── inventory/
│   │   │       │   ├── controller/
│   │   │       │   ├── dto/
│   │   │       │   ├── service/
│   │   │       │   ├── repository/
│   │   │       │   └── entity/
│   │   │       │
│   │   │       ├── ordering/
│   │   │       │   ├── controller/
│   │   │       │   ├── dto/
│   │   │       │   ├── service/
│   │   │       │   ├── repository/
│   │   │       │   ├── entity/
│   │   │       │   └── mapper/
│   │   │       │
│   │   │       ├── payment/
│   │   │       │   ├── controller/
│   │   │       │   ├── dto/
│   │   │       │   ├── service/
│   │   │       │   ├── repository/
│   │   │       │   ├── entity/
│   │   │       │   └── gateway/
│   │   │
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       ├── application-test.yml
│   │       └── db/
│   │           └── migration/
│   │               ├── V1__create_identity_tables.sql
│   │               ├── V2__create_catalog_tables.sql
│   │               ├── V3__create_cart_tables.sql
│   │               ├── V4__create_order_tables.sql
│   │               └── V5__create_payment_tables.sql
│   │
│   └── test/
│       └── java/
│           └── com/it_chhean/ecommerce/
│               ├── identity/
│               ├── catalog/
│               ├── cart/
│               ├── inventory/
│               ├── ordering/
│               ├── payment/
│               └── architecture/
│
├── docs/
│   ├── architecture/
│   ├── api/
│   └── adr/
│
├── docker-compose.yml
├── Dockerfile
├── pom.xml
├── .env.example
├── .gitignore
└── README.md
```

#### Tech Stacks 
The tech stacks that using inside the system in order below: 

- **Application**: Modular monolith with Java and Spring Boot.
- **Security**: Spring Security, JWT, authorization, and request validation.
- **Database**: PostgreSQL with Flyway migrations and proper constraints.
- **Testing**: Unit tests, integration tests, security tests, and PostgreSQL-backed tests.
- **Containerization**: Dockerfile and Docker Compose.
- **Deployment**: Ubuntu VPS,  HTTPS, and private application/database networking.
- **CI/CD**: GitHub Actions to test, build, and deploy releases.
- **Operations**: Logs, health checks, off-site backups, and recovery testing.
- **Performance**: Measure response times and database behavior before introducing caching or additional instances.
