package com.self.AOP.practice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SampleService.
 * Tests business logic and data transformation.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class SampleServiceTest {

    @Autowired
    private SampleService sampleService;

    @Test
    void getData_ShouldReturnExpectedString() {
        // Act
        String result = sampleService.getData();

        // Assert
        assertNotNull(result);
        assertEquals("Sample Data from Service", result);
    }

    @Test
    void greetUser_WithValidName_ShouldReturnPersonalizedGreeting() {
        // Arrange
        String name = "Rahul";

        // Act
        String result = sampleService.greetUser(name);

        // Assert
        assertNotNull(result);
        assertEquals("Hello, Rahul! Welcome to AOP Practice.", result);
    }

    @Test
    void greetUser_WithNullName_ShouldReturnGuestGreeting() {
        // Act
        String result = sampleService.greetUser(null);

        // Assert
        assertNotNull(result);
        assertEquals("Hello, Guest!", result);
    }

    @Test
    void greetUser_WithEmptyName_ShouldReturnGuestGreeting() {
        // Act
        String result = sampleService.greetUser("");

        // Assert
        assertNotNull(result);
        assertEquals("Hello, Guest!", result);
    }

    @Test
    void greetUser_WithWhitespaceName_ShouldReturnGuestGreeting() {
        // Act
        String result = sampleService.greetUser("   ");

        // Assert
        assertNotNull(result);
        assertEquals("Hello, Guest!", result);
    }

    @Test
    void processData_ShouldConvertToUpperCase() {
        // Arrange
        String input = "hello world";

        // Act
        String result = sampleService.processData(input);

        // Assert
        assertNotNull(result);
        assertEquals("Processed: HELLO WORLD", result);
    }

    @Test
    void processData_WithMixedCase_ShouldConvertToUpperCase() {
        // Arrange
        String input = "TeSt DaTa";

        // Act
        String result = sampleService.processData(input);

        // Assert
        assertNotNull(result);
        assertEquals("Processed: TEST DATA", result);
    }
}
