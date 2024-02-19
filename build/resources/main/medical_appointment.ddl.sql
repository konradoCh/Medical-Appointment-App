DROP TABLE IF EXISTS appointment CASCADE;
DROP TABLE IF EXISTS medical_appointment_request CASCADE;
DROP TABLE IF EXISTS illness CASCADE;
DROP TABLE IF EXISTS doctor CASCADE;
DROP TABLE IF EXISTS patient CASCADE;

CREATE TABLE patient
(
    patient_id  SERIAL  NOT NULL,
    name        VARCHAR(32)     NOT NULL,
    surname     VARCHAR(32)     NOT NULL,
    phone       VARCHAR(32)     NOT NULL,
    email       VARCHAR(60)     NOT NULL,
    pesel       VARCHAR(32)     NOT NULL,
    PRIMARY KEY (patient_id),
    UNIQUE (pesel)
);

CREATE TABLE doctor
(
    doctor_id  SERIAL  NOT NULL,
    name                VARCHAR(32)     NOT NULL,
    surname             VARCHAR(32)     NOT NULL,
    specialization      VARCHAR(32)     NOT NULL,
    medical_licence     VARCHAR(32)     NOT NULL,
    PRIMARY KEY (doctor_id),
    UNIQUE (medical_licence)
);

CREATE TABLE illness
(
    illness_id      SERIAL          NOT NULL,
    illness_code     VARCHAR(32)     NOT NULL,
    PRIMARY KEY (illness_id)
);

CREATE TABLE medical_appointment_request
(
    medical_appointment_request_id  SERIAL                      NOT NULL,
    medical_appointment_number      VARCHAR(32)                 NOT NULL,
    date_time                       TIMESTAMP WITH TIME ZONE    NOT NULL,
    patient_id                      INT                         NOT NULL,
    doctor_id                       INT                         NOT NULL,
    PRIMARY KEY (medical_appointment_request_id),
    UNIQUE (medical_appointment_number),
        CONSTRAINT fk_medical_appointment_request_patient
                    FOREIGN KEY (patient_id)
                        REFERENCES patient (patient_id),
        CONSTRAINT fk_medical_appointment_request_doctor
                            FOREIGN KEY (doctor_id)
                                REFERENCES doctor (doctor_id)
);

CREATE TABLE appointment
(
    appointment_id                   SERIAL         NOT NULL,
    description                     VARCHAR(32)     NOT NULL,
    illness_id                      INT             NOT NULL,
    medical_appointment_request_id  INT             NOT NULL,
    PRIMARY KEY (appointment_id),
        CONSTRAINT fk_appointment_illness
            FOREIGN KEY (illness_id)
                REFERENCES illness (illness_id),
        CONSTRAINT fk_appointment_medical_appointment_request
                    FOREIGN KEY (medical_appointment_request_id)
                        REFERENCES medical_appointment_request (medical_appointment_request_id)
);