package org.medical_appointment.api.controller;

import lombok.RequiredArgsConstructor;
import org.medical_appointment.api.DTO.AppointmentDTO;
import org.medical_appointment.api.DTO.PatientDTO;
import org.medical_appointment.api.DTO.mapper.AppointmentMapper;
import org.medical_appointment.api.DTO.mapper.PatientMapper;
import org.medical_appointment.business.AppointmentService;
import org.medical_appointment.business.DoctorService;
import org.medical_appointment.business.PatientService;
import org.medical_appointment.domain.Appointment;
import org.medical_appointment.domain.Patient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PatientController {

    static final String PATIENT = "/patient";

    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;

    private final AppointmentMapper appointmentMapper;


    @GetMapping(value = PATIENT)
    public String selectPatientStatus(Model model) {
        model.addAttribute("patientDTO", new PatientDTO());
        return "patient_status";
    }

    @GetMapping("/patient/{pesel}")
    public String getPatientAccount(
            @PathVariable String pesel,
            ModelMap model
    ) {
        prepareData(model, pesel);
        return "patient_portal";
    }

    @GetMapping("/patient/reserve")
    public String reserveAppointment(
            @RequestParam(value = "pesel") String pesel,
            Model model
    ) {
        List<AppointmentDTO> upcomingAppointmentsDTO = getUpcomingAppointmentsDTO(pesel);
        List<String> availableSpecializations = doctorService.availableSpecializations();

        model.addAttribute("availableSpecializations", availableSpecializations);
        model.addAttribute("pesel", pesel);
        model.addAttribute("upcomingAppointmentsDTO", upcomingAppointmentsDTO);

        return "select_specialization_for_reserve";
    }

    @DeleteMapping("/patient/cancel/{appointmentId}")
    public String cancelAppointmentReservation(
            @PathVariable Integer appointmentId,
            @RequestParam String pesel,
            Model model
    ) {
        Appointment appointment = appointmentService.findAppointmentById(appointmentId);
        appointmentService.cancelReservation(appointment);
        model.addAttribute("pesel", pesel);

        return "redirect:/patient/" + pesel;
    }

    @GetMapping("/patient/upcomingAppointments")
    public String getUpcomingAppointments(
            @RequestParam(value = "pesel") String pesel,
            ModelMap model
    ) {
        List<AppointmentDTO> upcomingAppointmentsDTO = getUpcomingAppointmentsDTO(pesel);

        model.addAttribute("pesel", pesel);
        model.addAttribute("upcomingAppointmentsDTO", upcomingAppointmentsDTO);
        return "appointments_upcoming";
    }

    @GetMapping("/patient/completedAppointments")
    public String getCompletedAppointments(
            @RequestParam(value = "pesel") String pesel,
            ModelMap model
    ) {
        List<AppointmentDTO> completedAppointmentsDTO = getCompletedAppointmentsDTO(pesel);

        model.addAttribute("pesel", pesel);
        model.addAttribute("completedAppointmentsDTO", completedAppointmentsDTO);

        return "appointments_completed";
    }

    @GetMapping("/patient/canceledAppointments")
    public String getCanceledAppointments(
            @RequestParam(value = "pesel") String pesel,
            ModelMap model
    ) {
        List<AppointmentDTO> canceledAppointmentsDTO = getCanceledAppointmentsDTO(pesel);

        model.addAttribute("pesel", pesel);
        model.addAttribute("canceledAppointmentsDTO", canceledAppointmentsDTO);

        return "appointments_canceled";
    }

    private void prepareData(ModelMap model, String pesel) {
        Patient patient = patientService.findPatientByPesel(pesel);

        model.addAllAttributes(Map.of(
                "pesel", pesel,
                "name", patient.getName(),
                "surname", patient.getSurname()
        ));
    }

    private List<AppointmentDTO> getUpcomingAppointmentsDTO(String pesel) {
        List<Appointment> allPatientAppointments = appointmentService.findAllUncompletedAppointmentsByPesel(pesel);

        return allPatientAppointments.stream()
                .map(appointmentMapper::mapUncompleted)
                .filter(value -> value.getCanceled().equals(false))
                .toList();
    }

    private List<AppointmentDTO> getCompletedAppointmentsDTO(String pesel) {
        List<Appointment> allPatientAppointments = appointmentService.findAllCompletedAppointmentsByPesel(pesel);

        return allPatientAppointments.stream()
                .map(appointmentMapper::map)
                .filter(value -> value.getCompleted().equals(true))
                .toList();
    }

    private List<AppointmentDTO> getCanceledAppointmentsDTO(String pesel) {
        List<Appointment> allPatientAppointments = appointmentService.findAllUncompletedAppointmentsByPesel(pesel);

        return allPatientAppointments.stream()
                .map(appointmentMapper::mapUncompleted)
                .filter(value -> value.getCanceled().equals(true))
                .toList();
    }
}
