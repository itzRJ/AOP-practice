package com.self.AOP.practice.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the entire AOP flow.
 * Tests the full stack from controller through service with AOP aspects.
 */
@SpringBootTest
@AutoConfigureMockMvc
class AopIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void helloEndpoint_ShouldReturnDataWithAopLogging() throws Exception {
        mockMvc.perform(get("/api/v1/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Sample Data from Service"));
    }

    @Test
    void dataEndpoint_ShouldReturnDataWithAopLogging() throws Exception {
        mockMvc.perform(get("/api/v1/data"))
                .andExpect(status().isOk())
                .andExpect(content().string("Sample Data from Service"));
    }

    @Test
    void greetEndpoint_WithName_ShouldReturnPersonalizedGreeting() throws Exception {
        String name = "Rahul";
        mockMvc.perform(get("/api/v1/greet/{name}", name))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, Rahul! Welcome to AOP Practice."));
    }

    @Test
    void greetEndpoint_WithDifferentName_ShouldReturnPersonalizedGreeting() throws Exception {
        String name = "Alice";
        mockMvc.perform(get("/api/v1/greet/{name}", name))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, Alice! Welcome to AOP Practice."));
    }

    @Test
    void greetEndpoint_WithSpecialCharacters_ShouldHandleGracefully() throws Exception {
        String name = "John-Doe";
        mockMvc.perform(get("/api/v1/greet/{name}", name))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, John-Doe! Welcome to AOP Practice."));
    }

    @Test
    void allEndpoints_WithCorrelationIdHeader_ShouldProcessSuccessfully() throws Exception {
        String correlationId = "test-correlation-123";

        mockMvc.perform(get("/api/v1/hello")
                        .header("X-CorrelationId", correlationId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/data")
                        .header("X-CorrelationId", correlationId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/greet/TestUser")
                        .header("X-CorrelationId", correlationId))
                .andExpect(status().isOk());
    }
}
