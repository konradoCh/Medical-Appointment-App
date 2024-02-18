package org.medical_appointment.domain;


import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"appointmentId",  "appointmentNumber"})
@ToString(of = {"doctorAvailable", "illness", "completed"})

public class Appointment {

    Integer appointmentId;
    String appointmentNumber;
    Patient patient;
    DoctorAvailable doctorAvailable;
    String description;
    String illness;
    Boolean completed;
    Boolean canceled;


}
