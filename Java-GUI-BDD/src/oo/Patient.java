package oo;

import Project.Database.Callable;
import Project.Database.Prepared;
import Project.Utilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Patient extends User {
    private String _firstName;
    private String _lastName;
    private String _mail;
    private Date _dob;
    private Boolean _relation;
    private String _job;
    private String _address;

    /**
     * Create Patient from database
     * @param _id ID of patient
     */
    public Patient(Integer _id) {
        super(_id);

        // Selecting element from patient table
        Prepared m = new Prepared("SELECT nom, prenom, email, dob, couple, adresse FROM patient WHERE id_patient=?");
        m.setValue(1, _id);
        try {
            ResultSet x = m.executeQuery();
            if (x.next()) {
                _lastName = x.getString(1);
                _firstName = x.getString(2);
                _mail = x.getString(3);
                _dob = x.getDate(4);
                _relation = x.getBoolean(5);
                _address = x.getString(6);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }


        // Selecting job
        m = new Prepared("SELECT nom FROM V_historique_job_complet" +
                " WHERE id_patient = ? AND id_historique_job = (" +
                "       SELECT MAX(id_historique_job)" +
                "       FROM historique_job " +
                "       WHERE id_patient = ?)");
        m.setValue(1, _id);
        m.setValue(2, _id);
        try{
            ResultSet x = m.executeQuery();
            if(x.next())
            {
                _job = x.getString(1);
            }
        }catch (SQLException e){
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    /**
     * Create Patient from data and add it to database
     * @param _firstName First name
     * @param _lastName Last name
     * @param _mail Mail address
     * @param _dob Dob
     * @param _relation Relation
     * @param _job Job name
     */
    public static String createPatient(String _firstName, String _lastName, String _mail, String _dob, Boolean _relation, String _job, String password, String moyen, String address) {
        Date dob;
        if(_firstName.isEmpty()) {
            return "first name";
        }
        if(_lastName.isEmpty()) {
            return "last name";
        }
        _mail = _mail.replaceAll("\\s+", ""); // Remove white spaces
        if (!Utilities.isValidMail(_mail)) {
            return "mail";
        }
        try {
            dob = new SimpleDateFormat("yyyy-MM-dd").parse(_dob);
        } catch (Exception e) {
            return "date of birth";
        }
        if(_job.isEmpty()) {
            return "job";
        }
        if(password.isEmpty()) {
            return "password";
        }

        Callable c = new Callable("CALL new_patient(?,?,?,?,?,?,?,?,?,?)");
        Integer index = 1;

        c.setValue(index++, _firstName);
        c.setValue(index++, _lastName);
        c.setValue(index++, _mail);
        c.setValue(index++, new java.sql.Date(dob.getTime()), Types.DATE);
        c.setValue(index++, _relation, Types.BOOLEAN);
        c.setValue(index++, _job);
        c.setValue(index++, moyen);
        c.setValue(index++, password);
        c.setValue(index++, address);
        c.registerOutParameter(index, Types.BOOLEAN);

        c.executeUpdate();

        Boolean result = c.getBoolean(index);
        if(result) {
            return "";
        } else {
            return "creation";
        }
    }

    public String get_firstName() {
        return _firstName;
    }

    public String get_lastName() {
        return _lastName;
    }

    public String get_mail() {
        return _mail;
    }

    public Date get_dob() {
        return _dob;
    }

    public Boolean get_relation() {
        return _relation;
    }

    public String get_job(){
        return _job;
    }

    public String updateFields(String firstName, String lastName, String email, String dobString, Boolean couple, String password, String job, String adress) {
        Prepared m;
        Date dob;
        if (firstName.isEmpty()) {
            return "First Name";
        }
        if (lastName.isEmpty()) {
            return "Last Name";
        }
        email = email.replaceAll("\\s+", ""); // Remove white spaces
        if (!Utilities.isValidMail(email)) {
            return "Mail Address";
        }
        if(job.isEmpty())
        {
            return "Job";
        }
        try {
            dob = new SimpleDateFormat("yyyy-MM-dd").parse(dobString);
        } catch (Exception e) {
            return "date of birth";
        }

        // First we update direct information of the user
        if (password.isEmpty()) {
            m = new Prepared("UPDATE patient SET nom = ?, prenom = ?, email = ?, dob = ?, couple = ?, adresse = ? WHERE id_patient = ?");
        } else {
            m = new Prepared("UPDATE patient SET nom = ?, prenom = ?, email = ?, dob = ?, couple = ?, adresse = ?, password = ? WHERE id_patient = ?");
        }
        
        Integer index = 1;
        m.setValue(index++, lastName);
        m.setValue(index++, firstName);
        m.setValue(index++, email);
        m.setValue(index++, new java.sql.Date(dob.getTime()), Types.DATE);
        m.setValue(index++, couple, Types.BOOLEAN);
        m.setValue(index++, adress);
        if (!password.isEmpty()) {
            m.setValue(index++, password);
        }
        m.setValue(index++, get_id());
        m.executeUpdate();

        // Now updating name
        Callable c = new Callable("Call new_job_patient(?,?)");
        c.setValue(1, job);
        c.setValue(2, get_id());
        c.executeUpdate();
        return "";
    }

    public String get_address() {
        return _address;
    }
}
