package org.medical_appointment.domain;

import lombok.*;
import org.medical_appointment.infrastructure.database.entity.DoctorAvailableEntity;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "medicalLicence")
@ToString(of = {"name", "surname", "specialization", "medicalLicence"})

public class Doctor {

    Integer doctorId;
    String name;
    String surname;
    String specialization;
    String medicalLicence;
    Set<DoctorAvailableEntity> availableAppointments;

}
