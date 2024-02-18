package org.medical_appointment.infrastructure.database.repository.jpa;

import org.medical_appointment.infrastructure.database.entity.DoctorAvailableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorAvailableJpaRepository extends JpaRepository<DoctorAvailableEntity, Integer> {

    @Query("""
            SELECT da FROM DoctorAvailableEntity da
            LEFT JOIN FETCH da.doctor d
            WHERE d.specialization = :specialization 
            AND da.reserved = FALSE
            """)
    List<DoctorAvailableEntity> findBySpecialization(final @Param("specialization") String specialization);

    @Query("""
            SELECT da FROM DoctorAvailableEntity da
            LEFT JOIN FETCH da.doctor d
            WHERE d.medicalLicence = :medicalLicence 
            """)
    List<DoctorAvailableEntity> findByMedicalLicence(final @Param("medicalLicence") String medicalLicence);

}
