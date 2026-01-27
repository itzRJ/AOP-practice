package com.self.AOP.practice.controller;

import com.self.AOP.practice.service.SampleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for SampleController.
 * Uses MockMvc for testing REST endpoints without starting the full server.
 */
@WebMvcTest(SampleController.class)
class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SampleService sampleService;

    @Test
    void sayHello_ShouldReturnDataFromService() throws Exception {
        // Arrange
        String expectedData = "Sample Data from Service";
        when(sampleService.getData()).thenReturn(expectedData);

        // Act & Assert
        mockMvc.perform(get("/api/v1/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedData));
    }

    @Test
    void getData_ShouldReturnDataFromService() throws Exception {
        // Arrange
        String expectedData = "Sample Data from Service";
        when(sampleService.getData()).thenReturn(expectedData);

        // Act & Assert
        mockMvc.perform(get("/api/v1/data"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedData));
    }

    @Test
    void greetUser_WithValidName_ShouldReturnPersonalizedGreeting() throws Exception {
        // Arrange
        String name = "Rahul";
        String expectedGreeting = "Hello, Rahul! Welcome to AOP Practice.";
        when(sampleService.greetUser(name)).thenReturn(expectedGreeting);

        // Act & Assert
        mockMvc.perform(get("/api/v1/greet/{name}", name))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedGreeting));
    }

    @Test
    void greetUser_WithDifferentName_ShouldReturnCorrectGreeting() throws Exception {
        // Arrange
        String name = "Alice";
        String expectedGreeting = "Hello, Alice! Welcome to AOP Practice.";
        when(sampleService.greetUser(name)).thenReturn(expectedGreeting);

        // Act & Assert
        mockMvc.perform(get("/api/v1/greet/{name}", name))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedGreeting));
    }
}
