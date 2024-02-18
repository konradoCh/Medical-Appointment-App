package org.medical_appointment.business;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.medical_appointment.business.dao.AppointmentDAO;
import org.medical_appointment.domain.Appointment;
import org.medical_appointment.domain.DoctorAvailable;
import org.medical_appointment.domain.Patient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class AppointmentService {

    private final PatientService patientService;
    private final AppointmentDAO appointmentDAO;
    private final DoctorAvailableService doctorAvailableService;

    @Transactional
    public void scheduleAppointment(String pesel, Integer doctorAvailableId) {
        Patient patient = patientService.findPatientByPesel(pesel);
        DoctorAvailable doctorAvailable = doctorAvailableService.findById(doctorAvailableId);

        Appointment scheduledAppointment = Appointment.builder()
                .appointmentNumber(UUID.randomUUID().toString().substring(0, 25))   //generator numerów
                .doctorAvailable(doctorAvailable)
                .completed(false)
                .canceled(false)
                .patient(patient)
                .build();

        appointmentDAO.scheduleAppointment(scheduledAppointment);
        doctorAvailableService.makeReservation(scheduledAppointment);
        log.info("Patient with pesel: [{}] reserved an appointment on [{}]",
                pesel,
                scheduledAppointment.getDoctorAvailable().getDateTime()
        );
    }

    @Transactional
    public void confirmAndAddDetailsForAppointment(Appointment appointment) {
        log.info("Confirmed and added details on appointment [{}]", appointment.getAppointmentNumber());
        appointmentDAO.updateAppointment(appointment);
    }

    @Transactional
    public void cancelReservation(Appointment appointment) {
        appointmentDAO.cancelReservation(appointment);
        doctorAvailableService.cancelReservation(appointment);
        log.info("Canceled reservation number [{}], date: [{}]",
                appointment.getAppointmentNumber(),
                appointment.getDoctorAvailable().getDateTime());
    }

    public Appointment findAppointmentById(Integer appointmentId) {
        Optional<Appointment> appointment = appointmentDAO.findById(appointmentId);
        if (appointment.isEmpty()) {
            throw new EntityNotFoundException("Could not find appointment by id: [%s]".formatted(appointmentId));
        }
        return appointment.get();
    }

    public Appointment findAppointmentByDoctorAvailableId(Integer doctorAvailableId) {
        Optional<Appointment> appointment = appointmentDAO.findAppointmentByDoctorAvailableId(doctorAvailableId);
        if (appointment.isEmpty()) {
            throw new EntityNotFoundException("Could not find appointment by id: [%s]".formatted(doctorAvailableId));
        }
        return appointment.get();
    }

    public List<Appointment> findAllUncompletedAppointmentsByPesel(String pesel) {
        Patient patient = patientService.findPatientByPesel(pesel);
        return appointmentDAO.findAllUncompletedAppointmentsForPatient(patient);
    }

    public List<Appointment> findAllCompletedAppointmentsByPesel(String pesel) {
        Patient patient = patientService.findPatientByPesel(pesel);
        return appointmentDAO.findAllCompletedAppointmentsForPatient(patient);
    }
}
