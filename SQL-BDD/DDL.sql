CREATE DATABASE IF NOT EXISTS RDVS DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE RDVS;

CREATE TABLE JOB (
  id_job INT NOT NULL AUTO_INCREMENT,
  nom VARCHAR(42) NOT NULL,
  PRIMARY KEY (id_job),
  UNIQUE(nom) -- No duplication
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE ADRESSE (
  id_adresse INT NOT NULL AUTO_INCREMENT,
  pays VARCHAR(42) NOT NULL,
  region VARCHAR(42) NOT NULL,
  ville VARCHAR(42),
  departement VARCHAR(42) NOT NULL,
  code_postal VARCHAR(42),
  numero VARCHAR(42),
  rue VARCHAR(42),
  PRIMARY KEY (id_adresse)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE PATIENT (
  id_patient INT NOT NULL AUTO_INCREMENT,
  nom VARCHAR(42) NOT NULL,
  prenom VARCHAR(42) NOT NULL,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(200) NULL,
  dob Datetime NOT NULL,
  couple BOOLEAN DEFAULT false,
  categorie VARCHAR(42) NOT NULL,
  moyen VARCHAR(42),
  id_adresse INT, -- Adress can be null
  PRIMARY KEY (id_patient),
  FOREIGN KEY (id_adresse) REFERENCES ADRESSE (id_adresse) ON DELETE SET NULL
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
  paiement BOOLEAN NOT NULL DEFAULT false,
  id_type_rdv INT NOT NULL,
  PRIMARY KEY (id_rdv),
  FOREIGN KEY (id_type_rdv) REFERENCES TYPE_RDV (id_type_rdv) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE HISTORIQUE_JOB (
  id_patient INT NOT NULL,
  id_job INT NOT NULL,
  date_debut Datetime NOT NULL DEFAULT NOW(),
  date_fin Datetime,
  PRIMARY KEY (id_patient, id_job),
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