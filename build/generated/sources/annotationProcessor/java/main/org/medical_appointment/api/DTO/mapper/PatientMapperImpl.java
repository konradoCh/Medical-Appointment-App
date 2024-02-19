package org.medical_appointment.api.DTO.mapper;

import javax.annotation.processing.Generated;
import org.medical_appointment.api.DTO.PatientDTO;
import org.medical_appointment.domain.Patient;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-18T14:28:31+0100",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.0.jar, environment: Java 18.0.1.1 (Oracle Corporation)"
)
@Component
public class PatientMapperImpl implements PatientMapper {

    @Override
    public PatientDTO map(Patient patient) {
        if ( patient == null ) {
            return null;
        }

        PatientDTO.PatientDTOBuilder patientDTO = PatientDTO.builder();

        patientDTO.name( patient.getName() );
        patientDTO.surname( patient.getSurname() );
        patientDTO.phone( patient.getPhone() );
        patientDTO.email( patient.getEmail() );
        patientDTO.pesel( patient.getPesel() );

        return patientDTO.build();
    }

    @Override
    public Patient map(PatientDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Patient.PatientBuilder patient = Patient.builder();

        patient.name( dto.getName() );
        patient.surname( dto.getSurname() );
        patient.phone( dto.getPhone() );
        patient.email( dto.getEmail() );
        patient.pesel( dto.getPesel() );

        return patient.build();
    }
}
