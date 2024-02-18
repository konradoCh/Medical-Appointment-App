package org.medical_appointment.business;


import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.medical_appointment.api.DTO.DoctorDTO;
import org.medical_appointment.business.dao.DoctorDAO;
import org.medical_appointment.domain.Doctor;
import org.medical_appointment.domain.User;
import org.medical_appointment.infrastructure.database.entity.UserEntity;
import org.medical_appointment.infrastructure.database.mapper.UserEntityMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class DoctorService {

    private final DoctorDAO doctorDAO;
    private final UserService userService;
    private final UserEntityMapper userEntityMapper;

    public Doctor findDoctorByMedicalLicence(String medicalLicence) {
        Optional<Doctor> doctor = doctorDAO.findByMedicalLicence(medicalLicence);
        if (doctor.isEmpty()) {
            throw new EntityNotFoundException("Could not find doctor by medical licence: [%s]".formatted(medicalLicence));
        }
        return doctor.get();
    }

    public Doctor findPatientByUserId(int userId) {
        Optional<Doctor> doctor = doctorDAO.findDoctorByUserId(userId);
        if (doctor.isEmpty()) {
            throw new EntityNotFoundException("Could not find doctor by userId: [%s]".formatted(userId));
        }
        return doctor.get();
    }

    @Transactional
    public void createAndSaveNewDoctor(DoctorDTO doctorDTO) {
        Doctor newDoctor = Doctor.builder()
                .name(doctorDTO.getName())
                .surname(doctorDTO.getSurname())
                .specialization(doctorDTO.getSpecialization())
                .medicalLicence(doctorDTO.getMedicalLicence())
                .build();

        User user = userService.findByUserName(doctorDTO.getUser().getUserName());
        UserEntity userEntity = userEntityMapper.mapToEntity(user);

        doctorDAO.createAndSaveNewDoctor(newDoctor, userEntity);
        log.info("Created new Doctor [{}] [{}], medical licence: [{}]",
                newDoctor.getName(),
                newDoctor.getSurname(),
                newDoctor.getMedicalLicence());
    }

    public List<String> availableSpecializations() {
        List<String> availableSpecializations = doctorDAO.findAvailableSpecializations();
        log.info("Available specializations: [{}]", availableSpecializations.size());
        return availableSpecializations;
    }

}
