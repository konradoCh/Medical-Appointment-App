package org.medical_appointment.infrastructure.database.repository.jpa;


import org.medical_appointment.infrastructure.database.entity.DoctorAvailableEntity;
import org.medical_appointment.infrastructure.database.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientJpaRepository extends JpaRepository<PatientEntity, Integer> {

    Optional<PatientEntity> findByPesel(String pesel);

    @Query("""
            SELECT da FROM DoctorAvailableEntity da
            LEFT JOIN FETCH da.doctor d
            WHERE d.specialization = :specialization 
            AND da.reserved = FALSE
            """)
    List<DoctorAvailableEntity> findBySpecialization(final @Param("specialization") String specialization);

    @Query("""
    SELECT p FROM PatientEntity p 
    JOIN FETCH p.user u 
    WHERE u.id = :userId
    """)
    Optional<PatientEntity> findPatientByUserId(@Param("userId") int userId);
}
