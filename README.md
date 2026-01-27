# AOP Practice

A Spring Boot demo project showcasing Aspect-Oriented Programming (AOP) concepts with practical examples of logging aspects, method interception, and cross-cutting concerns.

## Description

This project demonstrates the implementation of AOP in Spring Boot applications. It includes:
- Custom logging aspects using `@Before`, `@AfterReturning`, `@AfterThrowing`, and `@Around` advice
- Custom annotation-based aspect implementation (`@BeforeLog`)
- Correlation ID tracking across requests using filters
- Method execution time monitoring
- REST API endpoints demonstrating AOP in action

## Technologies

- **Java 17**
- **Spring Boot 3.5.10**
- **Spring AOP** (AspectJ)
- **Gradle** (Build tool)
- **Logback** (Logging framework)
- **SLF4J** (Logging facade)

## Project Structure

```
src/main/java/com/self/AOP/practice/
├── AopPracticeApplication.java      # Main Spring Boot application
├── aspects/
│   ├── BeforeLog.java               # Custom annotation for method logging
│   └── LoggingAspect.java           # AOP aspect with logging advice
├── controller/
│   └── SampleController.java        # REST endpoints demonstrating AOP
├── filter/
│   └── CorrelationIdFilter.java     # Request correlation tracking
└── service/
    └── SampleService.java           # Business logic service layer
```

## Prerequisites

- Java 17 or higher
- Gradle (or use the included Gradle wrapper)

## Installation & Setup

1. Clone the repository:
```bash
git clone https://github.com/itzRJ/AOP-practice.git
cd AOP-practice
```

2. Build the project:
```bash
./gradlew clean build
```

3. Run the application:
```bash
./gradlew bootRun
```

The application will start on `http://localhost:8085`

## Usage

### Available Endpoints

Test the AOP logging functionality with these REST endpoints:

```bash
# Simple hello endpoint
curl http://localhost:8085/api/v1/hello

# Get data endpoint
curl http://localhost:8085/api/v1/data

# Personalized greeting with path variable
curl http://localhost:8085/api/v1/greet/Rahul
```

### Expected Behavior

When you call these endpoints, you'll see:
- **Method entry logs** with parameters (via `@Before` advice)
- **Method execution time** tracking (via `@Around` advice)
- **Method exit logs** with return values (via `@AfterReturning` advice)
- **Exception logs** if errors occur (via `@AfterThrowing` advice)
- **Correlation IDs** for request tracking

### Example Log Output

```
INFO  [correlationId: abc-123] Entering method: SampleController.sayHello with arguments: []
INFO  [correlationId: abc-123] Entering method: SampleService.getData with arguments: []
INFO  [correlationId: abc-123] Method getData completed successfully. Return value: Hello from Service!
INFO  [correlationId: abc-123] Method sayHello executed in 15ms
```

## Key AOP Concepts Demonstrated

### 1. Custom Annotation (`@BeforeLog`)
```java
@BeforeLog
public ResponseEntity<String> sayHello() {
    // Method will be intercepted by LoggingAspect
}
```

### 2. Aspect Advice Types
- **@Before**: Executes before method invocation
- **@AfterReturning**: Executes after successful method completion
- **@AfterThrowing**: Executes when method throws an exception
- **@Around**: Wraps method execution for full control

### 3. Cross-Cutting Concerns
- Logging
- Performance monitoring
- Request correlation tracking

## Configuration

Edit `application.properties` to customize:
```properties
server.port=8085
spring.application.name=AOP-practice
```

## Testing

Run the test suite:
```bash
./gradlew test
```

## Learning Resources

- [Spring AOP Documentation](https://docs.spring.io/spring-framework/reference/core/aop.html)
- [AspectJ Documentation](https://www.eclipse.org/aspectj/doc/released/progguide/index.html)
- [Baeldung Spring AOP Guide](https://www.baeldung.com/spring-aop)

## Project Status

Active development - This is a learning/practice project for understanding AOP concepts in Spring Boot.
