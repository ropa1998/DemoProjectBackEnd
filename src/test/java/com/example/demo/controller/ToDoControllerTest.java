package com.example.demo.controller;

import com.example.demo.entity.ToDo;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ToDoControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "/todo";

    @Test
    void getAllToDos() {
        val getResponse = restTemplate.exchange(baseUrl, HttpMethod.GET, null, ToDo[].class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    }
}