package org.medical_appointment.infrastructure.database.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.medical_appointment.domain.Doctor;
import org.medical_appointment.infrastructure.database.entity.DoctorEntity;
import org.medical_appointment.util.DoctorFixture;

public class DoctorEntityMapperTest {

    private DoctorEntityMapper doctorEntityMapper;

    @BeforeEach
    void setUp() {
        doctorEntityMapper = new DoctorEntityMapperImpl();
    }


    @Test
    void mapFromEntityToDoctorCorrectly() {
        //given
        DoctorEntity doctorEntity = DoctorFixture.someDoctorEntity();

        //when
        Doctor doctor = doctorEntityMapper.mapFromEntity(doctorEntity);

        //then
        Assertions.assertNotNull(doctor);
        Assertions.assertEquals(doctorEntity.getMedicalLicence(), doctor.getMedicalLicence());
        Assertions.assertEquals(doctorEntity.getDoctorId(), doctor.getDoctorId());
        Assertions.assertEquals(doctorEntity.getSurname(), doctor.getSurname());
        Assertions.assertEquals(doctorEntity.getSpecialization(), doctor.getSpecialization());
    }

    @Test
    void mapToEntityFromDoctorCorrectly() {
        //given
        Doctor doctor = DoctorFixture.someDoctor();

        //when
        DoctorEntity doctorEntity = doctorEntityMapper.mapToEntity(doctor);

        //then
        Assertions.assertNotNull(doctorEntity);
        Assertions.assertEquals(doctor.getMedicalLicence(), doctorEntity.getMedicalLicence());
        Assertions.assertEquals(doctor.getDoctorId(), doctorEntity.getDoctorId());
        Assertions.assertEquals(doctor.getSurname(), doctorEntity.getSurname());
        Assertions.assertEquals(doctor.getSpecialization(), doctorEntity.getSpecialization());
    }
}
