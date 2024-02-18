package org.medical_appointment.infrastructure.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.medical_appointment.MedicalAppointmentApplication;
import org.springdoc.core.models.GroupedOpenApi;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    // Klasa odpowiada za przygotowanie beanów, które posłużą do wygenerowania dokumentacji OpenAPI.

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("default")
                .pathsToMatch("/**")
                .packagesToScan(MedicalAppointmentApplication.class.getPackageName())
                .build();
    }

    @Bean
    public OpenAPI springDocOpenApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Medical appointment application")
                        .contact(new Contact().name("Konrad").email("konrad@gmail.com"))
                        .version("1.0"));
    }
}
