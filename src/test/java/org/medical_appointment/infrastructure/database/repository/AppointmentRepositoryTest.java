package org.medical_appointment.infrastructure.database.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medical_appointment.domain.Appointment;
import org.medical_appointment.domain.Patient;
import org.medical_appointment.infrastructure.database.entity.AppointmentEntity;
import org.medical_appointment.infrastructure.database.entity.PatientEntity;
import org.medical_appointment.infrastructure.database.mapper.AppointmentEntityMapper;
import org.medical_appointment.infrastructure.database.mapper.PatientEntityMapper;
import org.medical_appointment.infrastructure.database.repository.jpa.AppointmentJpaRepository;
import org.medical_appointment.util.AppointmentFixture;
import org.medical_appointment.util.PatientFixture;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AppointmentRepositoryTest {

    @InjectMocks
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentJpaRepository appointmentJpaRepository;
    @Mock
    private PatientEntityMapper patientEntityMapper;
    @Mock
    private AppointmentEntityMapper appointmentEntityMapper;

    @Test
    void scheduleAppointmentWorksCorrectly() {
        //given
        Appointment appointment = AppointmentFixture.someAppointment();
        AppointmentEntity appointmentEntity = AppointmentFixture.someAppointmentEntity();
        Mockito.when(appointmentEntityMapper.mapToEntityWithoutIllness(ArgumentMatchers.any()))
                .thenReturn(appointmentEntity);

        //when
        appointmentRepository.scheduleAppointment(appointment);

        //then
        Mockito.verify(appointmentJpaRepository, Mockito.times(1)).saveAndFlush(appointmentEntity);
    }

    @Test
    void updateAppointmentWorksCorrectly() {
        //given
        Appointment appointment = AppointmentFixture.someAppointment();
        AppointmentEntity appointmentEntity = AppointmentFixture.someAppointmentEntity();
        Mockito.when(appointmentEntityMapper.mapToEntity(ArgumentMatchers.any()))
                .thenReturn(appointmentEntity);

        //when
        appointmentRepository.updateAppointment(appointment);

        //then
        Mockito.verify(appointmentJpaRepository, Mockito.times(1)).saveAndFlush(appointmentEntity);
    }

    @Test
    void cancelReservationWorksCorrectly() {
        //given
        Appointment appointment = AppointmentFixture.someAppointment();
        AppointmentEntity appointmentEntity = AppointmentFixture.someAppointmentEntity();
        appointmentEntity.setCanceled(false);
        appointmentEntity.getDoctorAvailable().setReserved(true);

        Mockito.when(appointmentEntityMapper.mapToEntityWithoutIllness(ArgumentMatchers.any()))
                .thenReturn(appointmentEntity);

        //when
        appointmentRepository.cancelReservation(appointment);

        //then
        Mockito.verify(appointmentJpaRepository, Mockito.times(1)).saveAndFlush(appointmentEntity);
        Assertions.assertTrue(appointmentEntity.getCanceled());
        Assertions.assertFalse(appointmentEntity.getDoctorAvailable().getReserved());
    }

    @Test
    void findByIdWorksCorrectly() {
        //given
        Appointment appointment = AppointmentFixture.someAppointment();
        AppointmentEntity appointmentEntity = AppointmentFixture.someAppointmentEntity();

        Mockito.when(appointmentJpaRepository.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(appointmentEntity));
        Mockito.when(appointmentEntityMapper.mapFromEntityWithoutIllness(ArgumentMatchers.any()))
                .thenReturn(appointment);

        //when
        Optional<Appointment> foundAppointment = appointmentRepository.findById(appointment.getAppointmentId());

        //then
        Assertions.assertEquals(Optional.of(appointment), foundAppointment);
        Mockito.verify(appointmentJpaRepository, Mockito.times(1)).findById(appointment.getAppointmentId());
        Mockito.verify(appointmentEntityMapper, Mockito.times(1)).mapFromEntityWithoutIllness(appointmentEntity);
    }

    @Test
    void findAllCompletedAppointmentsForPatientWorksCorrectly() {
        //given
        Patient patient = PatientFixture.somePatient();
        PatientEntity patientEntity = PatientFixture.somePatientEntity();
        AppointmentEntity appointmentEntity1 = AppointmentFixture.someAppointmentEntity();
        AppointmentEntity appointmentEntity2 = AppointmentFixture.someAppointmentEntity();
        appointmentEntity1.setCompleted(true);
        appointmentEntity1.setCompleted(false);
        List<AppointmentEntity> appointmentsList = List.of(appointmentEntity1, appointmentEntity2);

        Mockito.when(patientEntityMapper.mapToEntity(ArgumentMatchers.any()))
                .thenReturn(patientEntity);
        Mockito.when(appointmentJpaRepository.findAllCompletedAppointmentsForPatient(ArgumentMatchers.any()))
                .thenReturn(appointmentsList.stream().filter(value -> value.getCompleted()).toList());

        //when
        List<Appointment> resultList = appointmentRepository.findAllCompletedAppointmentsForPatient(patient);

        //then
        Assertions.assertEquals(1, resultList.size());
    }

    @Test
    void findAppointmentByDoctorAvailableIdWorksCorrectly() {
        //given
        Appointment appointment = AppointmentFixture.someAppointment();
        AppointmentEntity appointmentEntity = AppointmentFixture.someAppointmentEntity();

        Mockito.when(appointmentJpaRepository.findAppointmentByDoctorAvailableId(ArgumentMatchers.any()))
                .thenReturn(Optional.of(appointmentEntity));
        Mockito.when(appointmentEntityMapper.mapFromEntityWithoutIllness(ArgumentMatchers.any()))
                .thenReturn(appointment);

        //when
        Optional<Appointment> foundAppointment = appointmentRepository.findAppointmentByDoctorAvailableId(appointment.getAppointmentId());

        //then
        Assertions.assertEquals(Optional.of(appointment), foundAppointment);
        Mockito.verify(appointmentJpaRepository, Mockito.times(1)).findAppointmentByDoctorAvailableId(appointment.getAppointmentId());
        Mockito.verify(appointmentEntityMapper, Mockito.times(1)).mapFromEntityWithoutIllness(appointmentEntity);
    }

    @Test
    void findAllUncompletedAppointmentsForPatientWorksCorrectly() {
        //given
        PatientEntity patientEntity = PatientFixture.somePatientEntity();
        Patient patient = PatientFixture.somePatient();
        AppointmentEntity appointmentEntity1 = AppointmentFixture.someAppointmentEntity();
        AppointmentEntity appointmentEntity2 = AppointmentFixture.someAppointmentEntity();
        appointmentEntity1.setCompleted(true);
        appointmentEntity2.setCompleted(false);

        List<AppointmentEntity> appointmentsList = List.of(appointmentEntity1, appointmentEntity2);
        Mockito.when(patientEntityMapper.mapToEntity(ArgumentMatchers.any()))
                .thenReturn(patientEntity);
        Mockito.when(appointmentJpaRepository.findAllUncompletedAppointmentsByPesel(ArgumentMatchers.any()))
                .thenReturn(appointmentsList.stream().filter(value -> !value.getCompleted()).toList());

        //when
        List<Appointment> result = appointmentRepository.findAllUncompletedAppointmentsForPatient(patient);

        //then
        Assertions.assertEquals(1, result.size());
        Mockito.verify(patientEntityMapper, Mockito.times(1)).mapToEntity(patient);
        Mockito.verify(appointmentJpaRepository, Mockito.times(1))
                .findAllUncompletedAppointmentsByPesel(patientEntity);
    }
}