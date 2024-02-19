package org.medical_appointment.infrastructure.database.mapper;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.medical_appointment.domain.Doctor;
import org.medical_appointment.infrastructure.database.entity.DoctorAvailableEntity;
import org.medical_appointment.infrastructure.database.entity.DoctorEntity;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-19T19:37:42+0100",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.0.jar, environment: Java 18.0.1.1 (Oracle Corporation)"
)
@Component
public class DoctorEntityMapperImpl implements DoctorEntityMapper {

    @Override
    public Doctor mapFromEntity(DoctorEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Doctor.DoctorBuilder doctor = Doctor.builder();

        doctor.doctorId( entity.getDoctorId() );
        doctor.name( entity.getName() );
        doctor.surname( entity.getSurname() );
        doctor.specialization( entity.getSpecialization() );
        doctor.medicalLicence( entity.getMedicalLicence() );

        return doctor.build();
    }

    @Override
    public DoctorEntity mapToEntity(Doctor doctor) {
        if ( doctor == null ) {
            return null;
        }

        DoctorEntity.DoctorEntityBuilder doctorEntity = DoctorEntity.builder();

        doctorEntity.doctorId( doctor.getDoctorId() );
        doctorEntity.name( doctor.getName() );
        doctorEntity.surname( doctor.getSurname() );
        doctorEntity.specialization( doctor.getSpecialization() );
        doctorEntity.medicalLicence( doctor.getMedicalLicence() );
        Set<DoctorAvailableEntity> set = doctor.getAvailableAppointments();
        if ( set != null ) {
            doctorEntity.availableAppointments( new LinkedHashSet<DoctorAvailableEntity>( set ) );
        }

        return doctorEntity.build();
    }
}
