package org.medical_appointment.infrastructure.database.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.medical_appointment.domain.Appointment;
import org.medical_appointment.domain.Doctor;
import org.medical_appointment.domain.DoctorAvailable;
import org.medical_appointment.domain.Patient;
import org.medical_appointment.infrastructure.database.entity.AppointmentEntity;
import org.medical_appointment.infrastructure.database.entity.DoctorAvailableEntity;
import org.medical_appointment.infrastructure.database.entity.DoctorEntity;
import org.medical_appointment.infrastructure.database.entity.PatientEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppointmentEntityMapper {

    //    @Mapping(target = "appointment", ignore = true)
    default Appointment mapFromEntity(AppointmentEntity entity) {
        return Appointment.builder()
                .appointmentId(entity.getAppointmentId())
                .appointmentNumber(entity.getAppointmentNumber())
                .patient(Patient.builder()
                        .patientId(entity.getPatient().getPatientId())
                        .name(entity.getPatient().getName())
                        .surname(entity.getPatient().getSurname())
                        .phone(entity.getPatient().getPhone())
                        .email(entity.getPatient().getEmail())
                        .pesel(entity.getPatient().getPesel())
                        .build())
                .doctorAvailable(DoctorAvailable.builder()
                        .doctorAvailableId(entity.getDoctorAvailable().getDoctorAvailableId())
                        .dateTime(entity.getDoctorAvailable().getDateTime())
                        .reserved(entity.getDoctorAvailable().getReserved())
                        .doctor(Doctor.builder()
                                .doctorId(entity.getDoctorAvailable().getDoctor().getDoctorId())
                                .name(entity.getDoctorAvailable().getDoctor().getName())
                                .surname(entity.getDoctorAvailable().getDoctor().getSurname())
                                .specialization(entity.getDoctorAvailable().getDoctor().getSpecialization())
                                .medicalLicence(entity.getDoctorAvailable().getDoctor().getMedicalLicence())
                                .build())
                        .build())
                .description(entity.getDescription())
                .completed(entity.getCompleted())
                .canceled(entity.getCanceled())
                .illness(entity.getIllness())
                .build();
    }


    default AppointmentEntity mapToEntity(Appointment scheduledAppointment) {
        return AppointmentEntity.builder()
                .appointmentId(scheduledAppointment.getAppointmentId())
                .appointmentNumber(scheduledAppointment.getAppointmentNumber())
                .description(scheduledAppointment.getDescription())
                .completed(scheduledAppointment.getCompleted())
                .canceled(scheduledAppointment.getCanceled())
                .patient(PatientEntity.builder()
                        .patientId(scheduledAppointment.getPatient().getPatientId())
                        .name(scheduledAppointment.getPatient().getName())
                        .surname(scheduledAppointment.getPatient().getSurname())
                        .phone(scheduledAppointment.getPatient().getPhone())
                        .email(scheduledAppointment.getPatient().getEmail())
                        .pesel(scheduledAppointment.getPatient().getPesel())
                        .build())
                .doctorAvailable(DoctorAvailableEntity.builder()
                        .doctorAvailableId(scheduledAppointment.getDoctorAvailable().getDoctorAvailableId())
                        .dateTime(scheduledAppointment.getDoctorAvailable().getDateTime())
                        .reserved(scheduledAppointment.getDoctorAvailable().getReserved())
                        .doctor(DoctorEntity.builder()
                                .doctorId(scheduledAppointment.getDoctorAvailable().getDoctor().getDoctorId())
                                .name(scheduledAppointment.getDoctorAvailable().getDoctor().getName())
                                .surname(scheduledAppointment.getDoctorAvailable().getDoctor().getSurname())
                                .specialization(scheduledAppointment.getDoctorAvailable().getDoctor().getSpecialization())
                                .medicalLicence(scheduledAppointment.getDoctorAvailable().getDoctor().getMedicalLicence())
                                .build())
                        .build())
                .illness(scheduledAppointment.getIllness())
                .build();
    }

    default AppointmentEntity mapToEntityWithoutIllness(Appointment scheduledAppointment) {
        return AppointmentEntity.builder()
                .appointmentId(scheduledAppointment.getAppointmentId())
                .appointmentNumber(scheduledAppointment.getAppointmentNumber())
                .description(scheduledAppointment.getDescription())
                .completed(scheduledAppointment.getCompleted())
                .canceled(scheduledAppointment.getCanceled())
                .patient(PatientEntity.builder()
                        .patientId(scheduledAppointment.getPatient().getPatientId())
                        .name(scheduledAppointment.getPatient().getName())
                        .surname(scheduledAppointment.getPatient().getSurname())
                        .phone(scheduledAppointment.getPatient().getPhone())
                        .email(scheduledAppointment.getPatient().getEmail())
                        .pesel(scheduledAppointment.getPatient().getPesel())
                        .build())
                .doctorAvailable(DoctorAvailableEntity.builder()
                        .doctorAvailableId(scheduledAppointment.getDoctorAvailable().getDoctorAvailableId())
                        .dateTime(scheduledAppointment.getDoctorAvailable().getDateTime())
                        .reserved(scheduledAppointment.getDoctorAvailable().getReserved())
                        .doctor(DoctorEntity.builder()
                                .doctorId(scheduledAppointment.getDoctorAvailable().getDoctor().getDoctorId())
                                .name(scheduledAppointment.getDoctorAvailable().getDoctor().getName())
                                .surname(scheduledAppointment.getDoctorAvailable().getDoctor().getSurname())
                                .specialization(scheduledAppointment.getDoctorAvailable().getDoctor().getSpecialization())
                                .medicalLicence(scheduledAppointment.getDoctorAvailable().getDoctor().getMedicalLicence())
                                .build())
                        .build())
                .build();
    }

    default Appointment mapFromEntityWithoutIllness(AppointmentEntity entity) {
        return Appointment.builder()
                .appointmentId(entity.getAppointmentId())
                .appointmentNumber(entity.getAppointmentNumber())
                .patient(Patient.builder()
                        .patientId(entity.getPatient().getPatientId())
                        .name(entity.getPatient().getName())
                        .surname(entity.getPatient().getSurname())
                        .phone(entity.getPatient().getPhone())
                        .email(entity.getPatient().getEmail())
                        .pesel(entity.getPatient().getPesel())
                        .build())
                .doctorAvailable(DoctorAvailable.builder()
                        .doctorAvailableId(entity.getDoctorAvailable().getDoctorAvailableId())
                        .dateTime(entity.getDoctorAvailable().getDateTime())
                        .reserved(entity.getDoctorAvailable().getReserved())
                        .doctor(Doctor.builder()
                                .doctorId(entity.getDoctorAvailable().getDoctor().getDoctorId())
                                .name(entity.getDoctorAvailable().getDoctor().getName())
                                .surname(entity.getDoctorAvailable().getDoctor().getSurname())
                                .specialization(entity.getDoctorAvailable().getDoctor().getSpecialization())
                                .medicalLicence(entity.getDoctorAvailable().getDoctor().getMedicalLicence())
                                .build())
                        .build())
                .description(entity.getDescription())
                .completed(entity.getCompleted())
                .canceled(entity.getCanceled())
                .build();
    }
}
