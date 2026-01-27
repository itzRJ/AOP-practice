package com.self.AOP.practice.service;

import com.self.AOP.practice.aspects.BeforeLog;
import org.springframework.stereotype.Service;

/**
 * Service layer for handling business logic.
 * All methods are logged via AOP using @BeforeLog annotation.
 */
@Service
public class SampleService {

    @BeforeLog
    public String getData() {
        return "Sample Data from Service";
    }

    @BeforeLog
    public String greetUser(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Hello, Guest!";
        }
        return String.format("Hello, %s! Welcome to AOP Practice.", name);
    }

    @BeforeLog
    public String processData(String input) {
        // Simulate some processing
        return "Processed: " + input.toUpperCase();
    }
}
