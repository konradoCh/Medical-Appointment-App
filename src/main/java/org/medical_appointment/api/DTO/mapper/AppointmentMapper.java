package org.medical_appointment.api.DTO.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.medical_appointment.api.DTO.AppointmentDTO;
import org.medical_appointment.domain.Appointment;

@Mapper(componentModel = "spring")
public interface AppointmentMapper extends OffsetDateTimeMapper {

    @Mapping(source = "dateTime", target = "dateTime", qualifiedByName = "mapOffsetDateTimeToString")
    default AppointmentDTO map(Appointment appointment) {
        return AppointmentDTO.builder()
                .appointmentId(appointment.getAppointmentId())
                .appointmentNumber(appointment.getAppointmentNumber())
                .dateTime(mapOffsetDateTimeToString(appointment.getDoctorAvailable().getDateTime()))
                .description(appointment.getDescription())
                .doctorName(appointment.getDoctorAvailable().getDoctor().getName())
                .doctorSurname(appointment.getDoctorAvailable().getDoctor().getSurname())
                .doctorSpecialization(appointment.getDoctorAvailable().getDoctor().getSpecialization())
                .doctorAvailableId(appointment.getDoctorAvailable().getDoctorAvailableId())
                .patientName(appointment.getPatient().getName())
                .patientSurname(appointment.getPatient().getSurname())
                .patientSurname(appointment.getPatient().getSurname())
                .patientPesel(appointment.getPatient().getPesel())
                .completed(appointment.getCompleted())
                .canceled(appointment.getCanceled())
                .reserved(appointment.getDoctorAvailable().getReserved())
                .illness(appointment.getIllness())
                .build();
    }

    @Mapping(source = "dateTime", target = "dateTime", qualifiedByName = "mapOffsetDateTimeToString")
    default AppointmentDTO mapUncompleted(Appointment appointment) {
        return AppointmentDTO.builder()
                .appointmentId(appointment.getAppointmentId())
                .appointmentNumber(appointment.getAppointmentNumber())
                .dateTime(mapOffsetDateTimeToString(appointment.getDoctorAvailable().getDateTime()))
                .description(appointment.getDescription())
                .doctorName(appointment.getDoctorAvailable().getDoctor().getName())
                .doctorSurname(appointment.getDoctorAvailable().getDoctor().getSurname())
                .doctorSpecialization(appointment.getDoctorAvailable().getDoctor().getSpecialization())
                .doctorAvailableId(appointment.getDoctorAvailable().getDoctorAvailableId())
                .patientName(appointment.getPatient().getName())
                .patientSurname(appointment.getPatient().getSurname())
                .patientSurname(appointment.getPatient().getSurname())
                .patientPesel(appointment.getPatient().getPesel())
                .completed(appointment.getCompleted())
                .canceled(appointment.getCanceled())
                .reserved(appointment.getDoctorAvailable().getReserved())
                .build();
    }
}
