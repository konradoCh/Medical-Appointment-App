package org.medical_appointment.api.DTO.mapper;

import javax.annotation.processing.Generated;
import org.medical_appointment.api.DTO.DoctorDTO;
import org.medical_appointment.domain.Doctor;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-18T14:28:32+0100",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.0.jar, environment: Java 18.0.1.1 (Oracle Corporation)"
)
@Component
public class DoctorMapperImpl implements DoctorMapper {

    @Override
    public DoctorDTO map(Doctor doctor) {
        if ( doctor == null ) {
            return null;
        }

        DoctorDTO.DoctorDTOBuilder doctorDTO = DoctorDTO.builder();

        doctorDTO.name( doctor.getName() );
        doctorDTO.surname( doctor.getSurname() );
        doctorDTO.specialization( doctor.getSpecialization() );
        doctorDTO.medicalLicence( doctor.getMedicalLicence() );

        return doctorDTO.build();
    }

    @Override
    public Doctor map(DoctorDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Doctor.DoctorBuilder doctor = Doctor.builder();

        doctor.name( dto.getName() );
        doctor.surname( dto.getSurname() );
        doctor.specialization( dto.getSpecialization() );
        doctor.medicalLicence( dto.getMedicalLicence() );

        return doctor.build();
    }
}
