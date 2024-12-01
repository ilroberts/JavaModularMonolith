# Java Modular Monolith with Spring and JMolecules

Welcome to the **Java Modular Monolith** repository! This project demonstrates the implementation of a modular monolith architecture using the Spring framework. It also incorporates **JMolecules**, a framework that enforces domain-driven design (DDD) concepts through a set of annotations and patterns.

## Features

- **Modular Monolith Architecture**: A single deployable unit structured into distinct feature modules.
- **Domain-Driven Design (DDD)**: Leveraging JMolecules to model the domain effectively.
- **Spring Framework**: A robust foundation for building enterprise-grade applications.
- **Separation of Concerns**: Feature modules are designed to encapsulate their respective domains.
- **Automated Modularity Validation**: Ensures that the modular boundaries are maintained and documented.

## Project Structure

The repository is organized as follows:

- **Feature Modules**: Each module represents a specific business domain and contains its own domain, application, and infrastructure layers. Examples include:
  - `orders`: Manages orders and their lifecycles.
  - `customers`: Handles customer-related functionality.
  - `products`: Encapsulates product-related operations.
- **Shared Code**: Any shared logic or abstractions, such as domain primitives or utility functions, are isolated for reuse across modules.

## Technology Stack

- **Java**: The primary programming language.
- **Spring Framework**: For dependency injection, web layer, and transaction management.
- **JMolecules**: For DDD-compliant patterns and annotations.
- **Spring Modulith**: For enforcing modularity, visualizing module structure, and generating module documentation.
- **H2 Database**: For demonstration purposes (can be replaced with any relational database).
- **JUnit 5**: For unit and integration testing.

## Modularity Validation

The project includes a dedicated test class, `SpringModulithTests.java`, to validate and document the modular architecture:

- **Validation of Modularity**: Ensures that module boundaries are respected and inter-module dependencies adhere to defined constraints.
- **Module Structure Visualization**: Prints a detailed structure of all modules, highlighting their dependencies.
- **Documentation Generation**: Automatically generates documentation for the solution's modular architecture, aiding in maintainability and onboarding.

To run the tests, use the following command:
```bash
mvn test
```

You can find the results of the modularity validation and documentation under the test outputs or logs. This functionality is powered by [Spring Modulith](https://spring.io/projects/spring-modulith).

## Prerequisites

- **Java 17+**
- **Maven 3.8+**
- **Git**

## Getting Started

1. **Clone the repository:**
   ```bash
   git clone https://github.com/ilroberts/JavaModularMonolith.git
   cd JavaModularMonolith
   ```

2. **Build the project:**
   ```bash
   mvn clean install
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application:**
   Open your browser and navigate to `http://localhost:8080`.

## Key Concepts

### Modular Monolith

A modular monolith combines the simplicity of a monolithic application with the logical separation found in microservices. This approach allows:

- Easier testing and debugging compared to distributed systems.
- Gradual transition to microservices if needed.

### JMolecules Integration

[JMolecules](https://jmolecules.org/) enhances DDD in Java by:

- Providing annotations for Entities, Value Objects, and Aggregates.
- Promoting a clear and consistent domain model.

## Example Feature Modules

### Orders Module

- Manages order creation, updates, and queries.
- Demonstrates the use of aggregates and domain events.

### Customers Module

- Handles customer registration and management.
- Uses value objects for encapsulating customer details.

### Products Module

- Encapsulates product catalog functionality.
- Demonstrates repository and domain-driven persistence patterns.

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a feature branch.
3. Commit your changes with clear messages.
4. Submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

## Acknowledgments

- [Spring Framework](https://spring.io/)
- [JMolecules](https://jmolecules.org/)
- [Spring Modulith](https://spring.io/projects/spring-modulith)
- [H2 Database](https://www.h2database.com/)
- [JUnit 5](https://junit.org/junit5/)

