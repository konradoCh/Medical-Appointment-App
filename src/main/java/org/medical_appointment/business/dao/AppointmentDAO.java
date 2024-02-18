package org.medical_appointment.business.dao;


import org.medical_appointment.domain.Appointment;
import org.medical_appointment.domain.Patient;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AppointmentDAO {

    @Transactional
    void scheduleAppointment(Appointment scheduledAppointment);


    @Transactional
    List<Appointment> findAllCompletedAppointmentsForPatient(Patient patient);

    Optional<Appointment> findById(Integer appointmentId);

    @Transactional
    void cancelReservation(Appointment appointment);

    Optional<Appointment> findAppointmentByDoctorAvailableId(Integer doctorAvailableId);

    List<Appointment> findAllUncompletedAppointmentsForPatient(Patient patient);

    void updateAppointment(Appointment appointment);
}
