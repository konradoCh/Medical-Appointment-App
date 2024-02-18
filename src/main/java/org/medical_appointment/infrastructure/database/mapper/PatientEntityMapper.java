package org.medical_appointment.infrastructure.database.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.medical_appointment.domain.Patient;
import org.medical_appointment.infrastructure.database.entity.PatientEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientEntityMapper {

    @Mapping(target = "medicalAppointments", ignore = true)
    Patient mapFromEntity(PatientEntity entity);

    @Mapping(target = "medicalAppointments", ignore = true)
    PatientEntity mapToEntity(Patient patient);
}