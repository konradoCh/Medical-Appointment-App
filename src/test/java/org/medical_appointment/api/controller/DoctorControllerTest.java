package org.medical_appointment.api.controller;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.medical_appointment.api.DTO.AppointmentDTO;
import org.medical_appointment.api.DTO.PatientDTO;
import org.medical_appointment.api.DTO.mapper.AppointmentMapper;
import org.medical_appointment.api.DTO.mapper.DoctorAvailableMapper;
import org.medical_appointment.api.DTO.mapper.DoctorMapper;
import org.medical_appointment.api.DTO.mapper.PatientMapper;
import org.medical_appointment.business.AppointmentService;
import org.medical_appointment.business.DoctorAvailableService;
import org.medical_appointment.business.DoctorService;
import org.medical_appointment.domain.Appointment;
import org.medical_appointment.domain.Doctor;
import org.medical_appointment.domain.Patient;
import org.medical_appointment.infrastructure.database.mapper.DoctorEntityMapper;
import org.medical_appointment.util.AppointmentFixture;
import org.medical_appointment.util.DoctorFixture;
import org.medical_appointment.util.PatientFixture;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = DoctorController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class DoctorControllerTest {

    private MockMvc mockMvc;

    @MockBean
    @SuppressWarnings("unused")
    private final DoctorAvailableService doctorAvailableService;
    @MockBean
    @SuppressWarnings("unused")
    private final DoctorService doctorService;
    @MockBean
    @SuppressWarnings("unused")
    private final DoctorAvailableMapper doctorAvailableMapper;
    @MockBean
    @SuppressWarnings("unused")
    private final AppointmentService appointmentService;
    @MockBean
    @SuppressWarnings("unused")

    private final DoctorEntityMapper doctorEntityMapper;
    @MockBean
    @SuppressWarnings("unused")
    private final DoctorMapper doctorMapper;
    @MockBean
    @SuppressWarnings("unused")
    private final PatientMapper patientMapper;
    @MockBean
    @SuppressWarnings("unused")
    private final AppointmentMapper appointmentMapper;

    @Test
    void doctorHomePageWorksCorrectly() throws Exception {
        //given
        Doctor doctor = DoctorFixture.someDoctor();

        //when
        Mockito.when(doctorService.findDoctorByMedicalLicence(doctor.getMedicalLicence())).thenReturn(doctor);

        // then
        mockMvc.perform(get("/doctor/" + doctor.getMedicalLicence()))
                .andExpect(status().isOk())
                .andExpect(view().name("doctor_portal"));
    }

    @Test
    void addedAppointmentPageWorksCorrectly() throws Exception {
        //given
        Doctor doctor = DoctorFixture.someDoctor();
        String date = "31-12-2023";
        String time = "14:45";

        //when
        Mockito.when(doctorService.findDoctorByMedicalLicence(doctor.getMedicalLicence())).thenReturn(doctor);

        //then
        mockMvc.perform((post("/doctor/add"))
                        .param("medicalLicence", doctor.getMedicalLicence())
                        .param("date", date)
                        .param("time", time))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/doctor/" + doctor.getMedicalLicence()));
    }

    @Test
    void cancelAvailableDatePageWorksCorrectly() throws Exception {
        //given
        Doctor doctor = DoctorFixture.someDoctor();
        int doctorAvailableId = 1;

        //when
        Mockito.when(doctorService.findDoctorByMedicalLicence(doctor.getMedicalLicence())).thenReturn(doctor);

        //then
        mockMvc.perform((delete("/doctor/delete/{doctorAvailableId}", doctorAvailableId))
                        .param("medicalLicence", doctor.getMedicalLicence()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/doctor/" + doctor.getMedicalLicence()));
    }

    @Test
    void getPatientInfoPageWorksCorrectly() throws Exception {
        //given
        Appointment appointment = AppointmentFixture.someAppointment();
        PatientDTO patientDTO = PatientFixture.newPatientDTO();

        //when
        Mockito.when(appointmentService.findAppointmentByDoctorAvailableId(appointment.getAppointmentId()))
                .thenReturn(appointment);
        Mockito.when(patientMapper.map((Patient) ArgumentMatchers.any())).thenReturn(patientDTO);

        //when, then
        mockMvc.perform((get("/doctor/patientInfo"))
                        .param("doctorAvailableId", appointment.getAppointmentId().toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("patient_info_for_doctor"));
    }

    @Test
    void addDetailsOfAppointmentWorksCorrectly() throws Exception {
        //given
        Appointment appointment = AppointmentFixture.someAppointment();
        Doctor doctor = DoctorFixture.someDoctor();
        AppointmentDTO addDetailOfAppointmentDTO = AppointmentDTO.builder().illness("Heart Attack").description("Relax").build();

        //when
        Mockito.when(appointmentService.findAppointmentByDoctorAvailableId(appointment.getAppointmentId()))
                .thenReturn(appointment);

        //when, then
        mockMvc.perform((put("/doctor/addDetailsOfAppointment"))
                        .param("doctorAvailableId", appointment.getAppointmentId().toString())
                        .param("medicalLicence", doctor.getMedicalLicence()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/doctor/" + doctor.getMedicalLicence()));
    }
}