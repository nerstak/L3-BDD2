package oo;

import Project.Database.Prepared;
import Project.ItemComboBox;
import Project.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Appointment {
    /**
     * Get a vector of formatted types of Appointment with their prices
     *
     * @return Vector ordered by id
     */
    public static Vector<ItemComboBox> getTypesAndPrices() {
        Vector<ItemComboBox> formattedTypes = new Vector<>();
        Prepared p = new Prepared("SELECT type_rdv, prix, id_type_rdv FROM type_rdv ORDER BY id_type_rdv");
        try {
            ResultSet r = p.executeQuery();
            while (r.next()) {
                String tmp = r.getString(1) + ": " + r.getInt(2) + "€";
                tmp = tmp.substring(0, 1).toUpperCase() + tmp.substring(1);
                formattedTypes.add(new ItemComboBox(r.getInt(3), tmp));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return formattedTypes;
    }


    public static Pair<Boolean, String> createAppointment(String patient1, String patient2, String patient3, Integer type_id, String datetime) {
        Pair<Boolean, String> result = checkValuesAppointment(patient1, patient2, patient3, type_id, datetime);
        if (result.getA()) {
            int idAppointment = insertAppointment(type_id, datetime);

            if(idAppointment > 0) {
                int idPatient1 = User.verifyUserMail(patient1);
                int idPatient2 = User.verifyUserMail(patient2);
                int idPatient3 = User.verifyUserMail(patient3);
                if(idPatient1 != -1) {
                    Consultation.createConsultation(idPatient1,idAppointment);
                }
                if(idPatient2 != -1) {
                    Consultation.createConsultation(idPatient2,idAppointment);
                }
                if(idPatient3 != -1) {
                    Consultation.createConsultation(idPatient3,idAppointment);
                }
            } else {
                result.setA(false);
                result.setB("interference with other appointments");
            }

        }

        return result;
    }

    /**
     * Verify that values given are coherent
     *
     * @param patient1 Mail of patient
     * @param patient2 Mail of patient
     * @param patient3 Mail of patient
     * @param type_id  Type of appointment
     * @param datetime Date in String
     * @return Integrity and error message
     */
    private static Pair<Boolean, String> checkValuesAppointment(String patient1, String patient2, String patient3, Integer type_id, String datetime) {
        Pair<Boolean, String> result = new Pair(false, "");
        Date appointmentTime;

        int idPatient1 = User.verifyUserMail(patient1);
        int idPatient2 = User.verifyUserMail(patient2);
        int idPatient3 = User.verifyUserMail(patient3);

        // Checking if patients are known
        if (idPatient1 == -1 && !patient1.isBlank() || idPatient2 == -1 && !patient2.isBlank() || idPatient3 == -1 && !patient3.isBlank()) {
            result.setB("unknown patient");
        }

        // Patients fields must be filled in correct order
        if (idPatient1 == -1 || (idPatient2 == -1 && idPatient3 != -1)) {
            result.setB("fields incorrectly filled");
        }

        try {
            appointmentTime = new SimpleDateFormat("yyyy-MM-dd HH'h'mm").parse(datetime);
        } catch (Exception e) {
            result.setB("incorrect date");
            return result;
        }

        if (appointmentTime.before(new Date())) {
            result.setB("impossible date");
        }

        // Verifying if any error
        if (result.getB().isEmpty()) {
            result.setA(true);
        }

        return result;
    }

    /**
     * Insert Appointment
     *
     * @param type_id  Type of appointment
     * @param datetime Timestamp of appointment
     * @return Appointment ID
     */
    private static Integer insertAppointment(Integer type_id, String datetime) {
        Date appointmentTime;

        try {
            appointmentTime = new SimpleDateFormat("yyyy-MM-dd HH'h'mm").parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }

        // Inserting
        Prepared p = new Prepared("INSERT INTO rdv(date_rdv, id_type_rdv) VALUES (?,?)");
        int i = 1;
        p.setValue(i++, new java.sql.Timestamp(appointmentTime.getTime()), Types.TIMESTAMP);
        p.setValue(i++, type_id);
        p.executeUpdate();

        // Getting appointment id
        p = new Prepared("SELECT @@IDENTITY");
        ResultSet r = p.executeQuery();
        try {
            if (r.next()) {
                if(r.getInt(1) == 0) {
                    return -1;
                } else {
                    return r.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
