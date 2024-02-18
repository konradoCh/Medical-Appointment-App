package org.medical_appointment.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.medical_appointment.business.dao.PatientDAO;
import org.medical_appointment.domain.Patient;
import org.medical_appointment.infrastructure.database.entity.PatientEntity;
import org.medical_appointment.infrastructure.database.mapper.PatientEntityMapper;
import org.medical_appointment.infrastructure.database.repository.jpa.PatientJpaRepository;
import org.medical_appointment.infrastructure.database.entity.UserEntity;
import org.medical_appointment.infrastructure.database.repository.jpa.UserJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class PatientRepository implements PatientDAO {

    private final PatientJpaRepository patientJpaRepository;
    private final PatientEntityMapper patientEntityMapper;

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<Patient> findByPesel(String pesel) {
        return patientJpaRepository.findByPesel(pesel)
                .map(patientEntityMapper::mapFromEntity);
    }

    @Override
    public Optional<Patient> findPatientByUserId(int userId) {
        return patientJpaRepository.findPatientByUserId(userId)
                .map(patientEntityMapper::mapFromEntity);
    }

    @Override
    public void createAndSaveNewDoctor(Patient newPatient, UserEntity userEntity) {
        PatientEntity newPatientEntity = patientEntityMapper.mapToEntity(newPatient);

        newPatientEntity.setUser(userEntity);
        patientJpaRepository.saveAndFlush(newPatientEntity);
    }
}
