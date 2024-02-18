package org.medical_appointment.api.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {

    private String name;
    private String surname;
    private String specialization;
    @Pattern(regexp = "\\d+")
    @Size(min = 7, max = 7, message = "Medical Licence must contain exactly 7 digits")
    private String medicalLicence;
    @Valid
    UserDTO user;
}
