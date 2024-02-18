package org.medical_appointment.business;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medical_appointment.business.dao.DoctorAvailableDAO;
import org.medical_appointment.domain.Appointment;
import org.medical_appointment.domain.DoctorAvailable;
import org.medical_appointment.infrastructure.database.entity.DoctorAvailableEntity;
import org.medical_appointment.util.AppointmentFixture;
import org.medical_appointment.util.DoctorAvailableFixture;
import org.medical_appointment.util.DoctorFixture;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DoctorAvailableServiceTest {

    @InjectMocks
    private DoctorAvailableService doctorAvailableService;

    @Mock
    private DoctorAvailableDAO doctorAvailableDAO;


    @Test
    void findBySpecializationWorkCorrectly() {
        //given
        String specialization = "Cardiologist";
        DoctorAvailable doctorAvailable1 = DoctorAvailableFixture.someDoctorAvailable()
                .withDoctor(DoctorFixture.someDoctor().withSpecialization(specialization));
        DoctorAvailable doctorAvailable2 = DoctorAvailableFixture.someDoctorAvailable()
                .withDoctor(DoctorFixture.someDoctor().withSpecialization(specialization));
        DoctorAvailable doctorAvailable3 = DoctorAvailableFixture.someDoctorAvailable()
                .withDoctor(DoctorFixture.someDoctor().withSpecialization("Dermatologist"));

        List<DoctorAvailable> doctorList = List.of(doctorAvailable1, doctorAvailable2, doctorAvailable3);

        Mockito.when(doctorAvailableDAO.findBySpecialization(ArgumentMatchers.anyString()))
                .thenReturn(doctorList.stream().filter(value -> value.getDoctor().getSpecialization().equals(specialization)).toList());

        //when
        List<DoctorAvailable> resultList = doctorAvailableService.findBySpecialization(specialization);

        //then
        Mockito.verify(doctorAvailableDAO, Mockito.times(1))
                .findBySpecialization(ArgumentMatchers.any());
        Assertions.assertEquals(2, resultList.size());
    }

    @Test
    void reservedDoctorAvailableWorksCorrectly() {
        //given
        String medicalLicence = DoctorAvailableFixture.someDoctorAvailable().getDoctor().getMedicalLicence();
        DoctorAvailable doctorAvailable1 = DoctorAvailableFixture.someDoctorAvailable().withReserved(true).withCompleted(false);
        DoctorAvailable doctorAvailable2 = DoctorAvailableFixture.someDoctorAvailable().withReserved(true).withCompleted(false);
        ;
        DoctorAvailable doctorAvailable3 = DoctorAvailableFixture.someDoctorAvailable().withReserved(false).withCompleted(true);
        ;

        List<DoctorAvailable> doctorList = List.of(doctorAvailable1, doctorAvailable2, doctorAvailable3);

        Mockito.when(doctorAvailableDAO.findByMedicalLicence(ArgumentMatchers.anyString())).thenReturn(doctorList.stream()
                .filter(value -> value.getReserved().equals(true))
                .filter(value -> value.getCompleted().equals(false))
                .toList());

        //when
        List<DoctorAvailable> resultList = doctorAvailableService.reservedDoctorAvailable(medicalLicence);

        //then
        Mockito.verify(doctorAvailableDAO, Mockito.times(1))
                .findByMedicalLicence(ArgumentMatchers.any());
        Assertions.assertEquals(2, resultList.size());
    }

    @Test
    void notReservedDoctorAvailableWorksCorrectly() {
        //given
        String medicalLicence = DoctorAvailableFixture.someDoctorAvailable().getDoctor().getMedicalLicence();
        DoctorAvailable doctorAvailable1 = DoctorAvailableFixture.someDoctorAvailable().withReserved(true);
        DoctorAvailable doctorAvailable2 = DoctorAvailableFixture.someDoctorAvailable().withReserved(false);
        ;
        DoctorAvailable doctorAvailable3 = DoctorAvailableFixture.someDoctorAvailable().withReserved(false);
        ;

        List<DoctorAvailable> doctorList = List.of(doctorAvailable1, doctorAvailable2, doctorAvailable3);

        Mockito.when(doctorAvailableDAO.findByMedicalLicence(ArgumentMatchers.anyString())).thenReturn(doctorList.stream()
                .filter(value -> value.getReserved().equals(false))
                .toList());

        //when
        List<DoctorAvailable> resultList = doctorAvailableService.notReservedDoctorAvailable(medicalLicence);

        //then
        Mockito.verify(doctorAvailableDAO, Mockito.times(1))
                .findByMedicalLicence(ArgumentMatchers.any());
        Assertions.assertEquals(2, resultList.size());
    }

    @Test
    void createDoctorAvailableDateWorksCorrectly() {
        //given
        DoctorAvailableEntity doctorAvailableEntity = DoctorAvailableFixture.someDoctorAvailableEntity();

        //when
        doctorAvailableService.createDoctorAvailableDate(doctorAvailableEntity);

        //then
        Mockito.verify(doctorAvailableDAO, Mockito.times(1))
                .createAvailableDate(ArgumentMatchers.any());
    }

    @Test
    void findByIdWhenDoctorAvailableExists() {
        //given
        DoctorAvailable doctorAvailable = DoctorAvailableFixture.someDoctorAvailable();
        Integer doctorAvailableId = doctorAvailable.getDoctorAvailableId();
        Mockito.when(doctorAvailableDAO.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(doctorAvailable));

        //when
        DoctorAvailable foundDoctorAvailable = doctorAvailableService.findById(doctorAvailableId);

        //then
        Assertions.assertNotNull(foundDoctorAvailable);
        Assertions.assertEquals(doctorAvailable, foundDoctorAvailable);
    }

    @Test
    void findByIdWhenDoctorAvailableNotExists() {
        //given
        DoctorAvailable doctorAvailable = DoctorAvailableFixture.someDoctorAvailable();
        Integer doctorAvailableId = doctorAvailable.getDoctorAvailableId();
        Mockito.when(doctorAvailableDAO.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.empty());

        //then, when
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            doctorAvailableService.findById(doctorAvailableId);
        });
    }

    @Test
    void deleteDoctorAvailableDateWorksCorrectly() {
        //given
        DoctorAvailable doctorAvailable = DoctorAvailableFixture.someDoctorAvailable();
        Mockito.when(doctorAvailableDAO.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(doctorAvailable));

        //when
        doctorAvailableService.deleteDoctorAvailableDate(doctorAvailable.getDoctorAvailableId());

        //then
        Mockito.verify(doctorAvailableDAO, Mockito.times(1))
                .deleteDoctorAvailable(doctorAvailable);
    }

    @Test
    void makeReservationWorksCorrectly() {
        // Given
        Appointment appointment = AppointmentFixture.someAppointment();

        // When
        doctorAvailableService.makeReservation(appointment);

        // Then
        Mockito.verify(doctorAvailableDAO, Mockito.times(1))
                .makeReservation(appointment.getDoctorAvailable());
    }

    @Test
    void cancelReservationWorksCorrectly() {
        // Given
        Appointment appointment = AppointmentFixture.someAppointment();

        // When
        doctorAvailableService.cancelReservation(appointment);

        // Then
        Mockito.verify(doctorAvailableDAO, Mockito.times(1))
                .cancelReservation(appointment.getDoctorAvailable());
    }

    @Test
    void setAsCompletedDoctorAvailableWorksCorrectly() {
        // Given
        DoctorAvailable doctorAvailable = DoctorAvailableFixture.someDoctorAvailable();

        // When
        doctorAvailableService.setAsCompletedDoctorAvailable(doctorAvailable.getDoctorAvailableId());

        // Then
        Mockito.verify(doctorAvailableDAO, Mockito.times(1))
                .setAsCompleted(doctorAvailable.getDoctorAvailableId());
    }
}
