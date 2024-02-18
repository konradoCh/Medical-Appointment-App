package org.medical_appointment.business;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medical_appointment.business.dao.AppointmentDAO;
import org.medical_appointment.domain.Appointment;
import org.medical_appointment.domain.DoctorAvailable;
import org.medical_appointment.domain.Patient;
import org.medical_appointment.util.AppointmentFixture;
import org.medical_appointment.util.DoctorAvailableFixture;
import org.medical_appointment.util.PatientFixture;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private PatientService patientService;

    @Mock
    private AppointmentDAO appointmentDAO;

    @Mock
    private DoctorAvailableService doctorAvailableService;

    @Test
    void scheduleAppointmentWorksCorrectly() {
        //given
        Patient patient = PatientFixture.somePatient();
        DoctorAvailable doctorAvailable = DoctorAvailableFixture.someDoctorAvailable();
        Mockito.when(patientService.findPatientByPesel(ArgumentMatchers.anyString()))
                .thenReturn(patient);
        Mockito.when(doctorAvailableService.findById(ArgumentMatchers.anyInt()))
                .thenReturn(doctorAvailable);

        //when
        appointmentService.scheduleAppointment(patient.getPesel(), doctorAvailable.getDoctorAvailableId());

        //then
        Mockito.verify(appointmentDAO, Mockito.times(1))
                .scheduleAppointment(ArgumentMatchers.any());
    }

    @Test
    void confirmAndAddDetailsForAppointmentWorksCorrectly() {
        //given
        Appointment appointment = AppointmentFixture.someAppointment();

        //when
        appointmentService.confirmAndAddDetailsForAppointment(appointment);

        //then
        Mockito.verify(appointmentDAO, Mockito.times(1))
                .updateAppointment(ArgumentMatchers.any());
    }

    @Test
    void cancelReservationWorksCorrectly() {
        //given
        Appointment appointment = AppointmentFixture.someAppointment();

        //when
        appointmentService.cancelReservation(appointment);

        //then
        Mockito.verify(appointmentDAO, Mockito.times(1))
                .cancelReservation(ArgumentMatchers.any());
        Mockito.verify(doctorAvailableService, Mockito.times(1))
                .cancelReservation(ArgumentMatchers.any());
    }

    @Test
    void findAppointmentByIdWhenAppointmentExists() {
        //given
        Appointment appointment = AppointmentFixture.someAppointment();
        Mockito.when(appointmentDAO.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(appointment));

        //when
        Appointment foundAppointment = appointmentService.findAppointmentById(appointment.getAppointmentId());

        //then
        Assertions.assertNotNull(foundAppointment);
        Assertions.assertEquals(appointment, foundAppointment);
    }

    @Test
    void findAppointmentByIdWhenAppointmentNotExists() {
        //given
        Appointment appointment = AppointmentFixture.someAppointment();
        Mockito.when(appointmentDAO.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.empty());

        //when, then
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            appointmentService.findAppointmentById(appointment.getAppointmentId());
        });
    }

    @Test
    void findAppointmentByDoctorAvailableIdWhenAppointmentExists() {
        //given
        Appointment appointment = AppointmentFixture.someAppointment();
        Mockito.when(appointmentDAO.findAppointmentByDoctorAvailableId(ArgumentMatchers.any()))
                .thenReturn(Optional.of(appointment));

        //when
        Appointment foundAppointment = appointmentService.findAppointmentByDoctorAvailableId(appointment.getAppointmentId());

        //then
        Assertions.assertNotNull(foundAppointment);
        Assertions.assertEquals(appointment, foundAppointment);
    }

    @Test
    void findAppointmentByDoctorAvailableIdWhenAppointmentNotExists() {
        //given
        Appointment appointment = AppointmentFixture.someAppointment();
        Mockito.when(appointmentDAO.findAppointmentByDoctorAvailableId(ArgumentMatchers.any()))
                .thenReturn(Optional.empty());

        //when, then
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            appointmentService.findAppointmentByDoctorAvailableId(appointment.getAppointmentId());
        });
    }

    @Test
    void findAllUncompletedAppointmentsByPeselWorksCorrectly() {
        //given
        Patient patient = PatientFixture.somePatient();
        Mockito.when(patientService.findPatientByPesel(ArgumentMatchers.anyString()))
                .thenReturn(patient);

        //when
        appointmentService.findAllUncompletedAppointmentsByPesel(patient.getPesel());

        //then
        Mockito.verify(appointmentDAO, Mockito.times(1))
                .findAllUncompletedAppointmentsForPatient(patient);
    }

    @Test
    void findAllCompletedAppointmentsByPeselWorksCorrectly() {
        //given
        Patient patient = PatientFixture.somePatient();
        Mockito.when(patientService.findPatientByPesel(ArgumentMatchers.anyString()))
                .thenReturn(patient);

        //when
        appointmentService.findAllCompletedAppointmentsByPesel(patient.getPesel());

        //then
        Mockito.verify(appointmentDAO, Mockito.times(1))
                .findAllCompletedAppointmentsForPatient(patient);
    }
}