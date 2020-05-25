package oo;

import Project.Database.MariaDB;
import Project.Database.Prepared;

public class Consultation {
    /**
     * Create a consultation in the database
     * @param idPatient     id of patient
     * @param idAppointment if of appointment
     */
    public static void createConsultation(int idPatient, int idAppointment) {
        Prepared p = new Prepared("INSERT INTO consultation(id_rdv,id_patient) VALUES (?,?)");
        int i = 1;
        p.setValue(i++, idAppointment);
        p.setValue(i, idPatient);

        p.executeUpdate();
        MariaDB.endQuery();
    }

}
