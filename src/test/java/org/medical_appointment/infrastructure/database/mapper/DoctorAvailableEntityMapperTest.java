package org.medical_appointment.infrastructure.database.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.medical_appointment.domain.DoctorAvailable;
import org.medical_appointment.infrastructure.database.entity.DoctorAvailableEntity;
import org.medical_appointment.util.DoctorAvailableFixture;

class DoctorAvailableEntityMapperTest {

    private DoctorAvailableEntityMapper doctorAvailableEntityMapper;

    @BeforeEach
    void setUp() {
        doctorAvailableEntityMapper = new DoctorAvailableEntityMapperImpl();
    }

    @Test
    void mapToEntityFromDoctorAvailableCorrectly() {
        //given
        DoctorAvailable doctorAvailable = DoctorAvailableFixture.someDoctorAvailable();

        //when
        DoctorAvailableEntity doctorAvailableEntity = doctorAvailableEntityMapper.mapToEntity(doctorAvailable);

        //then
        Assertions.assertNotNull(doctorAvailableEntity);
        Assertions.assertEquals(doctorAvailable.getDoctorAvailableId(), doctorAvailableEntity.getDoctorAvailableId());
        Assertions.assertEquals(doctorAvailable.getDateTime(), doctorAvailableEntity.getDateTime());
        Assertions.assertEquals(doctorAvailable.getDoctor().getMedicalLicence(), doctorAvailableEntity.getDoctor().getMedicalLicence());
        Assertions.assertEquals(doctorAvailable.getCompleted(), doctorAvailableEntity.getCompleted());
        Assertions.assertEquals(doctorAvailable.getDeleted(), doctorAvailableEntity.getDeleted());
        Assertions.assertEquals(doctorAvailable.getReserved(), doctorAvailableEntity.getReserved());
    }

    @Test
    void mapFromEntityToDoctorAvailableCorrectly() {
        //given
        DoctorAvailableEntity doctorAvailableEntity = DoctorAvailableFixture.someDoctorAvailableEntity();

        //when
        DoctorAvailable doctorAvailable = doctorAvailableEntityMapper.mapFromEntity(doctorAvailableEntity);

        //then
        Assertions.assertNotNull(doctorAvailable);
        Assertions.assertEquals(doctorAvailableEntity.getDoctorAvailableId(), doctorAvailable.getDoctorAvailableId());
        Assertions.assertEquals(doctorAvailableEntity.getDateTime(), doctorAvailable.getDateTime());
        Assertions.assertEquals(doctorAvailableEntity.getDoctor().getMedicalLicence(), doctorAvailable.getDoctor().getMedicalLicence());
        Assertions.assertEquals(doctorAvailableEntity.getCompleted(), doctorAvailable.getCompleted());
        Assertions.assertEquals(doctorAvailableEntity.getDeleted(), doctorAvailable.getDeleted());
        Assertions.assertEquals(doctorAvailableEntity.getReserved(), doctorAvailable.getReserved());
    }
}