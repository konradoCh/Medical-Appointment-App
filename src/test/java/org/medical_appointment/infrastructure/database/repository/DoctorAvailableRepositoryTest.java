package org.medical_appointment.infrastructure.database.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medical_appointment.domain.DoctorAvailable;
import org.medical_appointment.infrastructure.database.entity.DoctorAvailableEntity;
import org.medical_appointment.infrastructure.database.mapper.DoctorAvailableEntityMapper;
import org.medical_appointment.infrastructure.database.repository.jpa.DoctorAvailableJpaRepository;
import org.medical_appointment.util.DoctorAvailableFixture;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DoctorAvailableRepositoryTest {

    @InjectMocks
    private DoctorAvailableRepository doctorAvailableRepository;

    @Mock
    private DoctorAvailableJpaRepository doctorAvailableJpaRepository;
    @Mock
    private DoctorAvailableEntityMapper doctorAvailableEntityMapper;

    @Test
    void createAvailableDateWorksCorrectly() {
        //given
        DoctorAvailableEntity doctorAvailableEntity = DoctorAvailableFixture.someDoctorAvailableEntity();

        //when
        doctorAvailableRepository.createAvailableDate(doctorAvailableEntity);

        //then
        Mockito.verify(doctorAvailableJpaRepository, Mockito.times(1))
                .saveAndFlush(doctorAvailableEntity);
    }

    @Test
    void findBySpecializationWorksCorrectly() {
        String specialization = "Cardiologist";

        DoctorAvailableEntity doctorAvailable1 = DoctorAvailableFixture.someDoctorAvailableEntity();
        doctorAvailable1.getDoctor().setSpecialization(specialization);
        DoctorAvailableEntity doctorAvailable2 = DoctorAvailableFixture.someDoctorAvailableEntity();
        doctorAvailable2.getDoctor().setSpecialization("Dermatologist");

        List<DoctorAvailableEntity> doctorAvailableList = List.of(doctorAvailable1, doctorAvailable2);

        Mockito.when(doctorAvailableJpaRepository.findBySpecialization(ArgumentMatchers.any()))
                .thenReturn(doctorAvailableList.stream()
                        .filter(value -> value.getDoctor().getSpecialization().equals(specialization)).toList());
        Mockito.when(doctorAvailableEntityMapper.mapFromEntity(doctorAvailable1))
                .thenReturn(DoctorAvailableFixture.someDoctorAvailable());

        //when
        List<DoctorAvailable> result = doctorAvailableRepository.findBySpecialization(specialization);

        //then
        Assertions.assertEquals(1, result.size());
        Mockito.verify(doctorAvailableJpaRepository, Mockito.times(1)).findBySpecialization(specialization);
        Mockito.verify(doctorAvailableEntityMapper, Mockito.times(1)).mapFromEntity(doctorAvailable1);
    }

    @Test
    void findByMedicalLicenceWorksCorrectly() {
        DoctorAvailableEntity doctorAvailable = DoctorAvailableFixture.someDoctorAvailableEntity();
        String medicalLicence = doctorAvailable.getDoctor().getMedicalLicence();
        List<DoctorAvailableEntity> list = List.of(doctorAvailable);

        Mockito.when(doctorAvailableJpaRepository.findByMedicalLicence(ArgumentMatchers.any()))
                .thenReturn(list);
        Mockito.when(doctorAvailableEntityMapper.mapFromEntity(ArgumentMatchers.any()))
                .thenReturn(DoctorAvailableFixture.someDoctorAvailable());

        //when
        List<DoctorAvailable> result = doctorAvailableRepository.findByMedicalLicence(medicalLicence);

        //then
        Assertions.assertEquals(1, result.size());
        Mockito.verify(doctorAvailableJpaRepository, Mockito.times(1)).findByMedicalLicence(medicalLicence);
    }

    @Test
    void deleteDoctorAvailableWorksCorrectly() {
        //given
        DoctorAvailableEntity doctorAvailableEntity = DoctorAvailableFixture.someDoctorAvailableEntity();
        DoctorAvailable doctorAvailable = DoctorAvailableFixture.someDoctorAvailable();
        Mockito.when(doctorAvailableJpaRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(doctorAvailableEntity));

        //when
        doctorAvailableRepository.deleteDoctorAvailable(doctorAvailable);

        //then
        Mockito.verify(doctorAvailableJpaRepository, Mockito.times(1)).saveAndFlush(doctorAvailableEntity);
        Assertions.assertTrue(doctorAvailableEntity.getDeleted());
    }

    @Test
    void makeReservationWorksCorrectly() {
        //given
        DoctorAvailableEntity doctorAvailableEntity = DoctorAvailableFixture.someDoctorAvailableEntity();
        doctorAvailableEntity.setReserved(false);
        DoctorAvailable doctorAvailable = DoctorAvailableFixture.someDoctorAvailable();
        Mockito.when(doctorAvailableJpaRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(doctorAvailableEntity));

        //when
        doctorAvailableRepository.makeReservation(doctorAvailable);

        //then
        Mockito.verify(doctorAvailableJpaRepository, Mockito.times(1)).saveAndFlush(doctorAvailableEntity);
        Assertions.assertTrue(doctorAvailableEntity.getReserved());
    }

    @Test
    void setAsCompletedWorksCorrectly() {
        //given
        DoctorAvailableEntity doctorAvailableEntity = DoctorAvailableFixture.someDoctorAvailableEntity();
        doctorAvailableEntity.setCompleted(false);
        DoctorAvailable doctorAvailable = DoctorAvailableFixture.someDoctorAvailable();
        Mockito.when(doctorAvailableJpaRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(doctorAvailableEntity));

        //when
        doctorAvailableRepository.setAsCompleted(doctorAvailable.getDoctorAvailableId());

        //then
        Mockito.verify(doctorAvailableJpaRepository, Mockito.times(1)).saveAndFlush(doctorAvailableEntity);
        Assertions.assertTrue(doctorAvailableEntity.getCompleted());
    }

    @Test
    void cancelReservationWorksCorrectly() {
        //given
        DoctorAvailableEntity doctorAvailableEntity = DoctorAvailableFixture.someDoctorAvailableEntity();
        doctorAvailableEntity.setReserved(true);
        DoctorAvailable doctorAvailable = DoctorAvailableFixture.someDoctorAvailable();
        Mockito.when(doctorAvailableJpaRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(doctorAvailableEntity));

        //when
        doctorAvailableRepository.cancelReservation(doctorAvailable);

        //then
        Mockito.verify(doctorAvailableJpaRepository, Mockito.times(2)).saveAndFlush(doctorAvailableEntity);
        Assertions.assertFalse(doctorAvailableEntity.getReserved());
    }

    @Test
    void findByIdWhenDoctorAvailableExists() {
        //given
        DoctorAvailable doctorAvailable = DoctorAvailableFixture.someDoctorAvailable();
        DoctorAvailableEntity doctorAvailableEntity = DoctorAvailableFixture.someDoctorAvailableEntity();

        Mockito.when(doctorAvailableEntityMapper.mapFromEntity(ArgumentMatchers.any())).thenReturn(doctorAvailable);
        Mockito.when(doctorAvailableJpaRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(doctorAvailableEntity));

        //when
        DoctorAvailable foundDoctorAvailable = doctorAvailableRepository.findById(doctorAvailable.getDoctorAvailableId()).orElseThrow();

        //then
        Assertions.assertNotNull(foundDoctorAvailable);
        Assertions.assertEquals(doctorAvailable.getDoctorAvailableId(), foundDoctorAvailable.getDoctorAvailableId());
    }

    @Test
    void findByIdWhenDoctorAvailableNotExists() {
        //given
        DoctorAvailable doctorAvailable = DoctorAvailableFixture.someDoctorAvailable();
        Integer doctorAvailableId = doctorAvailable.getDoctorAvailableId();
        Mockito.when(doctorAvailableJpaRepository.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.empty());

        //then, when
        Optional<DoctorAvailable> result = doctorAvailableRepository.findById(doctorAvailableId);

        // when
        Assertions.assertTrue(result.isEmpty());
    }
}