package org.medical_appointment.util;

import lombok.experimental.UtilityClass;
import org.medical_appointment.api.DTO.RandomUserDTO;

@UtilityClass
public class RandomUserFixture {

    public RandomUserDTO someRandomUserDTO() {
        return RandomUserDTO.builder()
                .firstName("Name")
                .lastName("Surname")
                .email("example@gmail.com")
                .build();
    }
}
