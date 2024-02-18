package org.medical_appointment.business.dao;

import org.medical_appointment.domain.Patient;
import org.medical_appointment.infrastructure.database.entity.UserEntity;

import java.util.Optional;

public interface PatientDAO {

    Optional<Patient> findByPesel(String pesel);

    Optional<Patient> findPatientByUserId(int userId);

    void createAndSaveNewDoctor(Patient newPatient, UserEntity user);
}
