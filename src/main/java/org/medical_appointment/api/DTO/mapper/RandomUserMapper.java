package org.medical_appointment.api.DTO.mapper;

import org.mapstruct.Mapper;
import org.medical_appointment.api.DTO.RandomUserDTO;
import org.medical_appointment.domain.RandomUser;

@Mapper(componentModel = "spring")
public interface RandomUserMapper {

    RandomUserDTO map(RandomUser randomUser);
}
