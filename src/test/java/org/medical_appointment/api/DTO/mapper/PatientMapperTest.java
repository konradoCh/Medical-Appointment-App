package org.medical_appointment.api.DTO.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.medical_appointment.api.DTO.PatientDTO;
import org.medical_appointment.domain.Patient;
import org.medical_appointment.util.PatientFixture;

class PatientMapperTest {
    private PatientMapper patientMapper;

    @BeforeEach
    void setUp() {
        patientMapper = new PatientMapperImpl();
    }

    @Test
    void mapToPatientDTOWorksCorrectly() {
        //given
        Patient patient = PatientFixture.somePatient();

        //when
        PatientDTO patientDTO = patientMapper.map(patient);

        //then
        Assertions.assertNotNull(patientDTO);
        Assertions.assertEquals(patient.getPesel(), patientDTO.getPesel());
        Assertions.assertEquals(patient.getEmail(), patientDTO.getEmail());
        Assertions.assertEquals(patient.getSurname(), patientDTO.getSurname());
    }

    @Test
    void mapToPatientWorksCorrectly() {
        //given
        PatientDTO patientDTO = PatientFixture.newPatientDTO();

        //when
        Patient patient = patientMapper.map(patientDTO);

        //then
        Assertions.assertNotNull(patient);
        Assertions.assertEquals(patientDTO.getPesel(), patient.getPesel());
        Assertions.assertEquals(patientDTO.getEmail(), patient.getEmail());
        Assertions.assertEquals(patientDTO.getSurname(), patient.getSurname());
    }

}