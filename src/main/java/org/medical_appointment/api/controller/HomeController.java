package org.medical_appointment.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.medical_appointment.business.DoctorService;
import org.medical_appointment.business.PatientService;
import org.medical_appointment.domain.Doctor;
import org.medical_appointment.domain.Patient;
import org.medical_appointment.domain.User;
import org.medical_appointment.business.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
@AllArgsConstructor
public class HomeController {

    private final UserService userService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    static final String HOME = "/";

    @RequestMapping(value = HOME, method = RequestMethod.GET)
    public String home(
            ModelMap model,
            Authentication authentication,
            HttpServletRequest request
    ) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String username = user.getUsername();

        User foundUser = userService.findByUserName(username);
        String role = foundUser.getRoles().stream().toList().get(0).getRole();
        int userId = foundUser.getId();

        prepareModel(model, role, userId);

//        HttpSession session = request.getSession();
//        String sessionId = session.getId();
//
//        log.info("ID Session: " + sessionId);

        return "home";
    }

    private void prepareModel(ModelMap model, String role, int userId) {
        if (role.equals("PATIENT")) {
            Patient patient = patientService.findPatientByUserId(userId);
            model.addAttribute("pesel", patient.getPesel());
        } else {
            Doctor doctor = doctorService.findPatientByUserId(userId);
            model.addAttribute("medicalLicence", doctor.getMedicalLicence());
        }
    }
}
