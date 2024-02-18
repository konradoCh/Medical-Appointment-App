package org.medical_appointment.api.controller;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.medical_appointment.api.DTO.DoctorAvailableDTO;
import org.medical_appointment.api.DTO.mapper.DoctorAvailableMapper;
import org.medical_appointment.business.AppointmentService;
import org.medical_appointment.business.DoctorAvailableService;
import org.medical_appointment.domain.DoctorAvailable;
import org.medical_appointment.domain.Patient;
import org.medical_appointment.util.DoctorAvailableFixture;
import org.medical_appointment.util.PatientFixture;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = ReservationController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class ReservationControllerTest {

    private MockMvc mockMvc;

    @MockBean
    @SuppressWarnings("unused")
    private final DoctorAvailableService doctorAvailableService;
    @MockBean
    @SuppressWarnings("unused")
    private final DoctorAvailableMapper doctorAvailableMapper;
    @MockBean
    @SuppressWarnings("unused")
    private final AppointmentService appointmentService;

    @Test
    void showAllAvailableDatesForSpecializationPageWorksCorrectly() throws Exception {
        //given
        Patient patient = PatientFixture.somePatient();
        String specialization = "cardiologist";
        List<DoctorAvailable> availableSpecialization = List.of(DoctorAvailableFixture.someDoctorAvailable());

        //when
        Mockito.when(doctorAvailableMapper.map(ArgumentMatchers.any(DoctorAvailable.class)))
                .thenReturn(DoctorAvailableFixture.someDoctorAvailableDTO());

        //then
        mockMvc.perform((get("/patient/specialization/{specialization}", specialization))
                        .param("pesel", patient.getPesel()))
                .andExpect(status().isOk())
                .andExpect(view().name("select_date_for_reservation"));
    }

    @Test
    void confirmReservationWorksCorrectly() throws Exception {
        //given
        Patient patient = PatientFixture.somePatient();
        DoctorAvailable doctorAvailable = DoctorAvailableFixture.someDoctorAvailable();
        DoctorAvailableDTO doctorAvailableDTO = DoctorAvailableFixture.someDoctorAvailableDTO();

        //when
        Mockito.when(doctorAvailableMapper.map(ArgumentMatchers.any()))
                .thenReturn(doctorAvailableDTO);

        //then
        mockMvc.perform((get("/reserve"))
                        .param("pesel", patient.getPesel())
                        .param("doctorAvailableId", doctorAvailable.getDoctorAvailableId().toString())
                        .param("specialization", doctorAvailable.getDoctor().getSpecialization())
                        .param("dateTime", "01-01-2024"))
                .andExpect(status().isOk())
                .andExpect(view().name("confirm_reservation"));
    }

    @Test
    void confirmedReservationAndAddAppointmentWorksCorrectly() throws Exception {
        //given
        Patient patient = PatientFixture.somePatient();
        DoctorAvailable doctorAvailable = DoctorAvailableFixture.someDoctorAvailable();
        DoctorAvailableDTO doctorAvailableDTO = DoctorAvailableFixture.someDoctorAvailableDTO();

        //when, then
        mockMvc.perform((post("/reserve/confirmed"))
                        .param("pesel", patient.getPesel())
                        .param("doctorAvailableId", doctorAvailable.getDoctorAvailableId().toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("reservation_done"));
    }
}