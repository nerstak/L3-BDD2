package oo;

import Project.Database.Prepared;

public class Consultation {
    private int idPatient1;
    private int idPatient2;
    private int idPatient3;

    public static void createConsultation(int idPatient, int idAppointment) {
        Prepared p = new Prepared("INSERT INTO consultation(id_rdv,id_patient) VALUES (?,?)");
        int i = 1;
        p.setValue(i++,idAppointment);
        p.setValue(i++, idPatient);

        p.executeUpdate();
    }

}
