package org.medical_appointment.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.medical_appointment.infrastructure.database.entity.DoctorAvailableEntity;
import org.medical_appointment.infrastructure.database.entity.DoctorEntity;
import org.medical_appointment.infrastructure.database.entity.UserEntity;
import org.medical_appointment.integration.configuration.PersistenceContainerTestConfiguration;
import org.medical_appointment.util.DoctorAvailableFixture;
import org.medical_appointment.util.DoctorFixture;
import org.medical_appointment.util.UserFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class DoctorAvailableJpaRepositoryTest {

    private DoctorAvailableJpaRepository doctorAvailableJpaRepository;
    private DoctorJpaRepository doctorJpaRepository;
    private UserJpaRepository userJpaRepository;

    @Test
    void findBySpecializationWorksCorrectly() {
        //given
        UserEntity userEntity = UserFixture.someUserEntity();
        DoctorEntity doctorEntity = DoctorFixture.someDoctorEntity();
        doctorEntity.setUser(userEntity);
        String specialization = doctorEntity.getSpecialization();

        userJpaRepository.saveAndFlush(userEntity);
        doctorJpaRepository.saveAndFlush(doctorEntity);

        DoctorAvailableEntity doctorAvailableEntity = DoctorAvailableFixture.someDoctorAvailableEntity();
        doctorAvailableEntity.setReserved(false);
        doctorAvailableEntity.setDoctor(doctorEntity);
        doctorAvailableJpaRepository.saveAndFlush(doctorAvailableEntity);

        //when
        List<DoctorAvailableEntity> result = doctorAvailableJpaRepository.findBySpecialization(specialization);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void findByMedicalLicenceWorksCorrectly() {
        //given
        UserEntity userEntity = UserFixture.someUserEntity();
        DoctorEntity doctorEntity = DoctorFixture.someDoctorEntity();
        doctorEntity.setUser(userEntity);
        String medicalLicence = doctorEntity.getMedicalLicence();

        userJpaRepository.saveAndFlush(userEntity);
        doctorJpaRepository.saveAndFlush(doctorEntity);

        DoctorAvailableEntity doctorAvailableEntity = DoctorAvailableFixture.someDoctorAvailableEntity();
        doctorAvailableEntity.setDoctor(doctorEntity);
        doctorAvailableJpaRepository.saveAndFlush(doctorAvailableEntity);

        // when
        List<DoctorAvailableEntity> result = doctorAvailableJpaRepository.findByMedicalLicence(medicalLicence);

        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(medicalLicence, result.get(0).getDoctor().getMedicalLicence());
    }
}