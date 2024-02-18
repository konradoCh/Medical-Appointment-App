package org.medical_appointment.infrastructure.database.repository.jpa;

import org.medical_appointment.infrastructure.database.entity.AppointmentEntity;
import org.medical_appointment.infrastructure.database.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentJpaRepository extends JpaRepository<AppointmentEntity, Integer> {

    @Query("""
            SELECT appointment FROM AppointmentEntity appointment 
            WHERE appointment.patient = :patient
            AND appointment.completed = TRUE
            """)
    List<AppointmentEntity> findAllCompletedAppointmentsForPatient(@Param("patient") PatientEntity patient);

    @Query("""
            SELECT app FROM AppointmentEntity app
            LEFT JOIN FETCH app.doctorAvailable da
            WHERE da.doctorAvailableId = :doctorAvailableId 
            """)
    Optional<AppointmentEntity> findAppointmentByDoctorAvailableId(@Param("doctorAvailableId") Integer doctorAvailableId);

    @Query("""
            SELECT appointment FROM AppointmentEntity appointment 
            WHERE appointment.patient = :patient
            AND appointment.completed = FALSE
                        """)
    List<AppointmentEntity> findAllUncompletedAppointmentsByPesel(@Param(value = "patient") PatientEntity patientEntity);
}
