package org.medical_appointment.api.DTO.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.medical_appointment.api.DTO.DoctorAvailableDTO;
import org.medical_appointment.domain.DoctorAvailable;
import org.medical_appointment.util.DoctorAvailableFixture;

class DoctorAvailableMapperTest {
    private DoctorAvailableMapper doctorAvailableMapper;

    @BeforeEach
    void setUp() {
        doctorAvailableMapper = new DoctorAvailableMapperImpl();
    }

    @Test
    void mapToDoctorAvailableDTOWorksCorrectly() {
        //given
        DoctorAvailable doctorAvailable = DoctorAvailableFixture.someDoctorAvailable();

        //when
        DoctorAvailableDTO doctorAvailableDTO = doctorAvailableMapper.map(doctorAvailable);

        //then
        Assertions.assertNotNull(doctorAvailableDTO);
        Assertions.assertEquals(doctorAvailable.getDoctorAvailableId(), doctorAvailableDTO.getDoctorAvailableId());
        Assertions.assertEquals(doctorAvailable.getDoctor().getSpecialization(), doctorAvailableDTO.getDoctor().getSpecialization());
    }

}