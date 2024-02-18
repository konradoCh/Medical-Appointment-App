package org.medical_appointment.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import org.medical_appointment.business.dao.DoctorAvailableDAO;
import org.medical_appointment.domain.DoctorAvailable;
import org.medical_appointment.infrastructure.database.entity.DoctorAvailableEntity;
import org.medical_appointment.infrastructure.database.mapper.DoctorAvailableEntityMapper;
import org.medical_appointment.infrastructure.database.repository.jpa.DoctorAvailableJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class DoctorAvailableRepository implements DoctorAvailableDAO {

    private final DoctorAvailableJpaRepository doctorAvailableJpaRepository;
    private final DoctorAvailableEntityMapper doctorAvailableEntityMapper;

    @Override
    public void createAvailableDate(DoctorAvailableEntity doctorAvailable) {
        doctorAvailableJpaRepository.saveAndFlush(doctorAvailable);
    }

    @Override
    public List<DoctorAvailable> findBySpecialization(String specialization) {
        return doctorAvailableJpaRepository.findBySpecialization(specialization)
                .stream()
                .map(doctorAvailableEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public List<DoctorAvailable> findByMedicalLicence(String medicalLicence) {
        List<DoctorAvailableEntity> byMedicalLicence = doctorAvailableJpaRepository.findByMedicalLicence(medicalLicence);
        return byMedicalLicence
                .stream()
                .map(doctorAvailableEntityMapper::mapFromEntity)
                .filter(value -> value.getDeleted().equals(false))
                .toList();
    }

    @Override
    public void deleteDoctorAvailable(DoctorAvailable doctorAvailable) {
        DoctorAvailableEntity entity = doctorAvailableJpaRepository.findById(doctorAvailable.getDoctorAvailableId()).orElseThrow();
        entity.setDeleted(true);
        doctorAvailableJpaRepository.saveAndFlush(entity);
    }

    @Override
    public void makeReservation(DoctorAvailable doctorAvailable) {
        DoctorAvailableEntity entity = doctorAvailableJpaRepository.findById(doctorAvailable.getDoctorAvailableId()).orElseThrow();
        entity.setReserved(true);
        doctorAvailableJpaRepository.saveAndFlush(entity);
    }

    @Override
    public void setAsCompleted(Integer doctorAvailableId) {
        DoctorAvailableEntity entity = doctorAvailableJpaRepository.findById(doctorAvailableId).orElseThrow();
        entity.setCompleted(true);
        doctorAvailableJpaRepository.saveAndFlush(entity);
    }

    @Override
    public void cancelReservation(DoctorAvailable doctorAvailable) {
        DoctorAvailableEntity doctorAvailableEntity = doctorAvailableJpaRepository.findById(doctorAvailable.getDoctorAvailableId()).orElseThrow();
        DoctorAvailableEntity doctorAvailableEntityCopy = createCopy(doctorAvailableEntity);

        doctorAvailableJpaRepository.saveAndFlush(doctorAvailableEntityCopy);
        doctorAvailableEntity.setReserved(false);
        doctorAvailableEntity.setDeleted(true);
        doctorAvailableJpaRepository.saveAndFlush(doctorAvailableEntity);
    }

    private static DoctorAvailableEntity createCopy(DoctorAvailableEntity doctorAvailableEntity) {
        DoctorAvailableEntity doctorAvailableEntityCopy = new DoctorAvailableEntity();
        doctorAvailableEntityCopy.setDateTime(doctorAvailableEntity.getDateTime());
        doctorAvailableEntityCopy.setReserved(false);
        doctorAvailableEntityCopy.setDeleted(doctorAvailableEntity.getDeleted());
        doctorAvailableEntityCopy.setCompleted(doctorAvailableEntity.getCompleted());
        doctorAvailableEntityCopy.setDoctor(doctorAvailableEntity.getDoctor());
        return doctorAvailableEntityCopy;
    }

    @Override
    public Optional<DoctorAvailable> findById(Integer doctorAvailableId) {
        return doctorAvailableJpaRepository.findById(doctorAvailableId)
                .map(doctorAvailableEntityMapper::mapFromEntity);
    }
}
