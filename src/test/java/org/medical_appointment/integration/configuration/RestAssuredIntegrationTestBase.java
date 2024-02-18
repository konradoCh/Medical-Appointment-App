package org.medical_appointment.integration.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class RestAssuredIntegrationTestBase extends AbstractIT {

    protected static WireMockServer wireMockServer;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @SuppressWarnings("unused")
    private ObjectMapper objectMapper;

    @Test
    void contextLoaded() {
        assertThat(true).isTrue();
    }

    @BeforeAll
    static void beforeAll() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
    }

    @AfterAll
    static void afterAll() {
        wireMockServer.stop();
    }

    @AfterEach
    void clear() {
        jdbcTemplate.execute("TRUNCATE TABLE doctor_available CASCADE;");
        jdbcTemplate.execute("TRUNCATE TABLE doctor CASCADE;");
        jdbcTemplate.execute("TRUNCATE TABLE patient CASCADE;");
        jdbcTemplate.execute("TRUNCATE TABLE medical_appointment_user CASCADE;");
    }

    @AfterEach
    void afterEach() {
        wireMockServer.resetAll();
    }

    public RequestSpecification requestSpecification() {
        return restAssuredBase();
    }

    private RequestSpecification restAssuredBase() {
        return RestAssured
                .given()
                .config(getConfig())
                .basePath(basePath)
                .port(port);
    }

    private RestAssuredConfig getConfig() {
        return RestAssuredConfig.config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((type, s) -> objectMapper));
    }
}
