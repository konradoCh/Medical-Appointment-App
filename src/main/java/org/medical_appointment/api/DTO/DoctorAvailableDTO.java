package org.medical_appointment.api.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAvailableDTO {

    Integer doctorAvailableId;
    String dateTime;
    DoctorDTO doctor;
    Boolean reserved;
    Boolean deleted;
    Boolean completed;

}
