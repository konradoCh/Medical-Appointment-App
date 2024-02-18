package org.medical_appointment.api.controller;

import lombok.AllArgsConstructor;
import org.medical_appointment.api.DTO.mapper.AppointmentMapper;
import org.medical_appointment.api.DTO.mapper.PatientMapper;
import org.medical_appointment.business.AppointmentService;
import org.medical_appointment.business.DoctorService;
import org.medical_appointment.business.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PatientController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class PatientValidationControllerTest {

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


//    @Test
//    void thatEmailValidationWorksCorrectly() throws Exception {
//        //given
//        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
//        Map<String, String> parametersMap = PatientFixture.asMap(PatientFixture.newPatientDTO());
//        String badEmail = "badEmail";
//        parametersMap.put("email", badEmail);
//        parametersMap.forEach(parameters::add);
//
//        //when, then
//        mockMvc.perform(post(PatientController.PATIENT_STATUS).params(parameters))
//                .andExpect(status().isBadRequest())
//                .andExpect(model().attributeExists("errorMessage"))
//                .andExpect(model().attribute("errorMessage", Matchers.containsString(badEmail)))
//                .andExpect(view().name("error"));
//    }

//    @ParameterizedTest
//    @MethodSource
//    void thatPeselValidationWorksCorrectly(PatientDTO patient, Boolean correctPesel, String pesel) throws Exception {
//        // given
//        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
//        Map<String, String> parametersMap = PatientFixture.asMap(patient);
//
//        parametersMap.forEach(parameters::add);
//
//        // when, then
//        if (correctPesel) {
//            mockMvc.perform(post(PatientController.PATIENT_STATUS).params(parameters))
//                    .andExpect(status().is3xxRedirection())
//                    .andExpect(view().name("redirect:/patient/" + pesel));
//        } else {
//            mockMvc.perform(post(PatientController.PATIENT_STATUS).params(parameters))
//                    .andExpect(status().isBadRequest())
//                    .andExpect(model().attributeExists("errorMessage"))
//                    .andExpect(model().attribute("errorMessage", Matchers.containsString(pesel)))
//                    .andExpect(view().name("error"));
//        }
//    }
//
//    public static Stream<Arguments> thatPeselValidationWorksCorrectly() {
//        return Stream.of(
//                Arguments.of(PatientFixture.newPatientDTO(), false, ""),
//                Arguments.of(PatientFixture.newPatientDTO(), false, "1234"),
//                Arguments.of(PatientFixture.newPatientDTO(), false, " "),
//                Arguments.of(PatientFixture.newPatientDTO(), false, "1 2 3 4"),
//                Arguments.of(PatientFixture.newPatientDTO(), false, "-"),
//                Arguments.of(PatientFixture.newPatientDTO(), false, "."),
//                Arguments.of(PatientFixture.newPatientDTO(), false, "abc"),
//                Arguments.of(PatientFixture.newPatientDTO(), true, "54082873337")
//        );
//    }
//
//    @ParameterizedTest
//    @MethodSource
//    void thatPhoneValidationWorksCorrectly(Boolean correctPhone, String phone) throws Exception {
//        // given
//        PatientDTO patient = PatientFixture.newPatientDTO();
//        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
//        Map<String, String> parametersMap = PatientFixture.asMap(patient);
//        parametersMap.put("phone", phone);
//        parametersMap.forEach(parameters::add);
//
//        // when, then
//        if (correctPhone) {
//            mockMvc.perform(post(PatientController.PATIENT_STATUS).params(parameters))
//                    .andExpect(status().is3xxRedirection())
//                    .andExpect(view().name("redirect:/patient/" + patient.getPesel()));
//        } else {
//            mockMvc.perform(post(PatientController.PATIENT_STATUS).params(parameters))
//                    .andExpect(status().isBadRequest())
//                    .andExpect(model().attributeExists("errorMessage"))
//                    .andExpect(model().attribute("errorMessage", Matchers.containsString(phone)))
//                    .andExpect(view().name("error"));
//        }
//    }
//
//    public static Stream<Arguments> thatPhoneValidationWorksCorrectly() {
//        return Stream.of(
//                Arguments.of(false, ""),
//                Arguments.of(false, "+48 504 203 260@@"),
//                Arguments.of(false, "+48.504.203.260"),
//                Arguments.of(false, "+55(123) 456-78-90-"),
//                Arguments.of(false, "+55(123) - 456-78-90"),
//                Arguments.of(false, "504.203.260"),
//                Arguments.of(false, " "),
//                Arguments.of(false, "-"),
//                Arguments.of(false, "()"),
//                Arguments.of(false, "() + ()"),
//                Arguments.of(false, "(21 7777"),
//                Arguments.of(false, "+48 (21)"),
//                Arguments.of(false, "+"),
//                Arguments.of(false, " 1"),
//                Arguments.of(false, "1"),
//                Arguments.of(false, "+48 (12) 504 203 260"),
//                Arguments.of(false, "+48 (12) 504-203-260"),
//                Arguments.of(false, "+48(12)504203260"),
//                Arguments.of(false, "555-5555-555"),
//                Arguments.of(true, "+48 754 552 234")
//        );
//    }
}