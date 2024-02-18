package org.medical_appointment.util;

import lombok.experimental.UtilityClass;
import org.medical_appointment.api.DTO.DoctorDTO;
import org.medical_appointment.domain.Doctor;
import org.medical_appointment.infrastructure.database.entity.DoctorEntity;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

@UtilityClass
public class DoctorFixture {

    public static Doctor someDoctor() {
        return Doctor.builder()
                .name("George")
                .surname("Mich")
                .specialization("Cardiologist")
                .medicalLicence("157895")
                .build();
    }

    public static DoctorEntity someDoctorEntity() {
        return DoctorEntity.builder()
                .name("George")
                .surname("Mich")
                .specialization("Cardiologist")
                .medicalLicence("1578965")
                .build();
    }

    public static DoctorDTO newDoctorDTO() {
        return DoctorDTO.builder()
                .name("Jan")
                .surname("Kowalski")
                .specialization("Cardiologist")
                .medicalLicence("5207099")
                .build();
    }

    public Map<String, String> asMap(DoctorDTO doctorDTO) {
        Map<String, String> result = new HashMap<>();
        ofNullable(doctorDTO.getName()).ifPresent(value -> result.put("name", value));
        ofNullable(doctorDTO.getSurname()).ifPresent(value -> result.put("surname", value));
        ofNullable(doctorDTO.getSpecialization()).ifPresent(value -> result.put("specialization", value));
        ofNullable(doctorDTO.getMedicalLicence()).ifPresent(value -> result.put("medicalLicence", value));

        return result;
    }
}
