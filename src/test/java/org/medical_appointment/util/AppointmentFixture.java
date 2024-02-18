package org.medical_appointment.util;

import lombok.experimental.UtilityClass;
import org.medical_appointment.domain.Appointment;
import org.medical_appointment.infrastructure.database.entity.AppointmentEntity;

@UtilityClass
public class AppointmentFixture {

    public static Appointment someAppointment() {
        return Appointment.builder()
                .appointmentId(1)
                .appointmentNumber("test-number1")
                .patient(PatientFixture.somePatient())
                .doctorAvailable(DoctorAvailableFixture.someDoctorAvailable())
                .description("description1")
                .illness("heart attack")
                .completed(true)
                .canceled(false)
                .build();

//                .appointmentNumber("test-number1")
//                .dateTime("30-12-2023")
//                .description("description1")
//                .doctorName("Mark")
//                .doctorSurname("Smith")
//                .doctorSpecialization("cardiologist")
//                .doctorAvailableId(2)
//                .patientName("Eva")
//                .patientSurname("Mouse")
//                .patientPesel("75040255792")
//                .illness("heart attack")
//                .completed(true)
//                .canceled(false)
//                .reserved(true)
//                .build();
    }

    public static AppointmentEntity someAppointmentEntity() {
        return AppointmentEntity.builder()
                .appointmentId(1)
                .appointmentNumber("test-number1")
                .patient(PatientFixture.somePatientEntity())
                .doctorAvailable(DoctorAvailableFixture.someDoctorAvailableEntity())
                .description("description1")
                .illness("heart attack")
                .completed(true)
                .canceled(false)
                .build();
    }
}
