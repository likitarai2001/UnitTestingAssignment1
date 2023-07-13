package com.example.socialmediaapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomExceptionHandlerTest {

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    @Test
     void emptyResponseBodyHandler() {
        assertEquals("Response body required", customExceptionHandler.emptyResponseBodyHandler());
    }

    @Test
    void serializeHandler() {
        assertEquals("Another transaction is using this data", customExceptionHandler.serializeHandler());
    }
}