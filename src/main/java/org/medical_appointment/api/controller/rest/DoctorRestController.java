package org.medical_appointment.api.controller.rest;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.medical_appointment.api.DTO.DoctorAvailableDTO;
import org.medical_appointment.api.DTO.DoctorAvailableListDTO;
import org.medical_appointment.api.DTO.mapper.DoctorAvailableMapper;
import org.medical_appointment.business.DoctorAvailableService;
import org.medical_appointment.business.DoctorService;
import org.medical_appointment.domain.Doctor;
import org.medical_appointment.domain.DoctorAvailable;
import org.medical_appointment.infrastructure.database.entity.DoctorAvailableEntity;
import org.medical_appointment.infrastructure.database.mapper.DoctorAvailableEntityMapper;
import org.medical_appointment.infrastructure.database.mapper.DoctorEntityMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@RestController
@AllArgsConstructor
@RequestMapping(DoctorRestController.API_DOCTOR)
public class DoctorRestController {

    public static final String API_DOCTOR = "/api/doctor";
    public static final String AVAILABLE_APPOINTMENTS = "{medicalLicence}/availableAppointments";
    public static final String DOCTOR_AVAILABLE_ID = "/{doctorAvailableId}";
    public static final String DOCTOR_AVAILABLE_ID_RESULT = "/%s";

    private final DoctorAvailableService doctorAvailableService;
    private final DoctorService doctorService;
    private final DoctorEntityMapper doctorEntityMapper;
    private final DoctorAvailableEntityMapper doctorAvailableEntityMapper;
    private final DoctorAvailableMapper doctorAvailableMapper;


    @GetMapping(value = AVAILABLE_APPOINTMENTS)
    public DoctorAvailableListDTO availableAppointments(
            @PathVariable String medicalLicence
    ) {
        return DoctorAvailableListDTO.builder()
                .doctoravailables(doctorAvailableService.notReservedDoctorAvailable(medicalLicence).stream()
                        .map(doctorAvailableMapper::map)
                        .toList())
                .build();
    }

    @PostMapping
    public ResponseEntity<DoctorAvailableDTO> addDoctorAvailable(
            @RequestParam String date,
            @RequestParam String time,
            @RequestParam String medicalLicence
    ) {
        Doctor foundDoctor = doctorService.findDoctorByMedicalLicence(medicalLicence);

        OffsetDateTime dateTime = getDateTime(date, time);

        DoctorAvailableEntity newDoctorAvailable = DoctorAvailableEntity.builder()
                .dateTime(dateTime)
                .reserved(false)
                .deleted(false)
                .completed(false)
                .doctor(doctorEntityMapper.mapToEntity(foundDoctor))
                .build();

        doctorAvailableService.createDoctorAvailableDate(newDoctorAvailable);

        return ResponseEntity
                .created(URI.create(API_DOCTOR + DOCTOR_AVAILABLE_ID_RESULT.formatted(newDoctorAvailable.getDoctorAvailableId())))
                .build();
    }

    private static OffsetDateTime getDateTime(String date, String time) {
        OffsetDateTime dateTime = OffsetDateTime.of(
                LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")),
                ZoneOffset.UTC
        );
        return dateTime;
    }

    @PutMapping(DOCTOR_AVAILABLE_ID)
    public ResponseEntity<?> updateDoctorAvailable(
            @PathVariable Integer doctorAvailableId,
            @RequestParam String date,
            @RequestParam String time,
            @RequestParam String medicalLicence
    ) {
        DoctorAvailable foundDoctorAvailable = doctorAvailableService.findById(doctorAvailableId);

        if (!foundDoctorAvailable.getDoctor().getMedicalLicence().equals(medicalLicence)) {
            throw new EntityNotFoundException();
        }
        DoctorAvailableEntity doctorAvailableEntity = doctorAvailableEntityMapper.mapToEntity(foundDoctorAvailable);

        Doctor foundDoctor = doctorService.findDoctorByMedicalLicence(medicalLicence);
        doctorAvailableEntity.setDoctor(doctorEntityMapper.mapToEntity(foundDoctor));
        doctorAvailableEntity.setDateTime(getDateTime(date, time));

        doctorAvailableService.createDoctorAvailableDate(doctorAvailableEntity);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(DOCTOR_AVAILABLE_ID)
    public ResponseEntity<?> deleteDoctorAvailable(
            @PathVariable Integer doctorAvailableId
    ) {
        try {
            doctorAvailableService.deleteDoctorAvailableDate(doctorAvailableId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
