package org.medical_appointment.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.medical_appointment.infrastructure.database.entity.*;
import org.medical_appointment.integration.configuration.PersistenceContainerTestConfiguration;
import org.medical_appointment.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AppointmentJpaRepositoryTest {

    private AppointmentJpaRepository appointmentJpaRepository;
    private PatientJpaRepository patientJpaRepository;
    private DoctorAvailableJpaRepository doctorAvailableJpaRepository;
    private DoctorJpaRepository doctorJpaRepository;
    private UserJpaRepository userJpaRepository;


    @Test
    void findAppointmentById() {
        //given
        UserEntity userEntity = UserFixture.someUserEntity();
        PatientEntity patientEntity = PatientFixture.somePatientEntity();
        patientEntity.setUser(userEntity);
        DoctorEntity doctorEntity = DoctorFixture.someDoctorEntity();
        doctorEntity.setUser(userEntity);
        DoctorAvailableEntity doctorAvailableEntity = DoctorAvailableFixture.someDoctorAvailableEntity();
        doctorAvailableEntity.setDoctor(doctorEntity);
        AppointmentEntity appointmentEntity = AppointmentFixture.someAppointmentEntity();
        appointmentEntity.setPatient(patientEntity);
        appointmentEntity.setDoctorAvailable(doctorAvailableEntity);

        userJpaRepository.saveAndFlush(userEntity);
        patientJpaRepository.saveAndFlush(patientEntity);

        doctorJpaRepository.saveAndFlush(doctorEntity);
        doctorAvailableJpaRepository.saveAndFlush(doctorAvailableEntity);
        appointmentJpaRepository.saveAndFlush(appointmentEntity);

        //when
        AppointmentEntity foundAppointment = appointmentJpaRepository.findById(appointmentEntity.getAppointmentId()).orElseThrow();
        PatientEntity foundPatient = patientJpaRepository.findByPesel(patientEntity.getPesel()).orElseThrow();
        DoctorEntity foundDoctor = doctorJpaRepository.findByMedicalLicence(doctorEntity.getMedicalLicence()).orElseThrow();
        DoctorAvailableEntity foundDoctorAvailable = doctorAvailableJpaRepository.findById(doctorAvailableEntity.getDoctorAvailableId()).orElseThrow();

        //then
        Assertions.assertNotNull(foundAppointment);
        Assertions.assertNotNull(foundPatient);
        Assertions.assertNotNull(foundDoctor);
        Assertions.assertNotNull(foundDoctorAvailable);

        Assertions.assertEquals(appointmentEntity.getAppointmentNumber(), foundAppointment.getAppointmentNumber());
        Assertions.assertEquals(patientEntity.getPesel(), foundPatient.getPesel());
        Assertions.assertEquals(doctorEntity.getMedicalLicence(), foundDoctor.getMedicalLicence());
        Assertions.assertEquals(doctorAvailableEntity.getDoctorAvailableId(), foundDoctorAvailable.getDoctorAvailableId());
    }

    @Test
    void findAllCompletedAppointmentsForPatient() {
        //given
        UserEntity userEntity = UserFixture.someUserEntity();
        PatientEntity patientEntity = PatientFixture.somePatientEntity();
        patientEntity.setUser(userEntity);
        DoctorEntity doctorEntity = DoctorFixture.someDoctorEntity();
        doctorEntity.setUser(userEntity);
        DoctorAvailableEntity doctorAvailableEntity = DoctorAvailableFixture.someDoctorAvailableEntity();
        doctorAvailableEntity.setDoctor(doctorEntity);
        AppointmentEntity appointmentEntity = AppointmentFixture.someAppointmentEntity();
        appointmentEntity.setPatient(patientEntity);
        appointmentEntity.setDoctorAvailable(doctorAvailableEntity);

        userJpaRepository.saveAndFlush(userEntity);
        patientJpaRepository.saveAndFlush(patientEntity);
        doctorJpaRepository.saveAndFlush(doctorEntity);
        doctorAvailableJpaRepository.saveAndFlush(doctorAvailableEntity);
        appointmentJpaRepository.saveAndFlush(appointmentEntity);

        //when
        List<AppointmentEntity> appointments
                = appointmentJpaRepository.findAllCompletedAppointmentsForPatient(patientEntity);

        //then
        Assertions.assertNotNull(appointments);
        Assertions.assertEquals(1, appointments.size());
    }

    @Test
    void findAppointmentByDoctorAvailableId() {
        //given
        UserEntity userEntity = UserFixture.someUserEntity();
        PatientEntity patientEntity = PatientFixture.somePatientEntity();
        patientEntity.setUser(userEntity);
        DoctorEntity doctorEntity = DoctorFixture.someDoctorEntity();
        doctorEntity.setUser(userEntity);
        DoctorAvailableEntity doctorAvailableEntity = DoctorAvailableFixture.someDoctorAvailableEntity();
        doctorAvailableEntity.setDoctor(doctorEntity);
        AppointmentEntity appointmentEntity = AppointmentFixture.someAppointmentEntity();
        appointmentEntity.setPatient(patientEntity);
        appointmentEntity.setDoctorAvailable(doctorAvailableEntity);

        userJpaRepository.saveAndFlush(userEntity);
        patientJpaRepository.saveAndFlush(patientEntity);
        doctorJpaRepository.saveAndFlush(doctorEntity);
        doctorAvailableJpaRepository.saveAndFlush(doctorAvailableEntity);
        appointmentJpaRepository.saveAndFlush(appointmentEntity);

        //when
        Optional<AppointmentEntity> result = appointmentJpaRepository.findAppointmentByDoctorAvailableId(doctorAvailableEntity.getDoctorAvailableId());

        //then
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(appointmentEntity, result.get());
    }
}
