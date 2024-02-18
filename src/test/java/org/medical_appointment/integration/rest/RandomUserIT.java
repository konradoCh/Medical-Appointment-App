package org.medical_appointment.integration.rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.medical_appointment.api.controller.rest.RandomUserRestController;
import org.medical_appointment.integration.configuration.RestAssuredIntegrationTestBase;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class RandomUserIT
        extends RestAssuredIntegrationTestBase {

    @Test
    void thatGettingRandomUserWorksCorrectly() {
        //given
        wireMockServer.stubFor(
                get(urlPathEqualTo(RandomUserRestController.API_RANDOM))
                        .willReturn(aResponse()
                                .withBodyFile("wiremock/randomUser.json"))
        );

        //when
        Response response = RestAssured.get(RandomUserRestController.API_RANDOM);

        //then
        Assertions.assertThat(response.jsonPath().getString("firstName")).isEqualTo("Name");
        Assertions.assertThat(response.jsonPath().getString("lastName")).isEqualTo("Surname");
        Assertions.assertThat(response.jsonPath().getString("email")).isEqualTo("example@gmail.com");
    }
}
