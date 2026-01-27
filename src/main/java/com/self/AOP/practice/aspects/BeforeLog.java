package com.self.AOP.practice.aspects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to enable AOP logging for methods.
 * When applied to a method, it triggers logging aspects including:
 * - Method entry/exit logging
 * - Execution time tracking
 * - Exception logging
 *
 * @see LoggingAspect
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BeforeLog {
}
