package org.medical_appointment.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.medical_appointment.business.dao.DoctorDAO;
import org.medical_appointment.domain.Doctor;
import org.medical_appointment.infrastructure.database.entity.DoctorEntity;
import org.medical_appointment.infrastructure.database.mapper.DoctorEntityMapper;
import org.medical_appointment.infrastructure.database.repository.jpa.DoctorJpaRepository;
import org.medical_appointment.infrastructure.database.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class DoctorRepository implements DoctorDAO {

    private final DoctorJpaRepository doctorJpaRepository;
    private final DoctorEntityMapper doctorEntityMapper;

    @Override
    public Optional<Doctor> findByMedicalLicence(String medicalLicence) {
        return doctorJpaRepository.findByMedicalLicence(medicalLicence)
                .map(doctorEntityMapper::mapFromEntity);
    }

    @Override
    public Optional<Doctor> findDoctorByUserId(int userId) {
        return doctorJpaRepository.findDoctorByUserId(userId)
                .map(doctorEntityMapper::mapFromEntity);
    }

    @Override
    public List<String> findAvailableSpecializations() {
        return doctorJpaRepository.findAvailableSpecializations()
                .stream()
                .toList();
    }

    @Override
    public void createAndSaveNewDoctor(Doctor newDoctor, UserEntity userEntity) {
        DoctorEntity newDoctorEntity = doctorEntityMapper.mapToEntity(newDoctor);
        newDoctorEntity.setUser(userEntity);
        doctorJpaRepository.saveAndFlush(newDoctorEntity);
    }
}
