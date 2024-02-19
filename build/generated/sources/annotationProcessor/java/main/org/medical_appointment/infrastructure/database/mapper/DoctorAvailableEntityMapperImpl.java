package org.medical_appointment.infrastructure.database.mapper;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.medical_appointment.domain.Doctor;
import org.medical_appointment.domain.DoctorAvailable;
import org.medical_appointment.infrastructure.database.entity.DoctorAvailableEntity;
import org.medical_appointment.infrastructure.database.entity.DoctorEntity;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-19T19:37:41+0100",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.0.jar, environment: Java 18.0.1.1 (Oracle Corporation)"
)
@Component
public class DoctorAvailableEntityMapperImpl implements DoctorAvailableEntityMapper {

    @Override
    public DoctorAvailableEntity mapToEntity(DoctorAvailable doctorAvailable) {
        if ( doctorAvailable == null ) {
            return null;
        }

        DoctorAvailableEntity.DoctorAvailableEntityBuilder doctorAvailableEntity = DoctorAvailableEntity.builder();

        doctorAvailableEntity.doctorAvailableId( doctorAvailable.getDoctorAvailableId() );
        doctorAvailableEntity.dateTime( doctorAvailable.getDateTime() );
        doctorAvailableEntity.reserved( doctorAvailable.getReserved() );
        doctorAvailableEntity.deleted( doctorAvailable.getDeleted() );
        doctorAvailableEntity.completed( doctorAvailable.getCompleted() );
        doctorAvailableEntity.doctor( doctorToDoctorEntity( doctorAvailable.getDoctor() ) );

        return doctorAvailableEntity.build();
    }

    protected DoctorEntity doctorToDoctorEntity(Doctor doctor) {
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
