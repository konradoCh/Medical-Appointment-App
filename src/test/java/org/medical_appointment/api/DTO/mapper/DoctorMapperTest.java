package org.medical_appointment.api.DTO.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.medical_appointment.api.DTO.DoctorDTO;
import org.medical_appointment.domain.Doctor;
import org.medical_appointment.util.DoctorFixture;

class DoctorMapperTest {
    private DoctorMapper doctorMapper;

    @BeforeEach
    void setUp() {
        doctorMapper = new DoctorMapperImpl();
    }

    @Test
    void mapToDoctorDTOWorksCorrectly() {
        //given
        Doctor doctor = DoctorFixture.someDoctor();

        //when
        DoctorDTO doctorDTO = doctorMapper.map(doctor);

        //then
        Assertions.assertNotNull(doctorDTO);
        Assertions.assertEquals(doctor.getMedicalLicence(), doctorDTO.getMedicalLicence());
        Assertions.assertEquals(doctor.getSpecialization(), doctorDTO.getSpecialization());
        Assertions.assertEquals(doctor.getSurname(), doctorDTO.getSurname());
    }

    @Test
    void mapToDoctorWorksCorrectly() {
        //given
        DoctorDTO doctorDTO = DoctorFixture.newDoctorDTO();

        //when
        Doctor doctor = doctorMapper.map(doctorDTO);

        //then
        Assertions.assertNotNull(doctorDTO);
        Assertions.assertEquals(doctorDTO.getMedicalLicence(), doctor.getMedicalLicence());
        Assertions.assertEquals(doctorDTO.getSpecialization(), doctor.getSpecialization());
        Assertions.assertEquals(doctorDTO.getSurname(), doctor.getSurname());
    }

}