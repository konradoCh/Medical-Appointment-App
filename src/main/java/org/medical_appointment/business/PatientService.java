package org.medical_appointment.business;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.medical_appointment.api.DTO.PatientDTO;
import org.medical_appointment.business.dao.PatientDAO;
import org.medical_appointment.domain.Patient;
import org.medical_appointment.domain.User;
import org.medical_appointment.infrastructure.database.entity.UserEntity;
import org.medical_appointment.infrastructure.database.mapper.UserEntityMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class PatientService {

    private final PatientDAO patientDAO;
    private final UserService userService;
    private final UserEntityMapper userEntityMapper;

    public Patient findPatientByPesel(String pesel) {
        Optional<Patient> patient = patientDAO.findByPesel(pesel);
        if (patient.isEmpty()) {
            throw new EntityNotFoundException("Could not find patient by pesel: [%s]".formatted(pesel));
        }
        return patient.get();
    }

    public Patient findPatientByUserId(int userId) {
        Optional<Patient> patient = patientDAO.findPatientByUserId(userId);
        if (patient.isEmpty()) {
            throw new EntityNotFoundException("Could not find patient by userId: [%s]".formatted(userId));
        }
        return patient.get();
    }


    @Transactional
    public void createAndSaveNewPatient(PatientDTO patientDTO) {
        Patient newPatient = Patient.builder()
                .name(patientDTO.getName())
                .surname(patientDTO.getSurname())
                .pesel(patientDTO.getPesel())
                .phone(patientDTO.getPhone())
                .email(patientDTO.getEmail())
                .build();

        User user = userService.findByUserName(patientDTO.getUser().getUserName());
        UserEntity userEntity = userEntityMapper.mapToEntity(user);

        patientDAO.createAndSaveNewDoctor(newPatient, userEntity);
        log.info("Created new Patient [{}] [{}], pesel: [{}]",
                newPatient.getName(),
                newPatient.getSurname(),
                newPatient.getPesel());
    }
}
