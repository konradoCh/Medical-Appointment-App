package org.medical_appointment.api.DTO.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.medical_appointment.api.DTO.AppointmentDTO;
import org.medical_appointment.domain.Appointment;
import org.medical_appointment.util.AppointmentFixture;

class AppointmentMapperTest {
    private AppointmentMapper appointmentMapper;

    @BeforeEach
    void setUp() {
        appointmentMapper = new AppointmentMapperImpl();
    }

    @Test
    void mapToAppointmentDTOWorksCorrectly() {
        //given
        Appointment appointment = AppointmentFixture.someAppointment();

        //when
        AppointmentDTO appointmentDTO = appointmentMapper.map(appointment);

        //then
        Assertions.assertNotNull(appointmentDTO);
        Assertions.assertEquals(appointment.getAppointmentId(), appointmentDTO.getAppointmentId());
        Assertions.assertEquals(appointment.getAppointmentNumber(), appointmentDTO.getAppointmentNumber());
        Assertions.assertEquals(appointment.getIllness(), appointmentDTO.getIllness());
    }

    @Test
    void mapUncompletedWorksCorrectly() {
        //given
        Appointment appointment = AppointmentFixture.someAppointment();
        appointment.withIllness(null);

        //when
        AppointmentDTO appointmentDTO = appointmentMapper.mapUncompleted(appointment);

        //then
        Assertions.assertNotNull(appointmentDTO);
        Assertions.assertEquals(appointment.getAppointmentId(), appointmentDTO.getAppointmentId());
        Assertions.assertEquals(appointment.getAppointmentNumber(), appointmentDTO.getAppointmentNumber());
        Assertions.assertNull(appointmentDTO.getIllness());
    }
}