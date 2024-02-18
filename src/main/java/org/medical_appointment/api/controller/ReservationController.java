package org.medical_appointment.api.controller;


import lombok.RequiredArgsConstructor;
import org.medical_appointment.api.DTO.DoctorAvailableDTO;
import org.medical_appointment.api.DTO.mapper.DoctorAvailableMapper;
import org.medical_appointment.business.AppointmentService;
import org.medical_appointment.business.DoctorAvailableService;
import org.medical_appointment.domain.DoctorAvailable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final DoctorAvailableService doctorAvailableService;
    private final DoctorAvailableMapper doctorAvailableMapper;
    private final AppointmentService appointmentService;

    @GetMapping("patient/specialization/{specialization}")
    public String showAllAvailableDatesForSpecialization(
            @RequestParam(value = "pesel") String pesel,
            @PathVariable String specialization,
            Model model
    ) {
        List<DoctorAvailableDTO> availableSpecialization = doctorAvailableService.findBySpecialization(specialization).stream()
                .map(doctorAvailableMapper::map)
                .toList();

        Map<String, List<DoctorAvailableDTO>> groupedByMedicalLicence = availableSpecialization.stream()
                .collect(Collectors.groupingBy(dto -> dto.getDoctor().getMedicalLicence()));

        model.addAttribute("pesel", pesel);
        model.addAttribute("specialization", availableSpecialization);
        model.addAttribute("groupedByMedicalLicence", groupedByMedicalLicence);

        return "select_date_for_reservation";
    }

    @GetMapping("/reserve")
    public String confirmReservation(
            @RequestParam(value = "pesel") String pesel,
            @RequestParam(value = "doctorAvailableId") Integer doctorAvailableId,
            @RequestParam(value = "specialization") String specialization,
            @RequestParam(value = "dateTime") String dateTime,
            Model model
    ) {
        DoctorAvailable doctorAvailable = doctorAvailableService.findById(doctorAvailableId);
        DoctorAvailableDTO doctorAvailableDTO = doctorAvailableMapper.map(doctorAvailable);

        model.addAttribute("pesel", pesel);
        model.addAttribute("doctorAvailableDTO", doctorAvailableDTO);
        model.addAttribute("specialization", specialization);
        model.addAttribute("dateTime", dateTime);

        return "confirm_reservation";
    }

    @PostMapping("/reserve/confirmed")
    public String confirmedReservationAndAddAppointment(
            @RequestParam(value = "pesel") String pesel,
            @RequestParam(value = "doctorAvailableId") Integer doctorAvailableId,
            Model model

    ) {
        appointmentService.scheduleAppointment(pesel, doctorAvailableId);
        model.addAttribute("pesel", pesel);

        return "reservation_done";
    }
}
