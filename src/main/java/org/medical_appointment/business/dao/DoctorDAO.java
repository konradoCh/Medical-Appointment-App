package org.medical_appointment.business.dao;

import org.medical_appointment.domain.Doctor;
import org.medical_appointment.infrastructure.database.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface DoctorDAO {
    Optional<Doctor> findByMedicalLicence(String medicalLicence);

    Optional<Doctor> findDoctorByUserId(int userId);

    List<String> findAvailableSpecializations();

    void createAndSaveNewDoctor(Doctor newDoctor, UserEntity userEntity);
}
