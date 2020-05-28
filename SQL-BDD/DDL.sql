drop database IF EXISTS rdvs;
CREATE DATABASE IF NOT EXISTS rdvs DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE rdvs;

DROP USER IF EXISTS login;
DROP USER IF EXISTS therapist;
DROP USER IF EXISTS patient;


/*
 Tables
 */
CREATE TABLE therapist (
    name VARCHAR(42) NOT NULL,
    password VARCHAR(200) NOT NULL,
    PRIMARY KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE job (
  id_job INT NOT NULL AUTO_INCREMENT,
  nom VARCHAR(42) NOT NULL,
  PRIMARY KEY (id_job),
  UNIQUE(nom) -- No duplication
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE patient (
  id_patient INT NOT NULL AUTO_INCREMENT,
  nom VARCHAR(42) NOT NULL,
  prenom VARCHAR(42) NOT NULL,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(200) NOT NULL,
  adresse VARCHAR(200),
  dob DATE NOT NULL,
  couple BOOLEAN DEFAULT false,
  categorie VARCHAR(42) NOT NULL DEFAULT 'adulte',
  moyen VARCHAR(42),
  PRIMARY KEY (id_patient),
  UNIQUE(email),
  CHECK(DATEDIFF(NOW(),dob) > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE OR REPLACE TABLE type_rdv (
  id_type_rdv INT NOT NULL AUTO_INCREMENT,
  type_rdv VARCHAR(42) NOT NULL,
  prix FLOAT NOT NULL,
  PRIMARY KEY (id_type_rdv),
  UNIQUE(type_rdv)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE rdv (
  id_rdv INT NOT NULL AUTO_INCREMENT,
  date_rdv DATETIME NOT NULL,
  status VARCHAR(42) NOT NULL DEFAULT 'Planned',
  payee BOOLEAN NOT NULL DEFAULT false,
  paiement VARCHAR(22) DEFAULT '',
  id_type_rdv INT NOT NULL,
  PRIMARY KEY (id_rdv),
  FOREIGN KEY (id_type_rdv) REFERENCES type_rdv (id_type_rdv) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE historique_job (
  id_historique_job INT NOT NULL AUTO_INCREMENT,
  id_patient INT NOT NULL,
  id_job INT NOT NULL,
  date_debut DATE NOT NULL DEFAULT NOW(),
  date_fin DATE,
  PRIMARY KEY (id_historique_job),
  FOREIGN KEY (id_job) REFERENCES job (id_job) ON DELETE CASCADE,
  FOREIGN KEY (id_patient) REFERENCES patient (id_patient) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE OR REPLACE TABLE consultation (
  id_patient INT NOT NULL,
  id_rdv INT NOT NULL,
  posture VARCHAR(200),
  gestuel VARCHAR(200),
  mots_cles VARCHAR(200),
  anxiete INT,
  PRIMARY KEY (id_patient, id_rdv),
  FOREIGN KEY (id_rdv) REFERENCES rdv (id_rdv) ON DELETE CASCADE,
  FOREIGN KEY (id_patient) REFERENCES patient (id_patient) ON DELETE CASCADE,
  check(anxiete >= 0 and anxiete <= 10) -- Range of value
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*
 Views
 */
 -- Complete view of the history of jobs
CREATE OR REPLACE DEFINER = CURRENT_USER
VIEW v_historique_job_complet
    AS SELECT id_historique_job, nom, id_patient, date_debut, date_fin
    FROM job
        INNER JOIN historique_job
        ON historique_job.id_job = job.id_job;

-- Complete view of the appointment & consultation
CREATE OR REPLACE DEFINER = CURRENT_USER
VIEW v_extended_appointment
    AS
    SELECT rdv.id_rdv, date_rdv, status, rdv.payee, rdv.paiement, type_rdv.type_rdv, type_rdv.prix, id_patient, gestuel, mots_cles, posture,anxiete
    FROM consultation
        INNER JOIN rdv
        ON rdv.id_rdv = consultation.id_rdv
            INNER JOIN type_rdv
            ON rdv.id_type_rdv = type_rdv.id_type_rdv;


/*
 Stored procedures
 */
-- Change job of a patient
DELIMITER |
CREATE OR REPLACE DEFINER = CURRENT_USER
PROCEDURE new_job_patient(IN job_name VARCHAR(42), IN patient_id INT)
BEGIN
    -- Variable declaration
	DECLARE v_id_job INT;
	DECLARE v_actual_name_job VARCHAR(42);
	DECLARE v_actual_job INT;

	SELECT id_job INTO v_id_job FROM job WHERE nom = job_name; -- Checking if job name is already in table
	IF (v_id_job IS NULL) THEN
		SET @insertSQL := CONCAT('INSERT INTO job(nom) VALUES ("',job_name,'")'); -- Inserting new job name w/ prepared stmt
		PREPARE stmt FROM @insertSQL;
		EXECUTE stmt;
		DEALLOCATE PREPARE stmt;
		SELECT id_job INTO v_id_job FROM job WHERE nom = job_name;
	END IF;


	SELECT MAX(id_historique_job) INTO v_actual_job FROM historique_job WHERE id_patient = patient_id; -- Selecting most recent job
	SELECT nom INTO v_actual_name_job FROM v_historique_job_complet WHERE id_historique_job = v_actual_job; -- Selecting its name

	-- Checking if the job has changed (if there was any!)
	IF(v_actual_job IS NULL OR v_actual_name_job != job_name) THEN
	    IF(v_actual_job IS NOT NULL) THEN
	        -- Terminating previous job
		    UPDATE historique_job SET date_fin = DATE(NOW()) WHERE id_historique_job = v_actual_job;
		END IF;
		INSERT INTO historique_job(id_patient, id_job) VALUES (patient_id, v_id_job);
	END IF;
END; |
DELIMITER ;

DELIMITER |
-- Create a new patient into tables
CREATE OR REPLACE DEFINER = CURRENT_USER
PROCEDURE new_patient(IN fname VARCHAR(42), IN lname VARCHAR(42),
													 IN mail VARCHAR(50), IN dob DATE, 
													 IN relation BOOLEAN, IN job_name VARCHAR(42), 
													 IN moyen VARCHAR(42), IN passw VARCHAR(200),
													 IN address VARCHAR(200),
													 OUT p_valid BOOLEAN)
BEGIN
    -- Variables declaration
	DECLARE v_id INT;

    -- Inserting patient through prepared stmt
	SET @insertSQL := CONCAT('INSERT INTO patient(nom, prenom, dob, moyen, couple, email, password, adresse)  VALUES ("',
				lname,'","',fname,'","',dob,'","',moyen,'","',relation,'","',mail,'","',passw,'","',address,'")');
	PREPARE stmt FROM @insertSQL;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	
	IF !EXISTS(SELECT id_patient FROM patient where email = mail) THEN
	    -- If insert failed
		SET p_valid = FALSE;
	ELSE
	    -- If success, we add the job
		SELECT id_patient INTO v_id FROM patient WHERE email = mail;
		CALL new_job_patient(job_name, v_id);
		SET p_valid = TRUE;
	END IF;
END; |
DELIMITER ;

DELIMITER |
-- Assign category from DoB
CREATE OR REPLACE DEFINER = CURRENT_USER
PROCEDURE check_dob(IN categorie VARCHAR(42), IN dob DATE, OUT cat_out VARCHAR(42))
BEGIN
    -- Variable declaration
    DECLARE v_diff INT;

    SELECT DATEDIFF(NOW(), dob) INTO v_diff;

    -- Selecting the correct category
    IF(v_diff > 18 * 365) THEN
        SET cat_out = 'adulte';
    ELSEIF(v_diff > 12 * 365) THEN
        SET cat_out = 'adolescent';
    ELSEIF(v_diff <= 12 *365 AND v_diff > 0) THEN
        SET cat_out = 'enfant';
    ELSE
        -- Dropping a customized SQL error
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Impossible to insert value: incorrect date of birth';
    END IF;
END; |
DELIMITER ;


/*
 Function
 */
DELIMITER |
-- Count number of appointment on one day from a datetime
CREATE OR REPLACE DEFINER = CURRENT_USER
FUNCTION numberAppointmentPlanned(dayTime datetime) RETURNS INT
BEGIN
    -- Variable declaration
    DECLARE v_day DATE;

    SELECT DATE(dayTime) INTO v_day;

    RETURN (SELECT COUNT(id_rdv) FROM rdv WHERE DATE(date_rdv) = v_day AND status != 'Cancelled');
end; |
DELIMITER ;

/*
 Triggers
 All triggers are done before insert / update to check data validity
 */
-- Trigger before insert to PATIENT
DELIMITER |
CREATE OR REPLACE TRIGGER trigg_categorie_patient_insert
    BEFORE INSERT ON patient
    FOR EACH ROW
BEGIN
    -- Variable declaration
	DECLARE cat VARCHAR(42);

    -- Category
	CALL check_dob(NEW.categorie,NEW.dob,cat);
	SET NEW.categorie = cat;

    -- Address
	IF(NEW.adresse = '') THEN
        SET NEW.adresse = NULL;
    end if;
END; |

-- Trigger before update to PATIENT
CREATE OR REPLACE TRIGGER trigg_categorie_patient_update
    BEFORE UPDATE ON patient
    FOR EACH ROW
BEGIN
    -- Variable declaration
	DECLARE cat VARCHAR(42);

    -- Category
	CALL check_dob(NEW.categorie,NEW.dob,cat);
	SET NEW.categorie = cat;

    -- Address
	IF(NEW.adresse = '') THEN
        SET NEW.adresse = NULL;
    end if;
END; |

-- Trigger before insert to RDV
CREATE OR REPLACE TRIGGER trigg_rdv_check_insert
    BEFORE INSERT ON rdv
    FOR EACH ROW
BEGIN
    IF((SELECT date_rdv FROM rdv WHERE date_rdv = NEW.date_rdv AND status != 'Cancelled') != 0 OR numberAppointmentPlanned(NEW.date_rdv) >= 20) THEN
        -- Dropping a customized SQL error
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Impossible to insert value';
    end if;

    -- Setting the correct status
    IF(datediff(date(NOW()), NEW.date_rdv) < 0) THEN
        SET NEW.status = 'Planned';
    ELSE
        SET NEW.status = 'Past';
    end if;

    -- Calculating attributes
    IF(NEW.paiement != '') THEN
        SET NEW.payee = true;
    end if;
END; |

-- Trigger before update to RDV
CREATE OR REPLACE TRIGGER trigg_rdv_check_update
    BEFORE UPDATE ON rdv
    FOR EACH ROW
BEGIN
    IF(NEW.status != 'Cancelled') THEN
        -- Setting the correct status
        IF(datediff(date(NOW()), NEW.date_rdv) < 0) THEN
            SET NEW.status = 'Planned';
        ELSE
            SET NEW.status = 'Past';
        end if;

        -- Calculating attributes
        IF(NEW.paiement != '') THEN
            SET NEW.payee = true;
        end if;
    END IF;
END; |

-- Trigger before insertion to CONSULTATION
CREATE OR REPLACE TRIGGER trigg_consultation_insert
    BEFORE INSERT ON consultation
    FOR EACH ROW
BEGIN
    IF((SELECT COUNT(id_rdv) FROM consultation WHERE id_rdv = NEW.id_rdv) >= 3) THEN
        -- Dropping a customized SQL error
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Impossible to insert value';
    end if;
end; |


DELIMITER ;

/*
 Users (creations and accesses)
 Note that no user (except root) has full rights on every table and function
 */
-- Login
CREATE USER 'login' IDENTIFIED BY 'loginPassword';
GRANT SELECT ON TABLE rdvs.patient TO login;
GRANT SELECT ON TABLE rdvs.therapist TO login;
GRANT SELECT ON TABLE rdvs.historique_job TO login; -- Following lines are needed to create a Patient in Java
GRANT SELECT ON TABLE rdvs.job TO login;
GRANT SELECT ON rdvs.v_historique_job_complet TO login;

-- Therapist
CREATE USER 'therapist' IDENTIFIED BY 'therapistPassword';
GRANT EXECUTE ON PROCEDURE check_dob TO therapist;
GRANT EXECUTE ON PROCEDURE new_job_patient TO therapist;
GRANT EXECUTE ON PROCEDURE new_patient TO therapist;
GRANT EXECUTE ON FUNCTION numberAppointmentPlanned TO therapist;
GRANT SELECT, INSERT, UPDATE ON rdvs.consultation TO therapist;
GRANT SELECT, INSERT ON rdvs.historique_job TO therapist;
GRANT SELECT, INSERT ON rdvs.job TO therapist;
GRANT SELECT, INSERT, UPDATE ON rdvs.patient TO therapist;
GRANT SELECT, INSERT, UPDATE ON rdvs.rdv TO therapist;
GRANT SELECT ON rdvs.type_rdv TO therapist;
GRANT SELECT ON rdvs.v_historique_job_complet TO therapist;
GRANT SELECT ON rdvs.v_extended_appointment TO therapist;

-- Patient
CREATE USER 'patient' IDENTIFIED BY 'patientPassword';
GRANT EXECUTE ON PROCEDURE new_job_patient TO patient;
GRANT EXECUTE ON PROCEDURE check_dob TO patient;
GRANT SELECT, INSERT ON rdvs.historique_job TO patient;
GRANT SELECT, INSERT ON rdvs.job TO patient;
GRANT SELECT, UPDATE ON rdvs.patient TO patient;
GRANT SELECT, UPDATE ON rdvs.rdv TO patient;
GRANT SELECT ON rdvs.type_rdv TO patient;
GRANT SELECT ON rdvs.consultation TO patient;
GRANT SELECT ON rdvs.v_historique_job_complet TO patient;
GRANT SELECT ON rdvs.v_extended_appointment TO patient;