package ru.clevertec.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IntegrationTest {

    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void call() {
    }

    @Test
    void getListRequestSize() {
    }
}
