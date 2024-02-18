package org.medical_appointment.integration.rest;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.medical_appointment.api.DTO.DoctorAvailableDTO;
import org.medical_appointment.api.DTO.DoctorAvailableListDTO;
import org.medical_appointment.api.DTO.DoctorDTO;
import org.medical_appointment.api.DTO.UserDTO;
import org.medical_appointment.business.DoctorService;
import org.medical_appointment.business.UserService;
import org.medical_appointment.integration.configuration.RestAssuredIntegrationTestBase;
import org.medical_appointment.integration.support.DoctorControllerTestSupport;
import org.medical_appointment.util.DoctorAvailableFixture;
import org.medical_appointment.util.UserFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DoctorRestIT
        extends RestAssuredIntegrationTestBase
        implements DoctorControllerTestSupport {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private UserService userService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void thatAvailableAppointmentsListCanBeDisplayedCorrectlyAndAddingWorksCorrectly() {
        //given
        DoctorAvailableDTO doctorAvailableDTO = prepareDoctorAvailableData();
        String medicalLicence = doctorAvailableDTO.getDoctor().getMedicalLicence();
        List<DoctorAvailableDTO> doctorAvailableListBeforeAdd = doctorAvailableList(medicalLicence).getDoctoravailables();

        //when
        saveDoctorAvailable(doctorAvailableDTO);
        List<DoctorAvailableDTO> doctorAvailableListAfterAdd = doctorAvailableList(medicalLicence).getDoctoravailables();

        //then
        Assertions.assertEquals(0, doctorAvailableListBeforeAdd.size());
        Assertions.assertEquals(1, doctorAvailableListAfterAdd.size());
    }

    @Test
    void thatUpdatingDoctorAvailableWorksCorrectly() {
        //given
        String newDate = "11-01-2025";
        String newTime = "20:30";

        DoctorAvailableDTO doctorAvailableDTO = prepareDoctorAvailableData();
        String medicalLicence = doctorAvailableDTO.getDoctor().getMedicalLicence();
        saveDoctorAvailable(doctorAvailableDTO);

        //when
        List<DoctorAvailableDTO> doctorAvailableDTOList = doctorAvailableList(medicalLicence).getDoctoravailables();
        doctorAvailableDTO.setDoctorAvailableId(doctorAvailableDTOList.get(0).getDoctorAvailableId());

        updateDoctorAvailable(doctorAvailableDTO, newDate, newTime);
        DoctorAvailableListDTO doctorAvailableListDTO = doctorAvailableList(medicalLicence);
        String dateTime = doctorAvailableListDTO.getDoctoravailables().get(0).getDateTime();

        String date = dateTime.substring(0, dateTime.indexOf(" "));
        String time = dateTime.substring(dateTime.indexOf(" ") + 1);

        //then
        Assertions.assertEquals(newDate, date);
        Assertions.assertEquals(newTime, time);
    }

    @Test
    void thatDeletingDoctorAvailableWorksCorrectly() {
        //given
        DoctorAvailableDTO doctorAvailableDTO = prepareDoctorAvailableData();
        String medicalLicence = doctorAvailableDTO.getDoctor().getMedicalLicence();
        saveDoctorAvailable(doctorAvailableDTO);


        //when
        List<DoctorAvailableDTO> doctorAvailableListBeforeDeleting = doctorAvailableList(medicalLicence).getDoctoravailables();
        doctorAvailableDTO.setDoctorAvailableId(doctorAvailableListBeforeDeleting.get(0).getDoctorAvailableId());
        deleteDoctorAvailable(doctorAvailableDTO);
        List<DoctorAvailableDTO> doctorAvailableListAfterDeleting = doctorAvailableList(medicalLicence).getDoctoravailables();

        //then
        Assertions.assertEquals(1, doctorAvailableListBeforeDeleting.size());
        Assertions.assertEquals(0, doctorAvailableListAfterDeleting.size());
    }

    private DoctorAvailableDTO prepareDoctorAvailableData() {
        DoctorAvailableDTO doctorAvailableDTO = DoctorAvailableFixture.someDoctorAvailableDTO();
        DoctorDTO doctorDTO = doctorAvailableDTO.getDoctor();
        UserDTO userDTO = UserFixture.someUserDTO();
        userService.registerNewUser(userDTO.getUserName(), userDTO.getPassword(), userDTO.getEmail(), userDTO.getRole());
        doctorDTO.setUser(userDTO);
        doctorService.createAndSaveNewDoctor(doctorDTO);
        return doctorAvailableDTO;
    }
}
