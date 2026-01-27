package com.self.AOP.practice.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CorrelationIdFilter.
 * Tests correlation ID extraction, generation, and MDC management.
 */
@ExtendWith(MockitoExtension.class)
class CorrelationIdFilterTest {

    private CorrelationIdFilter filter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @BeforeEach
    void setUp() {
        filter = new CorrelationIdFilter();
        MDC.clear();
    }

    @AfterEach
    void tearDown() {
        MDC.clear();
    }

    @Test
    void doFilter_WithExistingCorrelationId_ShouldUseProvidedId() throws IOException, ServletException {
        // Arrange
        String expectedCorrelationId = "test-correlation-id-123";
        when(request.getHeader("X-CorrelationId")).thenReturn(expectedCorrelationId);

        // Act
        filter.doFilter(request, response, chain);

        // Assert
        verify(chain).doFilter(request, response);
        // MDC should be cleared after filter execution
        assertNull(MDC.get("correlationId"));
    }

    @Test
    void doFilter_WithoutCorrelationId_ShouldGenerateNewId() throws IOException, ServletException {
        // Arrange
        when(request.getHeader("X-CorrelationId")).thenReturn(null);

        // Act
        filter.doFilter(request, response, chain);

        // Assert
        verify(chain).doFilter(request, response);
        // MDC should be cleared after filter execution
        assertNull(MDC.get("correlationId"));
    }

    @Test
    void doFilter_WithEmptyCorrelationId_ShouldGenerateNewId() throws IOException, ServletException {
        // Arrange
        when(request.getHeader("X-CorrelationId")).thenReturn("");

        // Act
        filter.doFilter(request, response, chain);

        // Assert
        verify(chain).doFilter(request, response);
        assertNull(MDC.get("correlationId"));
    }

    @Test
    void doFilter_WithWhitespaceCorrelationId_ShouldGenerateNewId() throws IOException, ServletException {
        // Arrange
        when(request.getHeader("X-CorrelationId")).thenReturn("   ");

        // Act
        filter.doFilter(request, response, chain);

        // Assert
        verify(chain).doFilter(request, response);
        assertNull(MDC.get("correlationId"));
    }

    @Test
    void doFilter_ShouldClearMDCAfterExecution() throws IOException, ServletException {
        // Arrange
        when(request.getHeader("X-CorrelationId")).thenReturn("test-id");

        // Act
        filter.doFilter(request, response, chain);

        // Assert
        assertNull(MDC.get("correlationId"), "MDC should be cleared after filter execution");
    }

    @Test
    void doFilter_WhenChainThrowsException_ShouldStillClearMDC() throws IOException, ServletException {
        // Arrange
        when(request.getHeader("X-CorrelationId")).thenReturn("test-id");
        doThrow(new ServletException("Test exception")).when(chain).doFilter(request, response);

        // Act & Assert
        assertThrows(ServletException.class, () -> filter.doFilter(request, response, chain));
        assertNull(MDC.get("correlationId"), "MDC should be cleared even when exception occurs");
    }
}
