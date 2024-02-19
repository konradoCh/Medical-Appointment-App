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