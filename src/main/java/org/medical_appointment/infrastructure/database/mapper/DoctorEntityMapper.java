package org.medical_appointment.infrastructure.database.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.medical_appointment.domain.Doctor;
import org.medical_appointment.infrastructure.database.entity.DoctorEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DoctorEntityMapper {

    @Mapping(target = "availableAppointments", ignore = true)
    Doctor mapFromEntity(DoctorEntity entity);

    DoctorEntity mapToEntity(Doctor doctor);
}
