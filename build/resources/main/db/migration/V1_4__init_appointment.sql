CREATE TABLE appointment
(
    appointment_id                    SERIAL                      NOT NULL,
    appointment_number                VARCHAR(32)                 NOT NULL,
    patient_id                        INT                         NOT NULL,
    doctor_available_id               INT                         NOT NULL,
    illness                           VARCHAR(32),
    description                       TEXT,
    completed                         BOOLEAN                     NOT NULL,
    canceled                          BOOLEAN                     NOT NULL,
    PRIMARY KEY (appointment_id),
    UNIQUE (appointment_number),
        CONSTRAINT fk_appointment_patient
            FOREIGN KEY (patient_id)
                REFERENCES patient (patient_id),
        CONSTRAINT fk_appointment_doctor_available
            FOREIGN KEY (doctor_available_id)
                REFERENCES doctor_available (doctor_available_id)
);