package org.medical_appointment.api.controller;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.medical_appointment.api.DTO.mapper.AppointmentMapper;
import org.medical_appointment.api.DTO.mapper.PatientMapper;
import org.medical_appointment.business.AppointmentService;
import org.medical_appointment.business.DoctorService;
import org.medical_appointment.business.PatientService;
import org.medical_appointment.domain.Patient;
import org.medical_appointment.util.PatientFixture;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = PatientController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class PatientControllerTest {

    private MockMvc mockMvc;

    @MockBean
    @SuppressWarnings("unused")
    private final PatientService patientService;

    @MockBean
    @SuppressWarnings("unused")
    private final AppointmentService appointmentService;

    @MockBean
    @SuppressWarnings("unused")
    private final DoctorService doctorService;

    @MockBean
    @SuppressWarnings("unused")
    private final PatientMapper patientMapper;

    @MockBean
    @SuppressWarnings("unused")
    private final AppointmentMapper appointmentMapper;

    @Test
    void patientHomePageWorksCorrectly() throws Exception {
        //given
        Patient patient = PatientFixture.somePatient();

        //when
        Mockito.when(patientService.findPatientByPesel(patient.getPesel())).thenReturn(patient);

        // then
        mockMvc.perform(get("/patient/" + patient.getPesel()))
                .andExpect(status().isOk())
                .andExpect(view().name("patient_portal"));
    }

    @Test
    void selectSpecializationForReservePageWorksCorrectly() throws Exception {
        //given
        Patient patient = PatientFixture.somePatient();

        //when
        Mockito.when(patientService.findPatientByPesel(patient.getPesel())).thenReturn(patient);

        //then
        ResultActions perform = mockMvc.perform((get("/patient/reserve"))
                        .param("pesel", patient.getPesel()))
                .andExpect(status().isOk())
                .andExpect(view().name("select_specialization_for_reserve"));
    }

    @Test
    void cancelReservationPageWorksCorrectly() throws Exception {
        //given
        Patient patient = PatientFixture.somePatient();
        int appointmentId = 1;

        //when
        Mockito.when(patientService.findPatientByPesel(patient.getPesel())).thenReturn(patient);

        //then
        ResultActions perform = mockMvc.perform((delete("/patient/cancel/{appointmentId}", appointmentId))
                        .param("pesel", patient.getPesel()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/patient/" + patient.getPesel()));
    }

    @Test
    void upcomingAppointmentsPageWorksCorrectly() throws Exception {
        //given
        Patient patient = PatientFixture.somePatient();

        //when
        Mockito.when(patientService.findPatientByPesel(patient.getPesel())).thenReturn(patient);

        //when, then
        ResultActions perform = mockMvc.perform((get("/patient/upcomingAppointments"))
                        .param("pesel", patient.getPesel()))
                .andExpect(status().isOk())
                .andExpect(view().name("appointments_upcoming"));
    }

    @Test
    void completedAppointmentsPageWorksCorrectly() throws Exception {
        //given
        Patient patient = PatientFixture.somePatient();

        //when
        Mockito.when(patientService.findPatientByPesel(patient.getPesel())).thenReturn(patient);

        //when, then
        ResultActions perform = mockMvc.perform((get("/patient/completedAppointments"))
                        .param("pesel", patient.getPesel()))
                .andExpect(status().isOk())
                .andExpect(view().name("appointments_completed"));
    }

    @Test
    void canceledAppointmentsPageWorksCorrectly() throws Exception {
        //given
        Patient patient = PatientFixture.somePatient();

        //when
        Mockito.when(patientService.findPatientByPesel(patient.getPesel())).thenReturn(patient);

        //when, then
        ResultActions perform = mockMvc.perform((get("/patient/canceledAppointments"))
                        .param("pesel", patient.getPesel()))
                .andExpect(status().isOk())
                .andExpect(view().name("appointments_canceled"));
    }
}