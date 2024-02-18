package org.medical_appointment.infrastructure.database.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medical_appointment.domain.Patient;
import org.medical_appointment.infrastructure.database.entity.PatientEntity;
import org.medical_appointment.infrastructure.database.mapper.PatientEntityMapper;
import org.medical_appointment.infrastructure.database.repository.jpa.PatientJpaRepository;
import org.medical_appointment.util.PatientFixture;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PatientRepositoryTest {

    @InjectMocks
    private PatientRepository patientRepository;

    @Mock
    private PatientJpaRepository patientJpaRepository;

    @Mock
    private PatientEntityMapper patientEntityMapper;

    @Test
    void findByPeselWhenExists() {
        //given
        Patient patient = PatientFixture.somePatient();
        PatientEntity patientEntity = PatientFixture.somePatientEntity();

        Mockito.when(patientEntityMapper.mapFromEntity(ArgumentMatchers.any())).thenReturn(patient);
        Mockito.when(patientJpaRepository.findByPesel(ArgumentMatchers.any())).thenReturn(Optional.of(patientEntity));

        //when
        Patient foundPatient = patientRepository.findByPesel(patient.getPesel()).orElseThrow();

        //then
        Assertions.assertNotNull(foundPatient);
        Assertions.assertEquals(patient.getPesel(), foundPatient.getPesel());
    }

    @Test
    void findByPeselWhenNotExists() {
        //given
        Patient patient = PatientFixture.somePatient();

        Mockito.when(patientJpaRepository.findByPesel(ArgumentMatchers.any())).thenReturn(Optional.empty());

        //when
        Optional<Patient> foundPatient = patientRepository.findByPesel(patient.getPesel());

        //then
        Assertions.assertTrue(foundPatient.isEmpty());
    }

//    @Test
//    void createAndSaveNewDoctorWorksCorrectly() {
//        //given
//        PatientEntity patientEntity = PatientFixture.somePatientEntity();
//        Patient patient = PatientFixture.somePatient();
//        Mockito.when(patientEntityMapper.mapToEntity(patient)).thenReturn(patientEntity);
//
//        //when
//        patientRepository.createAndSaveNewDoctor(patient, user);
//
//        //then
//        Mockito.verify(patientEntityMapper, Mockito.times(1)).mapToEntity(patient);
//        Mockito.verify(patientJpaRepository, Mockito.times(1)).saveAndFlush(patientEntity);
//    }

}