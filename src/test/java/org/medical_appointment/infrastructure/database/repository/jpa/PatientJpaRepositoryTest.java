package org.medical_appointment.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.medical_appointment.infrastructure.database.entity.PatientEntity;
import org.medical_appointment.infrastructure.database.entity.UserEntity;
import org.medical_appointment.integration.configuration.PersistenceContainerTestConfiguration;
import org.medical_appointment.util.PatientFixture;
import org.medical_appointment.util.UserFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PatientJpaRepositoryTest {

    private PatientJpaRepository patientJpaRepository;
    private UserJpaRepository userJpaRepository;

    @Test
    void findPatientByPeselWhenExist() {
        //given
        UserEntity userEntity = UserFixture.someUserEntity();
        PatientEntity patientEntity = PatientFixture.somePatientEntity();
        patientEntity.setUser(userEntity);

        userJpaRepository.saveAndFlush(userEntity);
        patientJpaRepository.saveAndFlush(patientEntity);

        //when
        PatientEntity foundPatient = patientJpaRepository.findByPesel(patientEntity.getPesel()).orElseThrow();

        //then
        Assertions.assertNotNull(foundPatient);
        Assertions.assertEquals(patientEntity.getPesel(), foundPatient.getPesel());
        Assertions.assertEquals(patientEntity.getEmail(), foundPatient.getEmail());
        Assertions.assertEquals(patientEntity.getSurname(), foundPatient.getSurname());
    }

    @Test
    void findPatientByPeselWhenNotExist() {
        //given
        PatientEntity patientEntity = PatientFixture.somePatientEntity();

        //when
        Optional<PatientEntity> foundPatient = patientJpaRepository.findByPesel(patientEntity.getPesel());

        //then
        Assertions.assertTrue(foundPatient.isEmpty());
    }
}
