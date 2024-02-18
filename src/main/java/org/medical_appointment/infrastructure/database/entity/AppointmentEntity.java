package org.medical_appointment.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = "appointmentNumber")
@ToString(of = {"appointmentNumber"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointment")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Integer appointmentId;

    @Column(name = "appointment_number")
    private String appointmentNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "completed")
    private Boolean completed;

    @Column(name = "canceled")
    private Boolean canceled;

    @Column(name = "illness")
    private String illness;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_available_id")
    private DoctorAvailableEntity doctorAvailable;
}
