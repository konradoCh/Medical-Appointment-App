package org.medical_appointment.integration;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.medical_appointment.integration.configuration.AbstractIT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginControllerIT extends AbstractIT {

    private final TestRestTemplate testRestTemplate;

    @Test
    void homePageWorksCorrectly() {
        String url = String.format("http://localhost:%s%s/login", port, basePath);

        String page = this.testRestTemplate.getForObject(url, String.class);
        Assertions.assertThat(page).contains("Login Page");
    }
}
