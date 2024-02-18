package org.medical_appointment.infrastructure.database.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.medical_appointment.domain.Patient;
import org.medical_appointment.infrastructure.database.entity.PatientEntity;
import org.medical_appointment.util.PatientFixture;

public class PatientEntityMapperTest {

    private PatientEntityMapper patientEntityMapper;

    @BeforeEach
    void setUp() {
        patientEntityMapper = new PatientEntityMapperImpl();
    }


    @Test
    void mapFromEntityToPatientCorrectly() {
        //given
        PatientEntity patientEntity = PatientFixture.somePatientEntity();

        //when
        Patient patient = patientEntityMapper.mapFromEntity(patientEntity);

        //then
        Assertions.assertNotNull(patient);
        Assertions.assertEquals(patientEntity.getPesel(), patient.getPesel());
        Assertions.assertEquals(patientEntity.getPatientId(), patient.getPatientId());
        Assertions.assertEquals(patientEntity.getPhone(), patient.getPhone());
        Assertions.assertEquals(patientEntity.getSurname(), patient.getSurname());
    }

    @Test
    void mapToEntityFromPatientCorrectly() {
        //given
        Patient patient = PatientFixture.somePatient();

        //when
        PatientEntity patientEntity = patientEntityMapper.mapToEntity(patient);

        //then
        Assertions.assertNotNull(patientEntity);
        Assertions.assertEquals(patient.getPesel(), patientEntity.getPesel());
        Assertions.assertEquals(patient.getPatientId(), patientEntity.getPatientId());
        Assertions.assertEquals(patient.getPhone(), patientEntity.getPhone());
        Assertions.assertEquals(patient.getSurname(), patientEntity.getSurname());
    }
}
