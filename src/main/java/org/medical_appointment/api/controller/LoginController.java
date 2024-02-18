package org.medical_appointment.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.medical_appointment.api.DTO.DoctorDTO;
import org.medical_appointment.api.DTO.PatientDTO;
import org.medical_appointment.business.DoctorService;
import org.medical_appointment.business.PatientService;
import org.medical_appointment.business.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
class LoginController {

    private final UserService userService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String performLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        this.logoutHandler.logout(request, response, authentication);
        return "redirect:/";
    }

    @GetMapping("/register")
    public String fillDataOfNewUser(Model model) {
        model.addAttribute("patientDTO", new PatientDTO());
        model.addAttribute("doctorDTO", new DoctorDTO());
        return "register_new_user";
    }

    @PostMapping("/created")
    public String createNewUser(
            @Valid @ModelAttribute("patientDTO") PatientDTO patientDTO,
            @Valid @ModelAttribute("doctorDTO") DoctorDTO doctorDTO
    ) {
        if (patientDTO.getPesel() != null) {
            registerPatient(patientDTO);
        } else if (doctorDTO.getMedicalLicence() != null) {
            registerDoctor(doctorDTO);
        }
        return "registration_done";
    }

    private void registerPatient(PatientDTO patientDTO) {
        patientDTO.getUser().setRole("patient");
        userService.registerNewUser(
                patientDTO.getUser().getUserName(),
                patientDTO.getUser().getPassword(),
                patientDTO.getEmail(),
                patientDTO.getUser().getRole()
        );

        patientService.createAndSaveNewPatient(patientDTO);
    }

    private void registerDoctor(DoctorDTO doctorDTO) {
        doctorDTO.getUser().setRole("doctor");
        userService.registerNewUser(
                doctorDTO.getUser().getUserName(),
                doctorDTO.getUser().getPassword(),
                doctorDTO.getUser().getEmail(),
                doctorDTO.getUser().getRole()
        );

        doctorService.createAndSaveNewDoctor(doctorDTO);
    }
}
