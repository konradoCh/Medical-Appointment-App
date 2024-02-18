package org.medical_appointment.util;

import lombok.experimental.UtilityClass;
import org.medical_appointment.api.DTO.PatientDTO;
import org.medical_appointment.domain.Patient;
import org.medical_appointment.infrastructure.database.entity.PatientEntity;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

@UtilityClass
public class PatientFixture {

    public static PatientEntity somePatientEntity() {
        return PatientEntity.builder()
                .name("Jan")
                .surname("Kowalski")
                .phone("+48 754 552 234")
                .email("j.kowal@gmail.com")
                .pesel("54082873337")
                .build();
    }

    public static Patient somePatient() {
        return Patient.builder()
                .name("Jan")
                .surname("Kowalski")
                .phone("+48 754 552 234")
                .email("j.kowal@gmail.com")
                .pesel("54082873337")
                .build();
    }

    public static PatientDTO newPatientDTO() {
        return PatientDTO.builder()
                .name("Jan")
                .surname("Kowalski")
                .phone("+48 754 552 234")
                .email("j.kowal@gmail.com")
                .pesel("54082873337")
                .build();
    }

    public Map<String, String> asMap(PatientDTO patient) {
        Map<String, String> result = new HashMap<>();
        ofNullable(patient.getName()).ifPresent(value -> result.put("name", value));
        ofNullable(patient.getSurname()).ifPresent(value -> result.put("surname", value));
        ofNullable(patient.getPhone()).ifPresent(value -> result.put("phone", value));
        ofNullable(patient.getEmail()).ifPresent(value -> result.put("email", value));
        ofNullable(patient.getPesel()).ifPresent(value -> result.put("pesel", value));

        return result;
    }
}
