package com.self.AOP.practice.aspects;

import com.self.AOP.practice.service.SampleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for LoggingAspect.
 * Verifies that AOP logging is correctly applied to annotated methods.
 */
@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class LoggingAspectTest {

    @Autowired
    private SampleService sampleService;

    @Test
    void loggingAspect_ShouldLogMethodEntry(CapturedOutput output) {
        // Act
        sampleService.getData();

        // Assert
        String logOutput = output.toString();
        assertThat(logOutput).contains("Entering method:");
        assertThat(logOutput).contains("SampleService.getData");
    }

    @Test
    void loggingAspect_ShouldLogMethodReturnValue(CapturedOutput output) {
        // Act
        String result = sampleService.getData();

        // Assert
        String logOutput = output.toString();
        assertThat(logOutput).contains("completed successfully");
        assertThat(logOutput).contains("Return value:");
        assertNotNull(result);
    }

    @Test
    void loggingAspect_ShouldLogMethodWithParameters(CapturedOutput output) {
        // Act
        sampleService.greetUser("TestUser");

        // Assert
        String logOutput = output.toString();
        assertThat(logOutput).contains("Entering method:");
        assertThat(logOutput).contains("greetUser");
        assertThat(logOutput).contains("TestUser");
    }

    @Test
    void loggingAspect_ShouldLogExecutionTime(CapturedOutput output) {
        // Act
        sampleService.processData("test");

        // Assert
        String logOutput = output.toString();
        assertThat(logOutput).contains("executed in");
        assertThat(logOutput).contains("ms");
    }

    @Test
    void loggingAspect_ShouldInterceptAllAnnotatedMethods() {
        // Act
        String getData = sampleService.getData();
        String greetUser = sampleService.greetUser("Alice");
        String processData = sampleService.processData("test");

        // Assert - verify all methods return expected values
        assertNotNull(getData);
        assertNotNull(greetUser);
        assertNotNull(processData);
        assertEquals("Sample Data from Service", getData);
        assertTrue(greetUser.contains("Alice"));
        assertTrue(processData.contains("TEST"));
    }
}
