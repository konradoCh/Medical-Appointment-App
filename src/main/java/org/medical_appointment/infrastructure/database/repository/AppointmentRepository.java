package org.medical_appointment.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.medical_appointment.business.dao.AppointmentDAO;
import org.medical_appointment.domain.Appointment;
import org.medical_appointment.domain.Patient;
import org.medical_appointment.infrastructure.database.entity.AppointmentEntity;
import org.medical_appointment.infrastructure.database.entity.DoctorAvailableEntity;
import org.medical_appointment.infrastructure.database.entity.PatientEntity;
import org.medical_appointment.infrastructure.database.mapper.AppointmentEntityMapper;
import org.medical_appointment.infrastructure.database.mapper.PatientEntityMapper;
import org.medical_appointment.infrastructure.database.repository.jpa.AppointmentJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AppointmentRepository implements AppointmentDAO {

    private final AppointmentJpaRepository appointmentJpaRepository;
    private final PatientEntityMapper patientEntityMapper;
    private final AppointmentEntityMapper appointmentEntityMapper;

    @Override
    public void scheduleAppointment(Appointment appointment) {
        AppointmentEntity appointmentEntity = appointmentEntityMapper.mapToEntityWithoutIllness(appointment);
        appointmentJpaRepository.saveAndFlush(appointmentEntity);
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        AppointmentEntity appointmentEntity = appointmentEntityMapper.mapToEntity(appointment);
        appointmentJpaRepository.saveAndFlush(appointmentEntity);
    }

    @Override
    public void cancelReservation(Appointment appointment) {
        AppointmentEntity appointmentEntity = appointmentEntityMapper.mapToEntityWithoutIllness(appointment);
        appointmentEntity.setCanceled(true);
        appointmentJpaRepository.saveAndFlush(appointmentEntity);

        DoctorAvailableEntity doctorAvailableEntity = appointmentEntity.getDoctorAvailable();
        doctorAvailableEntity.setReserved(false);
    }

    @Override
    public Optional<Appointment> findById(Integer appointmentId) {
        return appointmentJpaRepository.findById(appointmentId)
                .map(appointmentEntityMapper::mapFromEntityWithoutIllness);
    }

    @Override
    public List<Appointment> findAllCompletedAppointmentsForPatient(Patient patient) {
        PatientEntity patientEntity = patientEntityMapper.mapToEntity(patient);
        List<AppointmentEntity> appointmentEntityList = appointmentJpaRepository.findAllCompletedAppointmentsForPatient(patientEntity);

        return appointmentEntityList.stream()
                .map(appointmentEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Optional<Appointment> findAppointmentByDoctorAvailableId(Integer doctorAvailableId) {
        return appointmentJpaRepository.findAppointmentByDoctorAvailableId(doctorAvailableId)
                .map(appointmentEntityMapper::mapFromEntityWithoutIllness);
    }

    @Override
    public List<Appointment> findAllUncompletedAppointmentsForPatient(Patient patient) {
        PatientEntity patientEntity = patientEntityMapper.mapToEntity(patient);
        List<AppointmentEntity> appointmentEntityList = appointmentJpaRepository.findAllUncompletedAppointmentsByPesel(patientEntity);

        return appointmentEntityList.stream()
                .map(appointmentEntityMapper::mapFromEntityWithoutIllness)
                .toList();
    }
}