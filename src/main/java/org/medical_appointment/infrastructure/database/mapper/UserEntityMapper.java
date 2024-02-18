package org.medical_appointment.infrastructure.database.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.medical_appointment.domain.User;
import org.medical_appointment.infrastructure.database.entity.UserEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserEntityMapper {

    User mapFromEntity(UserEntity entity);

    UserEntity mapToEntity(User user);
}
