package org.medical_appointment.util;

import lombok.experimental.UtilityClass;
import org.medical_appointment.api.DTO.UserDTO;
import org.medical_appointment.infrastructure.database.entity.UserEntity;

@UtilityClass
public class UserFixture {

    public static UserEntity someUserEntity() {
        return UserEntity.builder()
                .userName("UserName")
                .password("test")
                .email("test@gmail.com")
                .active(true)
                .build();
    }

    public static UserDTO someUserDTO() {
        return UserDTO.builder()
                .id(10)
                .userName("UserName")
                .password("test")
                .email("test@gmail.com")
                .active(true)
                .role("doctor")
                .build();
    }


}
