CREATE TABLE doctor_available
(
    doctor_available_id      SERIAL                      NOT NULL,
    date_time                TIMESTAMP WITH TIME ZONE    NOT NULL,
    reserved                 BOOLEAN,
    deleted                  BOOLEAN,
    completed                BOOLEAN,
    doctor_id                INT                         NOT NULL,
    PRIMARY KEY (doctor_available_id),
        CONSTRAINT fk_doctor
            FOREIGN KEY (doctor_id)
                REFERENCES doctor (doctor_id)
);