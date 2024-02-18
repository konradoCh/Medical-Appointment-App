package org.medical_appointment.business;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medical_appointment.business.dao.DoctorDAO;
import org.medical_appointment.domain.Doctor;
import org.medical_appointment.util.DoctorFixture;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @InjectMocks
    private DoctorService doctorService;

    @Mock
    private DoctorDAO doctorDAO;

    @Test
    void findDoctorByMedicalLicenceWhenDoctorExists() {
        //given
        Doctor doctor = DoctorFixture.someDoctor();
        String medicalLicence = doctor.getMedicalLicence();
        Mockito.when(doctorDAO.findByMedicalLicence(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(doctor));

        //when
        Doctor foundDoctor = doctorService.findDoctorByMedicalLicence(medicalLicence);

        //then
        Assertions.assertNotNull(foundDoctor);
        Assertions.assertEquals(doctor, foundDoctor);
    }

    @Test
    void findPatientByMedicalLicenceWhenDoctorNotExists() {
        //given
        Doctor doctor = DoctorFixture.someDoctor();
        String medicalLicence = doctor.getMedicalLicence();
        Mockito.when(doctorDAO.findByMedicalLicence(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        //then, when
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            doctorService.findDoctorByMedicalLicence(medicalLicence);
        });
    }

//    @Test
//    void createAndSaveNewDoctorWorksCorrectly() {
//        //given
//        Doctor newDoctor = DoctorFixture.someDoctor();
//
//        //when
//        doctorService.createAndSaveNewDoctor(newDoctor);
//
//        //then
//        Mockito.verify(doctorDAO, Mockito.times(1)).createAndSaveNewDoctor(newDoctor, userEntity);
//    }

    @Test
    void availableSpecializationsWorksCorrectly() {
        //given
        Doctor doctor = DoctorFixture.someDoctor();
        String specialization = doctor.getSpecialization();
        Mockito.when(doctorDAO.findAvailableSpecializations())
                .thenReturn(List.of(specialization));

        //when
        List<String> specializations = doctorService.availableSpecializations();

        //then
        Assertions.assertNotNull(specializations);
        Assertions.assertEquals(specialization, specializations.get(0));
    }
}