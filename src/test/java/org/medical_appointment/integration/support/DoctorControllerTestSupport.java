package org.medical_appointment.integration.support;

import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.medical_appointment.api.DTO.DoctorAvailableDTO;
import org.medical_appointment.api.DTO.DoctorAvailableListDTO;
import org.medical_appointment.api.controller.rest.DoctorRestController;
import org.springframework.http.HttpStatus;

public interface DoctorControllerTestSupport {

    RequestSpecification requestSpecification();

    default DoctorAvailableListDTO doctorAvailableList(String medicalLicence) {
        return requestSpecification()
                .get(DoctorRestController.API_DOCTOR + "/" + medicalLicence + "/availableAppointments")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(DoctorAvailableListDTO.class);
    }

    default ExtractableResponse<Response> saveDoctorAvailable(final DoctorAvailableDTO doctorAvailableDTO) {
        return requestSpecification()
                .body(doctorAvailableDTO)
                .queryParam("date", doctorAvailableDTO.getDateTime())
                .queryParam("time", "15:00")
                .queryParam("medicalLicence", doctorAvailableDTO.getDoctor().getMedicalLicence())
                .post(DoctorRestController.API_DOCTOR)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract();
    }

    default void updateDoctorAvailable(
            final DoctorAvailableDTO doctorAvailableDTO,
            final String newDate,
            final String newTime
    ) {
        Integer doctorAvailableId = doctorAvailableDTO.getDoctorAvailableId();
        String endpoint = DoctorRestController.API_DOCTOR + "/" + doctorAvailableId;

        requestSpecification()
                .contentType(ContentType.JSON)
                .body(doctorAvailableDTO)
                .queryParam("date", newDate)
                .queryParam("time", newTime)
                .queryParam("medicalLicence", doctorAvailableDTO.getDoctor().getMedicalLicence())
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    default void deleteDoctorAvailable(
            final DoctorAvailableDTO doctorAvailableDTO
    ) {
        Integer doctorAvailableId = doctorAvailableDTO.getDoctorAvailableId();
        String endpoint = DoctorRestController.API_DOCTOR + "/" + doctorAvailableId;

        requestSpecification()
                .contentType(ContentType.JSON)
                .body(doctorAvailableDTO)
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }
}
