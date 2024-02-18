package org.medical_appointment.api.DTO.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.medical_appointment.api.DTO.PatientDTO;
import org.medical_appointment.domain.Patient;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDTO map(Patient patient);

    @Mapping(target = "patientId", ignore = true)
    @Mapping(target = "medicalAppointments", ignore = true)
    Patient map(final PatientDTO dto);
}
