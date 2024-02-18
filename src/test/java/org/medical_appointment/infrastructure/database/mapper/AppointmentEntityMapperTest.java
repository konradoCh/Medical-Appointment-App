package org.medical_appointment.infrastructure.database.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.medical_appointment.domain.Appointment;
import org.medical_appointment.infrastructure.database.entity.AppointmentEntity;
import org.medical_appointment.util.AppointmentFixture;

class AppointmentEntityMapperTest {

    private AppointmentEntityMapper appointmentEntityMapper;

    @BeforeEach
    void setUp() {
        appointmentEntityMapper = new AppointmentEntityMapperImpl();
    }

    @Test
    void mapFromEntityToAppointmentCorrectly() {
        //given
        AppointmentEntity appointmentEntity = AppointmentFixture.someAppointmentEntity();

        //when
        Appointment appointment = appointmentEntityMapper.mapFromEntity(appointmentEntity);

        //then
        Assertions.assertNotNull(appointment);
        Assertions.assertEquals(appointmentEntity.getAppointmentId(), appointment.getAppointmentId());
        Assertions.assertEquals(appointmentEntity.getAppointmentNumber(), appointment.getAppointmentNumber());
        Assertions.assertEquals(appointmentEntity.getDescription(), appointment.getDescription());
        Assertions.assertEquals(appointmentEntity.getPatient().getPesel(), appointment.getPatient().getPesel());
        Assertions.assertEquals(appointmentEntity.getDoctorAvailable().getDoctor().getMedicalLicence(), appointment.getDoctorAvailable().getDoctor().getMedicalLicence());
        Assertions.assertEquals(appointmentEntity.getIllness(), appointment.getIllness());
        Assertions.assertEquals(appointmentEntity.getCompleted(), appointment.getCompleted());
        Assertions.assertEquals(appointmentEntity.getCanceled(), appointment.getCanceled());
    }

    @Test
    void mapToEntityFromPatientWhenScheduledAppointmentCorrectly() {
        //given
        Appointment appointment = AppointmentFixture.someAppointment();

        //when
        AppointmentEntity appointmentEntity = appointmentEntityMapper.mapToEntity(appointment);

        //then
        Assertions.assertNotNull(appointmentEntity);
        Assertions.assertEquals(appointment.getAppointmentId(), appointmentEntity.getAppointmentId());
        Assertions.assertEquals(appointment.getAppointmentNumber(), appointmentEntity.getAppointmentNumber());
        Assertions.assertEquals(appointment.getDescription(), appointmentEntity.getDescription());
        Assertions.assertEquals(appointment.getPatient().getPesel(), appointmentEntity.getPatient().getPesel());
        Assertions.assertEquals(appointment.getDoctorAvailable().getDoctor().getMedicalLicence(), appointmentEntity.getDoctorAvailable().getDoctor().getMedicalLicence());
        Assertions.assertEquals(appointment.getIllness(), appointmentEntity.getIllness());
        Assertions.assertEquals(appointment.getCompleted(), appointmentEntity.getCompleted());
        Assertions.assertEquals(appointment.getCanceled(), appointmentEntity.getCanceled());
    }

    @Test
    void mapFromEntityToAppointmentWithoutIllnessCorrectly() {
        //given
        AppointmentEntity appointmentEntity = AppointmentFixture.someAppointmentEntity();

        //when
        Appointment appointment = appointmentEntityMapper.mapFromEntityWithoutIllness(appointmentEntity);

        //then
        Assertions.assertNotNull(appointment);
        Assertions.assertEquals(appointmentEntity.getAppointmentId(), appointment.getAppointmentId());
        Assertions.assertEquals(appointmentEntity.getAppointmentNumber(), appointment.getAppointmentNumber());
        Assertions.assertEquals(appointmentEntity.getDescription(), appointment.getDescription());
        Assertions.assertEquals(appointmentEntity.getPatient().getPesel(), appointment.getPatient().getPesel());
        Assertions.assertEquals(appointmentEntity.getDoctorAvailable().getDoctor().getMedicalLicence(), appointment.getDoctorAvailable().getDoctor().getMedicalLicence());
        Assertions.assertEquals(null, appointment.getIllness());
        Assertions.assertEquals(appointmentEntity.getCompleted(), appointment.getCompleted());
        Assertions.assertEquals(appointmentEntity.getCanceled(), appointment.getCanceled());
    }

    @Test
    void mapToEntityFromPatientWhenScheduledAppointmentWithoutIllnessCorrectly() {
        //given
        Appointment appointment = AppointmentFixture.someAppointment();

        //when
        AppointmentEntity appointmentEntity = appointmentEntityMapper.mapToEntityWithoutIllness(appointment);

        //then
        Assertions.assertNotNull(appointmentEntity);
        Assertions.assertEquals(appointment.getAppointmentId(), appointmentEntity.getAppointmentId());
        Assertions.assertEquals(appointment.getAppointmentNumber(), appointmentEntity.getAppointmentNumber());
        Assertions.assertEquals(appointment.getDescription(), appointmentEntity.getDescription());
        Assertions.assertEquals(appointment.getPatient().getPesel(), appointmentEntity.getPatient().getPesel());
        Assertions.assertEquals(appointment.getDoctorAvailable().getDoctor().getMedicalLicence(), appointmentEntity.getDoctorAvailable().getDoctor().getMedicalLicence());
        Assertions.assertEquals(null, appointmentEntity.getIllness());
        Assertions.assertEquals(appointment.getCompleted(), appointmentEntity.getCompleted());
        Assertions.assertEquals(appointment.getCanceled(), appointmentEntity.getCanceled());
    }

}