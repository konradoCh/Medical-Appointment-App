package org.medical_appointment.api.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    Integer id;
    @Size(min = 3, max = 32, message = "User name must contain from 3 to 32 chars")
    String userName;
    @Email
    String email;
    @Size(min = 4, max = 128, message = "Password must contain min 4 chars")
    String password;
    String role;
    Boolean active;


}
