ALTER TABLE patient
ADD COLUMN user_id INT,
ADD FOREIGN KEY (user_id) REFERENCES medical_appointment_user (user_id);

ALTER TABLE doctor
ADD COLUMN user_id INT,
ADD FOREIGN KEY (user_id) REFERENCES medical_appointment_user (user_id);

INSERT INTO medical_appointment_user (user_name, email, password, active)
VALUES
('stefan_chorobliwy', 'stefanChorobliwy@gmail.com','$2a$12$p/U/oavW9LeeS9AgTeK3leqt/K.CgHeLTyuvoRTMYBnijVGVBfpoy', true),
('agnieszka_kichajska', 'agaKichaj@wp.pl', '$2a$12$p/U/oavW9LeeS9AgTeK3leqt/K.CgHeLTyuvoRTMYBnijVGVBfpoy', true),
('tomasz_kaszlacy', 'tKaszl@interia.pl', '$2a$12$p/U/oavW9LeeS9AgTeK3leqt/K.CgHeLTyuvoRTMYBnijVGVBfpoy', true),
('rafal_mocny', 'rafalMocny00@op.pl', '$2a$12$p/U/oavW9LeeS9AgTeK3leqt/K.CgHeLTyuvoRTMYBnijVGVBfpoy', true),

('robert_bialy', 'robertBialy@wp.pl', '$2a$12$p/U/oavW9LeeS9AgTeK3leqt/K.CgHeLTyuvoRTMYBnijVGVBfpoy', true),
('zygmunt_wojtunik', 'zygmuntWojtunik@gmail.com', '$2a$12$p/U/oavW9LeeS9AgTeK3leqt/K.CgHeLTyuvoRTMYBnijVGVBfpoy', true),
('remigiusz_ostry', 'remigiuszOstry@op.pl', '$2a$12$p/U/oavW9LeeS9AgTeK3leqt/K.CgHeLTyuvoRTMYBnijVGVBfpoy', true),
('mateusz_wozny', 'mateuszWozny@gmail.com', '$2a$12$p/U/oavW9LeeS9AgTeK3leqt/K.CgHeLTyuvoRTMYBnijVGVBfpoy', true);


UPDATE patient SET user_id = (SELECT user_id FROM medical_appointment_user WHERE user_name = 'stefan_chorobliwy') WHERE pesel = '67020499436';
UPDATE patient SET user_id = (SELECT user_id FROM medical_appointment_user WHERE user_name = 'agnieszka_kichajska') WHERE pesel = '73021314515';
UPDATE patient SET user_id = (SELECT user_id FROM medical_appointment_user WHERE user_name = 'tomasz_kaszlacy') WHERE pesel = '55091699846';
UPDATE patient SET user_id = (SELECT user_id FROM medical_appointment_user WHERE user_name = 'rafal_mocny') WHERE pesel = '62081825675';

UPDATE doctor SET user_id = (SELECT user_id FROM medical_appointment_user WHERE user_name = 'robert_bialy') WHERE medical_licence = '5207099';
UPDATE doctor SET user_id = (SELECT user_id FROM medical_appointment_user WHERE user_name = 'zygmunt_wojtunik') WHERE medical_licence = '8301186';
UPDATE doctor SET user_id = (SELECT user_id FROM medical_appointment_user WHERE user_name = 'remigiusz_ostry') WHERE medical_licence = '6711139';
UPDATE doctor SET user_id = (SELECT user_id FROM medical_appointment_user WHERE user_name = 'mateusz_wozny') WHERE medical_licence = '6555439';

INSERT INTO medical_appointment_role (role_id, role)
VALUES
(1, 'PATIENT'),
(2, 'DOCTOR');

INSERT INTO medical_appointment_user_role (user_id, role_id)
VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),

(5, 2),
(6, 2),
(7, 2),
(8, 2);

ALTER TABLE patient
ALTER COLUMN user_id SET NOT NULL;

ALTER TABLE doctor
ALTER COLUMN user_id SET NOT NULL;


