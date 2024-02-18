package org.medical_appointment.util;

import lombok.experimental.UtilityClass;
import org.medical_appointment.api.DTO.DoctorAvailableDTO;
import org.medical_appointment.domain.DoctorAvailable;
import org.medical_appointment.infrastructure.database.entity.DoctorAvailableEntity;

import java.time.OffsetDateTime;

@UtilityClass
public class DoctorAvailableFixture {

    public static DoctorAvailable someDoctorAvailable() {
        return DoctorAvailable.builder()
                .doctorAvailableId(1)
                .dateTime(OffsetDateTime.now())
                .reserved(true)
                .deleted(false)
                .completed(true)
                .doctor(DoctorFixture.someDoctor())
                .build();
    }

    public static DoctorAvailableDTO someDoctorAvailableDTO() {
        return DoctorAvailableDTO.builder()
                .doctorAvailableId(1)
                .dateTime("01-01-2024")
                .doctor(DoctorFixture.newDoctorDTO())
                .reserved(true)
                .deleted(false)
                .completed(false)
                .build();
    }

    public static DoctorAvailableEntity someDoctorAvailableEntity() {
        return DoctorAvailableEntity.builder()
                .dateTime(OffsetDateTime.now())
                .reserved(true)
                .deleted(false)
                .completed(true)
                .doctor(DoctorFixture.someDoctorEntity())
                .build();
    }
}
