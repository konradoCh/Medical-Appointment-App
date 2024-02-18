package org.medical_appointment.api.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {

    Integer appointmentId;
    String appointmentNumber;
    String dateTime;
    String description;
    String doctorName;
    String doctorSurname;
    String doctorSpecialization;
    Integer doctorAvailableId;
    String patientName;
    String patientSurname;
    String patientPesel;
    String illness;
    Boolean completed;
    Boolean canceled;
    Boolean reserved;
}
