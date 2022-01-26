package com.lsdconsulting.stub.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.lsdconsulting.stub.integration.controller.RestControllerExampleStub;
import com.lsdconsulting.stub.integration.model.Greeting;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static java.util.Objects.requireNonNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RestControllerIT {

    private static WireMockServer wireMockServer;

    private final RestTemplate restTemplate = new RestTemplate();
    private final RestControllerExampleStub underTest = new RestControllerExampleStub(new ObjectMapper());

    @BeforeAll
    static void setupAll() {
        wireMockServer = new WireMockServer(options().port(8080));
        wireMockServer.start();
    }

    @AfterAll
    static void tearDownAll() {
        wireMockServer.stop();
    }

    @Test
    void shouldHandleGetMapping() {
        String name = RandomStringUtils.randomAlphabetic(10);
        underTest.getResource1(Greeting.builder().name(name).build());

        ResponseEntity<Greeting> response = restTemplate.getForEntity("http://localhost:8080/root1/resource1", Greeting.class);

        assertThat(requireNonNull(response.getBody()).getName(), is(name));
        underTest.verifyGetResource1();
    }
}