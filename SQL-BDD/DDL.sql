drop database rdvs;
CREATE DATABASE IF NOT EXISTS RDVS DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE RDVS;

-- CREATE USER 'PSY' IDENTIFIED BY 'admin';
-- Creation of tables

CREATE TABLE JOB (
  id_job INT NOT NULL AUTO_INCREMENT,
  nom VARCHAR(42) NOT NULL,
  PRIMARY KEY (id_job),
  UNIQUE(nom) -- No duplication
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE PATIENT (
  id_patient INT NOT NULL AUTO_INCREMENT,
  nom VARCHAR(42) NOT NULL,
  prenom VARCHAR(42) NOT NULL,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(200) NOT NULL,
  adresse VARCHAR(200),
  dob DATE NOT NULL,
  couple BOOLEAN DEFAULT false,
  categorie VARCHAR(42) NOT NULL DEFAULT "adulte",
  moyen VARCHAR(42),
  PRIMARY KEY (id_patient),
  UNIQUE(email),
  CHECK(DATEDIFF(DATE(NOW()),dob) > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE TYPE_RDV (
  id_type_rdv INT NOT NULL AUTO_INCREMENT,
  type_rdv VARCHAR(42) NOT NULL,
  prix FLOAT NOT NULL,
  PRIMARY KEY (id_type_rdv),
  UNIQUE(type_rdv)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE RDV (
  id_rdv INT NOT NULL AUTO_INCREMENT,
  date_rdv DATETIME NOT NULL,
  payee BOOLEAN NOT NULL DEFAULT false,
  paiement VARCHAR(22) DEFAULT "X",
  id_type_rdv INT NOT NULL,
  PRIMARY KEY (id_rdv),
  FOREIGN KEY (id_type_rdv) REFERENCES TYPE_RDV (id_type_rdv) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE HISTORIQUE_JOB (
  id_historique_job INT NOT NULL AUTO_INCREMENT,
  id_patient INT NOT NULL,
  id_job INT NOT NULL,
  date_debut DATE NOT NULL DEFAULT NOW(),
  date_fin DATE,
  PRIMARY KEY (id_historique_job),
  FOREIGN KEY (id_job) REFERENCES JOB (id_job) ON DELETE CASCADE,
  FOREIGN KEY (id_patient) REFERENCES PATIENT (id_patient) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE CONSULTATION (
  id_patient INT NOT NULL,
  id_rdv INT NOT NULL,
  posture VARCHAR(200),
  gestuel VARCHAR(200),
  mots_clés VARCHAR(200),
  anxiete INT,
  PRIMARY KEY (id_patient, id_rdv),
  FOREIGN KEY (id_rdv) REFERENCES RDV (id_rdv) ON DELETE CASCADE,
  FOREIGN KEY (id_patient) REFERENCES PATIENT (id_patient) ON DELETE CASCADE,
  check(anxiete >= 0 and anxiete <= 10) -- Range of value
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Creation of views
CREATE OR REPLACE VIEW V_historique_job_complet 
AS SELECT id_historique_job, nom, id_patient, date_debut, date_fin 
FROM job
INNER JOIN historique_job 
ON historique_job.id_job = job.id_job;


-- Stored procedure
DELIMITER |
CREATE OR REPLACE PROCEDURE new_job_patient(IN job_name VARCHAR(42), IN patient_id INT)
BEGIN
	DECLARE v_id_job INT;
	DECLARE v_actual_name_job VARCHAR(42);
	DECLARE v_actual_job INT;
	
	SELECT id_job INTO v_id_job FROM job WHERE nom = job_name; -- Checking if job name is already in table
	IF (v_id_job IS NULL) THEN
		SET @insertSQL := CONCAT("INSERT INTO job(nom) VALUES ('",job_name,"')"); -- Inserting new job name w/ prepared stmt
		PREPARE stmt FROM @insertSQL;
		EXECUTE stmt;
		DEALLOCATE PREPARE stmt;
		SELECT id_job INTO v_id_job FROM job WHERE nom = job_name;
	END IF;


	SELECT MAX(id_historique_job) INTO v_actual_job FROM historique_job WHERE id_patient = patient_id; -- Selecting most recent job
	SELECT nom INTO v_actual_name_job FROM V_historique_job_complet WHERE id_historique_job = v_actual_job; -- Selecting its name
	
	IF(v_actual_name_job != job_name) THEN -- Checking if the has changed
		UPDATE historique_job SET date_fin = DATE(NOW()) WHERE id_historique_job = v_actual_job;
		INSERT INTO historique_job(id_patient, id_job) VALUES (patient_id, v_id_job);
	END IF;
END; |
DELIMITER ;

DELIMITER |
CREATE OR REPLACE PROCEDURE new_patient(IN fname VARCHAR(42), IN lname VARCHAR(42),
													 IN mail VARCHAR(50), IN dob DATE, 
													 IN relation BOOLEAN, IN job_name VARCHAR(42), 
													 IN moyen VARCHAR(42), IN passw VARCHAR(200), 
													 OUT p_valid BOOLEAN)
BEGIN
	DECLARE v_id INT;

	SET @insertSQL := CONCAT('INSERT INTO patient(nom, prenom, dob, moyen, couple, email, password)  VALUES ("',
				lname,'","',fname,'","',dob,'","',moyen,'","',relation,'","',mail,'","',passw,'")');-- Inserting patient in prepared stmt
	PREPARE stmt FROM @insertSQL;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	
	IF(ROW_COUNT() = 0) THEN -- If insert failed
		SET p_valid = FALSE;
	ELSE -- If success, we add the job
		SELECT id_patient INTO v_id FROM patient WHERE email = mail;
		CALL new_job_patient(job_name, v_id);
	END IF;
	SET p_valid = TRUE;
END; |
DELIMITER ;

DELIMITER |
CREATE OR REPLACE PROCEDURE check_dob(IN categorie VARCHAR(42), IN dob DATE, OUT cat_out VARCHAR(42))
BEGIN
    DECLARE v_diff INT;
    SELECT DATEDIFF(NOW(), dob) INTO v_diff;
    IF(v_diff > 18 * 365) THEN
        SET cat_out = "adulte";
    ELSEIF(v_diff > 12 * 365) THEN
        SET cat_out = "adolescent";
    ELSE
        SET cat_out = "enfant";
    END IF;
END; |
DELIMITER ;


-- Triggers
DELIMITER |
CREATE OR REPLACE TRIGGER trigg_categorie_patient_insert
    BEFORE INSERT ON patient
    FOR EACH ROW
BEGIN
	DECLARE cat VARCHAR(42);
	CALL check_dob(NEW.categorie,NEW.dob,cat);
	SET NEW.categorie = cat;
	IF(NEW.adresse = '') THEN
        SET NEW.adresse = NULL;
    end if;
END; |

CREATE OR REPLACE TRIGGER trigg_categorie_patient_update
    BEFORE UPDATE ON patient
    FOR EACH ROW
BEGIN
	DECLARE cat VARCHAR(42);
	CALL check_dob(NEW.categorie,NEW.dob,cat);
	SET NEW.categorie = cat;
	IF(NEW.adresse = '') THEN
        SET NEW.adresse = NULL;
    end if;
END; |


DELIMITER ;