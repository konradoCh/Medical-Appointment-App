package org.medical_appointment.api.DTO.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.medical_appointment.api.DTO.DoctorDTO;
import org.medical_appointment.domain.Doctor;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorDTO map(Doctor doctor);

    @Mapping(target = "doctorId", ignore = true)
    @Mapping(target = "availableAppointments", ignore = true)
    Doctor map(final DoctorDTO dto);
}
