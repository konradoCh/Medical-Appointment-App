package org.medical_appointment.business;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medical_appointment.business.dao.PatientDAO;
import org.medical_appointment.domain.Patient;
import org.medical_appointment.util.PatientFixture;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientDAO patientDAO;

    @Test
    void findPatientByPeselWhenPatientExists() {
        //given
        Patient patient = PatientFixture.somePatient();
        String pesel = patient.getPesel();
        Mockito.when(patientDAO.findByPesel(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(patient));

        //when
        Patient foundPatient = patientService.findPatientByPesel(pesel);

        //then
        Assertions.assertNotNull(foundPatient);
        Assertions.assertEquals(patient, foundPatient);
    }

    @Test
    void findPatientByPeselWhenPatientNotExists() {
        //given
        Patient patient = PatientFixture.somePatient();
        String pesel = patient.getPesel();
        Mockito.when(patientDAO.findByPesel(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        //when, then
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            patientService.findPatientByPesel(pesel);
        });
    }

//    @Test
//    void createAndSaveNewPatientWorksCorrectly() {
//        //given
//        Patient newPatient = PatientFixture.somePatient();
//
//        //when
//        patientService.createAndSaveNewPatient(newPatient);
//
//        //then
//        Mockito.verify(patientDAO, Mockito.times(1)).createAndSaveNewDoctor(newPatient);
//    }
}