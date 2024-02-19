package org.medical_appointment.infrastructure.database.mapper;

import javax.annotation.processing.Generated;
import org.medical_appointment.domain.Patient;
import org.medical_appointment.infrastructure.database.entity.PatientEntity;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-19T19:37:41+0100",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.0.jar, environment: Java 18.0.1.1 (Oracle Corporation)"
)
@Component
public class PatientEntityMapperImpl implements PatientEntityMapper {

    @Override
    public Patient mapFromEntity(PatientEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Patient.PatientBuilder patient = Patient.builder();

        patient.patientId( entity.getPatientId() );
        patient.name( entity.getName() );
        patient.surname( entity.getSurname() );
        patient.phone( entity.getPhone() );
        patient.email( entity.getEmail() );
        patient.pesel( entity.getPesel() );

        return patient.build();
    }

    @Override
    public PatientEntity mapToEntity(Patient patient) {
        if ( patient == null ) {
            return null;
        }

        PatientEntity.PatientEntityBuilder patientEntity = PatientEntity.builder();

        patientEntity.patientId( patient.getPatientId() );
        patientEntity.name( patient.getName() );
        patientEntity.surname( patient.getSurname() );
        patientEntity.phone( patient.getPhone() );
        patientEntity.email( patient.getEmail() );
        patientEntity.pesel( patient.getPesel() );

        return patientEntity.build();
    }
}
