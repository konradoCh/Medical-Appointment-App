package org.medical_appointment.domain;

import lombok.*;

import java.time.OffsetDateTime;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"doctorAvailableId", "doctor"})
@ToString(of = {"doctor", "dateTime", "reserved"})

public class DoctorAvailable {

    Integer doctorAvailableId;
    OffsetDateTime dateTime;
    Boolean reserved;
    Boolean deleted;
    Boolean completed;
    Doctor doctor;

}
