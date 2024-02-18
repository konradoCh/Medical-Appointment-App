package org.medical_appointment.infrastructure.database.repository.jpa;

import org.medical_appointment.infrastructure.database.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface DoctorJpaRepository extends JpaRepository<DoctorEntity, Integer> {

    Optional<DoctorEntity> findByMedicalLicence(String medicalLicence);

    @Query("""
            SELECT doctor.specialization FROM DoctorEntity doctor
            """)
    Set<String> findAvailableSpecializations();

    @Query("""
    SELECT d FROM DoctorEntity d 
    JOIN FETCH d.user u 
    WHERE u.id = :userId
    """)
    Optional<DoctorEntity>  findDoctorByUserId(@Param("userId") int userId);
}
