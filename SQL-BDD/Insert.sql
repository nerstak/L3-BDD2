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

INSERT INTO adresse(pays, region, ville, departement, code_postal, numero, rue) VALUES
('France', 'Ile-de-France', 'Paris', 'Paris', '75000', '30', 'Champs-Elysee'),
('France', 'Ile-de-France', NULL, 'Essonne', NULL, NULL, NULL),
('France', 'Bretagne', 'Vannes', 'Morbihan', NULL, NULL, NULL),
('Outre-Mer', 'Guyane', 'Cayenne', 'Guyane', NULL, NULL, NULL);

INSERT INTO patient(nom, prenom, dob, moyen, email, password, id_adresse) VALUES
('Pascal', 'Dupont', '1945-04-17', 'Pages-Jaunes', 'pascal.dupont@gmail.com', '1234', 1),
('Roger', 'Dupond', '1985-04-17', 'Internet', 'rogerdu91@toutanota.com', '12345', 2),
('Anna', 'Belle', '2004-02-29', 'Patient', 'annabelle@free.fr', 'mdp123', 3),
('Léa', 'Soutil', '1983-01-23', 'Bouche-a-Oreille', 'lea.soutil@sfr.fr', '#Ma1234', 4),
('Huges', 'Dupuit', '2012-06-30', 'Docteur', 'huges.dupuit@hotmail.fr', '1234', NULL);

INSERT INTO type_rdv(type_rdv, prix) VALUES
('familial', 40),
('conjugal', 30),
('normal', 15);

INSERT INTO rdv(id_type_rdv, date_rdv, paiement) VALUES
(1, '2020-04-17', 'Carte-Bleue'),
(2, '2019-08-17', 'Espece'),
(3, '2020-03-15', 'Cheque');

INSERT INTO historique_job(id_patient, id_job, date_debut, date_fin) VALUES
(1, 11, '2019-06-01', '2019-08-31'),
(2, 10, '2000-09-05', DATE(NOW())),
(3, 11, '2019-06-01', '2019-08-31'),
(4, 3, '1999-03-18', DATE(NOW()));

INSERT INTO consultation(id_rdv, id_patient, posture, gestuel, mots_clés, anxiete) VALUES
(1, 1, 'Ne se tient pas droit', NULL, 'pas droit', 5),
(2, 2, 'Ne se tient pas droit', 'regarde dans le vide, semble detruit interieurement', 'las', 5),
(3, 4, NULL, 'Bouge les mains sans cesse, ne fait que parler', 'surexitee', 7);

