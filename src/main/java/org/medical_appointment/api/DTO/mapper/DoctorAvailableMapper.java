package org.medical_appointment.api.DTO.mapper;

import org.mapstruct.Mapper;
import org.medical_appointment.api.DTO.DoctorAvailableDTO;
import org.medical_appointment.api.DTO.DoctorDTO;
import org.medical_appointment.domain.DoctorAvailable;

@Mapper(componentModel = "spring")
public interface DoctorAvailableMapper extends OffsetDateTimeMapper {

    default DoctorAvailableDTO map(DoctorAvailable doctorAvailable) {
        return DoctorAvailableDTO.builder()
                .doctorAvailableId(doctorAvailable.getDoctorAvailableId())
                .dateTime(mapOffsetDateTimeToString(doctorAvailable.getDateTime()))
                .doctor(DoctorDTO.builder()
                        .name(doctorAvailable.getDoctor().getName())
                        .surname(doctorAvailable.getDoctor().getSurname())
                        .specialization(doctorAvailable.getDoctor().getSpecialization())
                        .medicalLicence(doctorAvailable.getDoctor().getMedicalLicence())
                        .build())
                .reserved(doctorAvailable.getReserved())
                .completed(doctorAvailable.getCompleted())
                .deleted(doctorAvailable.getDeleted())
                .build();
    }

}
