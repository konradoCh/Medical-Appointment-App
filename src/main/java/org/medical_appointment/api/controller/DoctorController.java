package org.medical_appointment.api.controller;

import lombok.RequiredArgsConstructor;
import org.medical_appointment.api.DTO.AppointmentDTO;
import org.medical_appointment.api.DTO.DoctorAvailableDTO;
import org.medical_appointment.api.DTO.DoctorDTO;
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
import org.medical_appointment.infrastructure.database.entity.DoctorAvailableEntity;
import org.medical_appointment.infrastructure.database.mapper.DoctorEntityMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DoctorController {

    static final String DOCTOR = "/doctor";
    static final String DOCTOR_STATUS = "/doctor/doctorStatus";

    private final DoctorAvailableService doctorAvailableService;
    private final DoctorService doctorService;
    private final DoctorAvailableMapper doctorAvailableMapper;
    private final AppointmentService appointmentService;

    private final DoctorEntityMapper doctorEntityMapper;
    private final DoctorMapper doctorMapper;
    private final PatientMapper patientMapper;
    private final AppointmentMapper appointmentMapper;

    @GetMapping(value = DOCTOR)
    public String homePage(Model model) {
        model.addAttribute("doctorDTO", new DoctorDTO());
        return "doctor_status";
    }

    @GetMapping("/doctor/{medicalLicence}")
    public String getDoctorAppointments(
            @PathVariable String medicalLicence,
            Model model
    ) {
        Doctor doctorByMedicalLicence = doctorService.findDoctorByMedicalLicence(medicalLicence);

        List<DoctorAvailableDTO> doctorAvailableReservedDTO = doctorAvailableService.reservedDoctorAvailable(medicalLicence).stream()
                .map(doctorAvailableMapper::map)
                .filter(doctorAvailable -> !doctorAvailable.getDeleted())
                .toList();

        List<DoctorAvailableDTO> doctorAvailableNotReservedDTO = doctorAvailableService.notReservedDoctorAvailable(medicalLicence).stream()
                .map(doctorAvailableMapper::map)
                .toList();

        model.addAttribute("doctorName", doctorByMedicalLicence.getName());
        model.addAttribute("doctorSurname", doctorByMedicalLicence.getSurname());
        model.addAttribute("medicalLicence", medicalLicence);
        model.addAttribute("doctorAvailableNotReservedDTO", doctorAvailableNotReservedDTO);
        model.addAttribute("doctorAvailableReservedDTO", doctorAvailableReservedDTO);
        model.addAttribute("addDetailOfAppointmentDTO", new AppointmentDTO());

        return "doctor_portal";
    }

    @PostMapping("doctor/add")
    public String addAppointment(
            @RequestParam(value = "date") String date,
            @RequestParam(value = "time") String time,
            @RequestParam(value = "medicalLicence") String medicalLicence
    ) {

        Doctor doctor = doctorService.findDoctorByMedicalLicence(medicalLicence);
        OffsetDateTime dateTime = OffsetDateTime.of(
                LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")),
                ZoneOffset.UTC
        );

        DoctorAvailableEntity newAppointment = DoctorAvailableEntity.builder()
                .doctor(doctorEntityMapper.mapToEntity(doctor))
                .dateTime(dateTime)
                .reserved(false)
                .deleted(false)
                .completed(false)
                .build();

        doctorAvailableService.createDoctorAvailableDate(newAppointment);

        return "redirect:/doctor/" + medicalLicence;
    }

    @DeleteMapping("doctor/delete/{doctorAvailableId}")
    @SuppressWarnings("unused")
    public String deleteEmployee(
            @PathVariable Integer doctorAvailableId,
            @RequestParam String medicalLicence
    ) {
        doctorAvailableService.deleteDoctorAvailableDate(doctorAvailableId);

        return "redirect:/doctor/" + medicalLicence;
    }

    @GetMapping("/doctor/patientInfo")
    @SuppressWarnings("unused")
    public String getPatientInfo(
            @RequestParam Integer doctorAvailableId,
            Model model
    ) {
        Appointment appointment = appointmentService.findAppointmentByDoctorAvailableId(doctorAvailableId);
        Patient patient = appointment.getPatient();
        PatientDTO patientDTO = patientMapper.map(patient);

        List<AppointmentDTO> appointmentDTO = appointmentService.findAllCompletedAppointmentsByPesel(patient.getPesel()).stream()
                .map(appointmentMapper::map)
                .toList();

        model.addAttribute("appointmentDTO", appointmentDTO);
        model.addAttribute("medicalLicence", appointment.getDoctorAvailable().getDoctor().getMedicalLicence());
        model.addAttribute("patientDTO", patientDTO);

        return "patient_info_for_doctor";
    }

    @PutMapping("/doctor/addDetailsOfAppointment")
    @SuppressWarnings("unused")
    public String addDetailsOfAppointment(
            @RequestParam Integer doctorAvailableId,
            @ModelAttribute("addDetailOfAppointmentDTO") AppointmentDTO addDetailOfAppointmentDTO,
            @RequestParam String medicalLicence,
            Model model
    ) {
        Appointment appointment = appointmentService.findAppointmentByDoctorAvailableId(doctorAvailableId);
        appointment = appointment.withIllness(addDetailOfAppointmentDTO.getIllness())
                .withDescription(addDetailOfAppointmentDTO.getDescription())
                .withCompleted(true);

        appointmentService.confirmAndAddDetailsForAppointment(appointment);
        doctorAvailableService.setAsCompletedDoctorAvailable(doctorAvailableId);

        return "redirect:/doctor/" + medicalLicence;
    }
}
