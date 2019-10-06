package com.egebakken.springdemo;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ApplicationTests {

    @LocalServerPort
    String port;

    @Autowired
    private WebTestClient client;

    @Test
    public void findAllUsers() {
        client.get()
              .uri("user")
              .exchange()
              .expectStatus()
              .isOk()
              .expectBody()
              .json("[{\"id\":1,\"username\":\"smalahove\"}]");

    }
}
