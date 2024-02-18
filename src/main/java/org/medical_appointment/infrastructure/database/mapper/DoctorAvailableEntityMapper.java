package org.medical_appointment.infrastructure.database.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.medical_appointment.domain.Doctor;
import org.medical_appointment.domain.DoctorAvailable;
import org.medical_appointment.infrastructure.database.entity.DoctorAvailableEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DoctorAvailableEntityMapper {

    default DoctorAvailable mapFromEntity(DoctorAvailableEntity entity) {
        return DoctorAvailable.builder()
                .doctorAvailableId(entity.getDoctorAvailableId())
                .doctor(Doctor.builder()
                        .name(entity.getDoctor().getName())
                        .surname(entity.getDoctor().getSurname())
                        .medicalLicence(entity.getDoctor().getMedicalLicence())
                        .specialization(entity.getDoctor().getSpecialization())
                        .build())
                .dateTime(entity.getDateTime())
                .reserved(entity.getReserved())
                .deleted(entity.getDeleted())
                .completed(entity.getCompleted())
                .build();
    }

    DoctorAvailableEntity mapToEntity(DoctorAvailable doctorAvailable);
}
