package org.medical_appointment.integration.configuration;

import org.medical_appointment.MedicalAppointmentApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import(PersistenceContainerTestConfiguration.class)
@SpringBootTest(
        classes = MedicalAppointmentApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public abstract class AbstractIT {
    //klasa do testów integracyjnych, aby każdy test wykorzystywał tę konfigurację i żeby context nie musiała być
    // tworzony za każdym razem dla każdego testu (przyspieszenie działania)

    @LocalServerPort
    protected int port;

    @Value("${server.servlet.context-path}")
    protected String basePath;

}
