package org.medical_appointment.business.dao;

import org.medical_appointment.domain.DoctorAvailable;
import org.medical_appointment.infrastructure.database.entity.DoctorAvailableEntity;

import java.util.List;
import java.util.Optional;

public interface DoctorAvailableDAO {

    void createAvailableDate(DoctorAvailableEntity doctorAvailable);

    List<DoctorAvailable> findBySpecialization(String specialization);

    List<DoctorAvailable> findByMedicalLicence(String medicalLicence);

    void deleteDoctorAvailable(DoctorAvailable doctorAvailable);

    Optional<DoctorAvailable> findById(Integer doctorAvailableId);

    void cancelReservation(DoctorAvailable doctorAvailable);

    void makeReservation(DoctorAvailable doctorAvailable);

    void setAsCompleted(Integer doctorAvailableId);
}
