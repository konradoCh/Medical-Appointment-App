package org.medical_appointment.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.medical_appointment.infrastructure.database.entity.DoctorEntity;
import org.medical_appointment.infrastructure.database.entity.UserEntity;
import org.medical_appointment.integration.configuration.PersistenceContainerTestConfiguration;
import org.medical_appointment.util.DoctorFixture;
import org.medical_appointment.util.UserFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.Set;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DoctorJpaRepositoryTest {

    private DoctorJpaRepository doctorJpaRepository;
    private UserJpaRepository userJpaRepository;

    @Test
    void findDoctorByMedicalLicence() {
        //given
        UserEntity userEntity = UserFixture.someUserEntity();
        DoctorEntity doctorEntity = DoctorFixture.someDoctorEntity();
        doctorEntity.setUser(userEntity);

        userJpaRepository.saveAndFlush(userEntity);
        doctorJpaRepository.saveAndFlush(doctorEntity);

        //when
        DoctorEntity foundDoctor = doctorJpaRepository.findByMedicalLicence(doctorEntity.getMedicalLicence()).orElseThrow();

        //then
        Assertions.assertNotNull(foundDoctor);
        Assertions.assertEquals(doctorEntity.getMedicalLicence(), doctorEntity.getMedicalLicence());
        Assertions.assertEquals(doctorEntity.getSurname(), doctorEntity.getSurname());
        Assertions.assertEquals(doctorEntity.getSpecialization(), doctorEntity.getSpecialization());
    }

    @Test
    void findAvailableSpecializations() {
        //given
        UserEntity userEntity = UserFixture.someUserEntity();
        DoctorEntity doctor = DoctorFixture.someDoctorEntity();
        doctor.setUser(userEntity);

        userJpaRepository.saveAndFlush(userEntity);
        doctorJpaRepository.saveAndFlush(doctor);

        //when
        Set<String> availableSpecializations = doctorJpaRepository.findAvailableSpecializations();

        //then
        Assertions.assertNotNull(availableSpecializations);
        Assertions.assertEquals(3, availableSpecializations.size());
    }
}
