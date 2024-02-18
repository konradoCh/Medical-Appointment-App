package org.medical_appointment.business;


import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.medical_appointment.business.dao.DoctorAvailableDAO;
import org.medical_appointment.domain.Appointment;
import org.medical_appointment.domain.DoctorAvailable;
import org.medical_appointment.infrastructure.database.entity.DoctorAvailableEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class DoctorAvailableService {

    private final DoctorAvailableDAO doctorAvailableDAO;

    @Transactional
    public List<DoctorAvailable> findBySpecialization(String specialization) {
        List<DoctorAvailable> availableAppointmentsBySpecialization = doctorAvailableDAO.findBySpecialization(specialization).stream()
                .filter(value -> value.getDeleted().equals(false))
                .toList();
        log.info("Available doctor dates: [{}] for specialization: [{}]",
                availableAppointmentsBySpecialization.size(),
                specialization);
        return availableAppointmentsBySpecialization;
    }

    @Transactional
    public List<DoctorAvailable> reservedDoctorAvailable(String medicalLicence) {
        List<DoctorAvailable> reservedDoctorAvailableList = doctorAvailableDAO.findByMedicalLicence(medicalLicence).stream()
                .filter(value -> value.getReserved().equals(true))
                .filter(value -> value.getCompleted().equals(false))
                .toList();
        log.info("Reserved doctor dates: [{}]", reservedDoctorAvailableList.size());
        return reservedDoctorAvailableList;
    }

    @Transactional
    public List<DoctorAvailable> notReservedDoctorAvailable(String medicalLicence) {
        List<DoctorAvailable> notReservedDoctorAvailableList = doctorAvailableDAO.findByMedicalLicence(medicalLicence).stream()
                .filter(value -> value.getReserved().equals(false))
                .toList();
        log.info("Not reserved doctor dates: [{}]", notReservedDoctorAvailableList.size());
        return notReservedDoctorAvailableList;
    }


    @Transactional
    public void createDoctorAvailableDate(DoctorAvailableEntity newAppointment) {
        log.info("Saved new doctor available on date: [{}] by doctor [{}] [{}]",
                newAppointment.getDateTime(),
                newAppointment.getDoctor().getName(),
                newAppointment.getDoctor().getSurname()
        );
        doctorAvailableDAO.createAvailableDate(newAppointment);
    }

    @Transactional
    public void deleteDoctorAvailableDate(Integer doctorAvailableId) {
        Optional<DoctorAvailable> doctorAvailable = doctorAvailableDAO.findById(doctorAvailableId);
        if (doctorAvailable.isEmpty()) {
            throw new EntityNotFoundException("Could not find doctor available by id: [%s]".formatted(doctorAvailableId));
        }
        log.info("Deleted available appointment.");
        doctorAvailableDAO.deleteDoctorAvailable(doctorAvailable.get());
    }


    public DoctorAvailable findById(Integer doctorAvailableId) {
        Optional<DoctorAvailable> doctorAvailable = doctorAvailableDAO.findById(doctorAvailableId);
        if (doctorAvailable.isEmpty()) {
            throw new EntityNotFoundException("Could not find doctor available by id: [%s]".formatted(doctorAvailableId));
        }
        return doctorAvailable.get();
    }

    @Transactional
    public void makeReservation(Appointment appointment) {
        DoctorAvailable doctorAvailable = appointment.getDoctorAvailable();
        log.info("Doctor available date on [{}] is reserved now.", doctorAvailable.getDateTime());
        doctorAvailableDAO.makeReservation(doctorAvailable);
    }

    @Transactional
    public void cancelReservation(Appointment appointment) {
        DoctorAvailable doctorAvailable = appointment.getDoctorAvailable();
        log.info("Doctor available date on [{}] is available now.", doctorAvailable.getDateTime());
        doctorAvailableDAO.cancelReservation(doctorAvailable);
    }

    @Transactional
    public void setAsCompletedDoctorAvailable(Integer doctorAvailableId) {
        doctorAvailableDAO.setAsCompleted(doctorAvailableId);
        log.info("Doctor available id: [{}] is completed now.", doctorAvailableId);
    }
}
