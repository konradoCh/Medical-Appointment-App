package org.medical_appointment.api.controller;

import lombok.AllArgsConstructor;
import org.medical_appointment.api.DTO.mapper.AppointmentMapper;
import org.medical_appointment.api.DTO.mapper.DoctorAvailableMapper;
import org.medical_appointment.api.DTO.mapper.DoctorMapper;
import org.medical_appointment.api.DTO.mapper.PatientMapper;
import org.medical_appointment.business.AppointmentService;
import org.medical_appointment.business.DoctorAvailableService;
import org.medical_appointment.business.DoctorService;
import org.medical_appointment.infrastructure.database.mapper.DoctorEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = DoctorController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class DoctorValidationControllerTest {

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

//    @ParameterizedTest
//    @MethodSource
//    void thatMedicalLicenceValidationWorksCorrectly(DoctorDTO doctor, Boolean correctMedicalLicence, String medicalLicence) throws Exception {
//        // given
//
//        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
//        Map<String, String> parametersMap = DoctorFixture.asMap(doctor);
//
//        parametersMap.forEach(parameters::add);
//
//        // when, then
//        Mockito.when(doctorService.findDoctorByMedicalLicence(medicalLicence)).thenReturn(DoctorFixture.someDoctor());
//
//        if (correctMedicalLicence) {
//            mockMvc.perform(post(DoctorController.DOCTOR_STATUS).params(parameters))
//                    .andExpect(status().is3xxRedirection())
//                    .andExpect(view().name("redirect:/doctor/" + medicalLicence));
//        } else {
//            mockMvc.perform(post(DoctorController.DOCTOR_STATUS).params(parameters))
//                    .andExpect(status().isBadRequest())
//                    .andExpect(model().attributeExists("errorMessage"))
//                    .andExpect(model().attribute("errorMessage", Matchers.containsString(medicalLicence)))
//                    .andExpect(view().name("error"));
//        }
//    }
//
//
//    public static Stream<Arguments> thatMedicalLicenceValidationWorksCorrectly() {
//        return Stream.of(
//                Arguments.of(DoctorFixture.newDoctorDTO(), false, ""),
//                Arguments.of(DoctorFixture.newDoctorDTO(), false, "1234"),
//                Arguments.of(DoctorFixture.newDoctorDTO(), false, " "),
//                Arguments.of(DoctorFixture.newDoctorDTO(), false, "1 2 3 4"),
//                Arguments.of(DoctorFixture.newDoctorDTO(), false, "-"),
//                Arguments.of(DoctorFixture.newDoctorDTO(), false, "."),
//                Arguments.of(DoctorFixture.newDoctorDTO(), false, "abc"),
//                Arguments.of(DoctorFixture.newDoctorDTO(), false, "178947697"),
//                Arguments.of(DoctorFixture.newDoctorDTO(), true, "5799137")
//        );
//    }
}