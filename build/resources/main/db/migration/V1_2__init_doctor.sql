CREATE TABLE doctor
(
    doctor_id           SERIAL          NOT NULL,
    name                VARCHAR(32)     NOT NULL,
    surname             VARCHAR(32)     NOT NULL,
    specialization      VARCHAR(32)     NOT NULL,
    medical_licence     VARCHAR(32)     NOT NULL,
    PRIMARY KEY (doctor_id),
    UNIQUE (medical_licence)
);