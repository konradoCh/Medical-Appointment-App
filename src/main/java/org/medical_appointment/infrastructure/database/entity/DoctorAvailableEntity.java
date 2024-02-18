package org.medical_appointment.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = {"doctorAvailableId", "dateTime", "doctor"} )
@ToString(of = {"doctorAvailableId", "dateTime", "doctor", "reserved", "deleted"} )
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctor_available")
public class DoctorAvailableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_available_id")
    private Integer doctorAvailableId;

    @Column(name = "date_time")
    private OffsetDateTime dateTime;

    @Column(name = "reserved")
    private Boolean reserved;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "completed")
    private Boolean completed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;

//    @OneToOne(fetch = FetchType.EAGER, mappedBy = "doctorAvailable")
//    private AppointmentEntity appointmentEntity;

}
