INSERT INTO job(nom) VALUES
('agriculteur'),
('artiste'),
('cadre'),
('chomeur'),
('docteur'),
('etudiant'),
('fonctionnaire'),
('ingénieur'),
('ouvrier'),
('professeur'),
('saisonnier');

INSERT INTO patient(nom, prenom, dob, moyen, email, password, adresse) VALUES
('Pascal', 'Dupont', '1945-04-17', 'Pages-Jaunes', 'pascal.dupont@gmail.com', '1234', '30 avenue des Champs-Elysee, Paris 75000, France'),
('Roger', 'Dupond', '1985-04-17', 'Internet', 'rogerdu91@toutanota.com', '12345', 'Essone'),
('Anna', 'Belle', '2004-02-29', 'Patient', 'annabelle@free.fr', 'mdp123', 'Vannes, Bretagne'),
('Léa', 'Soutil', '1983-01-23', 'Bouche-a-Oreille', 'lea.soutil@sfr.fr', '#Ma1234', 'Cayenne, Guyane'),
('Huges', 'Dupuit', '2012-06-30', 'Docteur', 'huges.dupuit@hotmail.fr', '1234', NULL);

INSERT INTO type_rdv(type_rdv, prix) VALUES
('familial', 40),
('conjugal', 30),
('normal', 15);

INSERT INTO rdv(id_type_rdv, date_rdv, paiement) VALUES
(1, '2020-04-17 14:00:00', 'Carte-Bleue'),
(2, '2019-08-17 14:30:00', 'Espece'),
(3, '2020-01-05 08:00:00', 'Chèque'),
(3, '2020-09-30 12:00:00', ''),
(2, '2021-12-16 16:30:00', ''),
(3, '2020-09-30 10:00:00', ''),
(3, '2020-09-30 09:00:00', ''),
(2, '2020-09-30 08:30:00', ''),
(3, '2020-09-30 09:30:00', ''),
(1, '2020-09-30 10:30:00', ''),
(1, '2020-09-30 11:00:00', ''),
(2, '2020-09-30 12:30:00', ''),
(2, '2020-09-30 13:00:00', ''),
(2, '2020-09-30 14:00:00', ''),
(2, '2020-09-30 14:30:00', ''),
(2, '2020-09-30 15:00:00', ''),
(2, '2020-09-30 15:30:00', ''),
(2, '2020-09-30 16:00:00', ''),
(2, '2020-09-30 16:30:00', ''),
(2, '2020-09-30 17:00:00', ''),
(2, '2020-09-30 17:30:00', ''),
(3, '2020-09-30 08:00:00', ''),
(1, '2020-10-05 08:00:00', '');

UPDATE rdv SET status = 'Cancelled' WHERE id_rdv = 22;

INSERT INTO historique_job(id_patient, id_job, date_debut, date_fin) VALUES
(1, 11, '2019-06-01', '2019-08-31'),
(1, 5, '2019-08-31', NULL),
(2, 10, '2000-09-05', NULL),
(3, 6, '2019-06-01', '2019-08-31'),
(3, 4, '2019-08-31', NULL),
(4, 3, '1999-03-18', NULL);

INSERT INTO consultation(id_rdv, id_patient, posture, gestuel, mots_cles, anxiete) VALUES
(1, 1, 'Ne se tient pas droit', NULL, 'pas droit', 5),
(2, 2, 'Ne se tient pas droit', 'regarde dans le vide, semble detruit interieurement', 'las', 5),
(3, 4, NULL, 'Bouge les mains sans cesse, ne fait que parler', 'surexitee', 7),
(4,5,NULL,NULL,NULL,NULL),
(4,3,NULL,NULL,NULL,NULL),
(5,4,NULL,NULL,NULL,NULL),
(6,2,NULL,NULL,NULL,NULL),
(7,4,NULL,NULL,NULL,NULL),
(7,5,NULL,NULL,NULL,NULL),
(8,4,NULL,NULL,NULL,NULL),
(9,3,NULL,NULL,NULL,NULL),
(11,1,NULL,NULL,NULL,NULL),
(11,2,NULL,NULL,NULL,NULL),
(12,1,NULL,NULL,NULL,NULL),
(13,1,NULL,NULL,NULL,NULL),
(14,1,NULL,NULL,NULL,NULL),
(15,5,NULL,NULL,NULL,NULL),
(16,4,NULL,NULL,NULL,NULL),
(17,3,NULL,NULL,NULL,NULL),
(18,2,NULL,NULL,NULL,NULL),
(19,4,NULL,NULL,NULL,NULL),
(20,2,NULL,NULL,NULL,NULL),
(21,5,NULL,NULL,NULL,NULL),
(21,1,NULL,NULL,NULL,NULL),
(22,4,NULL,NULL,NULL,NULL);

INSERT INTO therapist(name, password) VALUES
('admin','admin');