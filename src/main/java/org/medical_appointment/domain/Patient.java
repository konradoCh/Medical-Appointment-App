package org.medical_appointment.domain;


import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "pesel")
@ToString(of = {"name", "surname", "phone"})

public class Patient {

    Integer patientId;
    String name;
    String surname;
    String phone;
    String email;
    String pesel;
    Set<Appointment> medicalAppointments;

}
