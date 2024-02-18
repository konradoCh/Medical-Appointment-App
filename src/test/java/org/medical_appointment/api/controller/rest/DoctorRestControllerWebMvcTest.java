package org.medical_appointment.api.controller.rest;

import lombok.AllArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.medical_appointment.api.DTO.DoctorAvailableDTO;
import org.medical_appointment.api.DTO.mapper.DoctorAvailableMapper;
import org.medical_appointment.business.DoctorAvailableService;
import org.medical_appointment.business.DoctorService;
import org.medical_appointment.domain.Doctor;
import org.medical_appointment.domain.DoctorAvailable;
import org.medical_appointment.infrastructure.database.entity.DoctorAvailableEntity;
import org.medical_appointment.infrastructure.database.entity.DoctorEntity;
import org.medical_appointment.infrastructure.database.mapper.DoctorAvailableEntityMapper;
import org.medical_appointment.infrastructure.database.mapper.DoctorEntityMapper;
import org.medical_appointment.util.DoctorAvailableFixture;
import org.medical_appointment.util.DoctorFixture;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DoctorRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class DoctorRestControllerWebMvcTest {

    private MockMvc mockMvc;

    @MockBean
    private final DoctorAvailableService doctorAvailableService;
    @MockBean
    private final DoctorAvailableMapper doctorAvailableMapper;
    @MockBean
    private final DoctorService doctorService;
    @MockBean
    private final DoctorEntityMapper doctorEntityMapper;
    @MockBean
    private final DoctorAvailableEntityMapper doctorAvailableEntityMapper;


    @Test
    void availableAppointmentsDisplayCorrectly() throws Exception {
        //given
        DoctorAvailable doctorAvailable = DoctorAvailableFixture.someDoctorAvailable();
        DoctorAvailableDTO doctorAvailableDTO = DoctorAvailableFixture.someDoctorAvailableDTO();
        String medicalLicence = doctorAvailable.getDoctor().getMedicalLicence();

        //when
        Mockito.when(doctorAvailableService.notReservedDoctorAvailable(any())).thenReturn(List.of(doctorAvailable));
        Mockito.when(doctorAvailableMapper.map(any())).thenReturn(doctorAvailableDTO);

        //then
        mockMvc.perform(get(DoctorRestController.API_DOCTOR + "/" + medicalLicence + "/availableAppointments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.doctoravailables[0].doctorAvailableId", Matchers.is(doctorAvailableDTO.getDoctorAvailableId())))
                .andExpect(jsonPath("$.doctoravailables[0].dateTime", Matchers.is(doctorAvailableDTO.getDateTime())));
    }

    @Test
    void addingDoctorAvailableWorksCorrectly() throws Exception {
        //given
        Doctor doctor = DoctorFixture.someDoctor();
        DoctorEntity doctorEntity = DoctorFixture.someDoctorEntity();
        String medicalLicence = doctor.getMedicalLicence();

        //when
        Mockito.when(doctorService.findDoctorByMedicalLicence(any())).thenReturn(doctor);
        Mockito.when(doctorEntityMapper.mapToEntity(any())).thenReturn(doctorEntity);

        //then
        mockMvc.perform(post(DoctorRestController.API_DOCTOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("date", "09-02-2024")
                        .param("time", "13:00")
                        .param("medicalLicence", medicalLicence))
                .andExpect(status().isCreated());
    }

    @Test
    void updateDoctorAvailableWorksCorrectly() throws Exception {
        //given
        DoctorAvailable doctorAvailable = DoctorAvailableFixture.someDoctorAvailable();
        DoctorAvailableEntity doctorAvailableEntity = DoctorAvailableFixture.someDoctorAvailableEntity();
        Doctor doctor = DoctorFixture.someDoctor();

        //when
        Mockito.when(doctorAvailableService.findById(any())).thenReturn(doctorAvailable);
        Mockito.when(doctorAvailableEntityMapper.mapToEntity(any())).thenReturn(doctorAvailableEntity);
        Mockito.when(doctorService.findDoctorByMedicalLicence(any())).thenReturn(doctor);

        //then
        mockMvc.perform(put(DoctorRestController.API_DOCTOR + doctorAvailable.getDoctorAvailableId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("doctorAvailableId", doctorAvailable.getDoctorAvailableId().toString())
                        .param("date", "12-12-2025")
                        .param("time", "20:00")
                        .param("medicalLicence", doctor.getMedicalLicence()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteDoctorAvailableWorksCorrectly() throws Exception {
        //given
        DoctorAvailable doctorAvailable = DoctorAvailableFixture.someDoctorAvailable();

        //when, then
        mockMvc.perform(delete(DoctorRestController.API_DOCTOR + "/" + doctorAvailable.getDoctorAvailableId()))
                .andExpect(status().isOk());
    }
}