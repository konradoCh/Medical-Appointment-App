package org.medical_appointment.infrastructure.database.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medical_appointment.domain.Doctor;
import org.medical_appointment.infrastructure.database.entity.DoctorEntity;
import org.medical_appointment.infrastructure.database.mapper.DoctorEntityMapper;
import org.medical_appointment.infrastructure.database.repository.jpa.DoctorJpaRepository;
import org.medical_appointment.util.DoctorAvailableFixture;
import org.medical_appointment.util.DoctorFixture;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class DoctorRepositoryTest {

    @InjectMocks
    private DoctorRepository doctorRepository;

    @Mock
    private DoctorJpaRepository doctorJpaRepository;

    @Mock
    private DoctorEntityMapper doctorEntityMapper;

    @Test
    void findByMedicalLicenceWhenExists() {
        //given
        Doctor doctor = DoctorFixture.someDoctor();
        DoctorEntity doctorEntity = DoctorFixture.someDoctorEntity();

        Mockito.when(doctorEntityMapper.mapFromEntity(ArgumentMatchers.any())).thenReturn(doctor);
        Mockito.when(doctorJpaRepository.findByMedicalLicence(ArgumentMatchers.any())).thenReturn(Optional.of(doctorEntity));

        //when
        Doctor foundDoctor = doctorRepository.findByMedicalLicence(doctor.getMedicalLicence()).orElseThrow();

        //then
        Assertions.assertNotNull(foundDoctor);
        Assertions.assertEquals(doctor.getMedicalLicence(), foundDoctor.getMedicalLicence());
    }

    @Test
    void findByMedicalLicenceWhenNotExists() {
        //given
        Doctor doctor = DoctorFixture.someDoctor();

        Mockito.when(doctorJpaRepository.findByMedicalLicence(ArgumentMatchers.any())).thenReturn(Optional.empty());

        //when
        Optional<Doctor> foundDoctor = doctorRepository.findByMedicalLicence(doctor.getMedicalLicence());

        //then
        Assertions.assertTrue(foundDoctor.isEmpty());
    }

    @Test
    void findAvailableSpecializationsWorksCorrectly() {
        //given
        String specialization = DoctorAvailableFixture.someDoctorAvailable().getDoctor().getSpecialization();
        Set<String> specializations = Set.of(specialization);

        Mockito.when(doctorJpaRepository.findAvailableSpecializations()).thenReturn(specializations);

        //when
        List<String> result = doctorRepository.findAvailableSpecializations();

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(new ArrayList<>(specializations), result);
    }

//    @Test
//    void createAndSaveNewDoctorWorksCorrectly() {
//        //given
//        Doctor doctor = DoctorFixture.someDoctor();
//        DoctorEntity doctorEntity = DoctorFixture.someDoctorEntity();
//        Mockito.when(doctorEntityMapper.mapToEntity(ArgumentMatchers.any())).thenReturn(doctorEntity);
//
//        //when
//        doctorRepository.createAndSaveNewDoctor(doctor, userEntity);
//
//        //then
//        Mockito.verify(doctorJpaRepository, Mockito.times(1)).saveAndFlush(doctorEntity);
//    }
}