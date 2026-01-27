package com.self.AOP.practice.controller;

import com.self.AOP.practice.aspects.BeforeLog;
import com.self.AOP.practice.service.SampleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for sample operations.
 * Demonstrates AOP logging with various endpoint types.
 */
@RestController
@RequestMapping("/api/v1")
public class SampleController {

    private final SampleService sampleService;

    // Constructor injection - preferred over field injection
    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @GetMapping("/hello")
    @BeforeLog
    public ResponseEntity<String> sayHello() {
        String data = sampleService.getData();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/data")
    @BeforeLog
    public ResponseEntity<String> getData() {
        return ResponseEntity.ok(sampleService.getData());
    }

    @GetMapping("/greet/{name}")
    @BeforeLog
    public ResponseEntity<String> greetUser(@PathVariable String name) {
        String greeting = sampleService.greetUser(name);
        return ResponseEntity.ok(greeting);
    }
}
