package oo;

import Project.Database.MariaDB;
import Project.Database.Prepared;
import Project.ItemComboBox;
import Project.Pair;
import Project.Utilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class Appointment {
    private final int idAppointment;
    private String payment;
    private boolean payed;
    private final String type;
    private final Date appointmentTime;
    private final double price;
    private String status;
    private final String email;
    private String gesture;
    private String keywords;
    private String position;
    private int anxiety;

    public Appointment(int idAppointment, String payment, boolean payed, String type, Date appointmentTime, double price, String status, String email, String gesture, String keywords, String position, int anxiety) {
        this.idAppointment = idAppointment;
        this.payment = payment;
        this.payed = payed;
        this.type = type;
        this.appointmentTime = appointmentTime;
        this.price = price;
        this.status = status;
        this.email = email;
        this.gesture = gesture;
        this.keywords = keywords;
        this.position = position;
        this.anxiety = anxiety;
    }

    public int getIdAppointment() {
        return idAppointment;
    }

    public String getPayment() {
        return payment;
    }

    public boolean isPayed() {
        return payed;
    }

    public String getType() {
        return type;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGesture() {
        return gesture;
    }

    public void setGesture(String gesture) {
        this.gesture = gesture;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getAnxiety() {
        return anxiety;
    }

    public void setAnxiety(int anxiety) {
        this.anxiety = anxiety;
    }

    public static ArrayList<Appointment> recoverAppointments(int idUser) {
        ArrayList<Appointment> list = new ArrayList<>();

        Prepared p;
        if (idUser == -1) {
            p = new Prepared("SELECT id_rdv, date_rdv, status, type_rdv, prix, paiement, payee, v_extended_appointment.id_patient, patient.email, gestuel, mots_cles, posture, anxiete " +
                    "FROM v_extended_appointment " +
                    "INNER JOIN patient ON v_extended_appointment.id_patient = patient.id_patient " +
                    "ORDER BY date_rdv DESC");
        } else {
            p = new Prepared("SELECT id_rdv, date_rdv, status, type_rdv, prix, paiement, payee, v_extended_appointment.id_patient, patient.email, gestuel, mots_cles, posture, anxiete " +
                    "FROM  v_extended_appointment " +
                    "INNER JOIN patient ON v_extended_appointment.id_patient = patient.id_patient " +
                    "WHERE v_extended_appointment.id_patient = ? " +
                    "ORDER BY date_rdv DESC");
            p.setValue(1, idUser);
        }


        try {
            ResultSet r = p.executeQuery();
            while(r.next()) {
                Appointment a = new Appointment(r.getInt(1),
                                                r.getString(6),
                                                r.getBoolean(7),
                                                r.getString(4),
                                                new Date(r.getTimestamp(2).getTime()),
                                                r.getFloat(5),
                        r.getString(3),
                        r.getString(9),
                        r.getString(10),
                        r.getString(11),
                        r.getString(12),
                        r.getInt(13)
                );
                list.add(a);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }


        return list;
    }

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
                tmp = Utilities.capitalizeFirstLetter(tmp);
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
        MariaDB.endQuery();

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

    /**
     * Update an appointment in DB based on the local values
     */
    public void updateAppointment() {
        Prepared p = new Prepared("UPDATE rdv SET status = ?, paiement = ?, payee = ? WHERE id_rdv = ?");

        int i = 1;
        p.setValue(i++, status);
        p.setValue(i++, payment);
        p.setValue(i++, payed);
        p.setValue(i, idAppointment);

        p.executeUpdate();
        MariaDB.endQuery();
    }

    /**
     * Update a consultation in DB based on the local values
     */
    public void updateConsultation() {
        int idPatient = Patient.verifyUserMail(email);
        Prepared p = new Prepared("UPDATE consultation SET gestuel = ?, mots_cles = ?, posture = ?, anxiete = ? WHERE id_rdv = ? AND id_patient = ?");

        int i = 1;
        p.setValue(i++, gesture);
        p.setValue(i++, keywords);
        p.setValue(i++, position);
        p.setValue(i++, anxiety);
        p.setValue(i++, idAppointment);
        p.setValue(i, idPatient);

        p.executeUpdate();
        MariaDB.endQuery();
    }
}
