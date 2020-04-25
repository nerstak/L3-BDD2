package oo;

import Project.MariaDB;
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

    public Patient(Integer _id) {
        super(_id);

        // Selecting element from patient table
        MariaDB m = new MariaDB("SELECT nom, prenom, email, dob, couple FROM patient WHERE id_patient=?");
        m.setValue(1, _id);
        try {
            ResultSet x = m.executeQuery();
            if (x.next()) {
                _firstName = x.getString(1);
                _lastName = x.getString(2);
                _mail = x.getString(3);
                _dob = x.getDate(4);
                _relation = x.getBoolean(5);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }


        // Selecting job
        m = new MariaDB("SELECT nom FROM V_historique_job_complet" +
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

    public String updateFields(String firstName, String lastName, String email, String dobString, Boolean couple, String password, String job) {
        MariaDB m;
        Date dob;
        if (firstName.isEmpty()) {
            return "First Name";
        }
        if (lastName.isEmpty()) {
            return "Last Name";
        }
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
            m = new MariaDB("UPDATE patient SET nom = ?, prenom = ?, email = ?, dob = ?, couple = ? WHERE id_patient = ?");
        } else {
            m = new MariaDB("UPDATE patient SET nom = ?, prenom = ?, email = ?, dob = ?, couple = ?, password = ? WHERE id_patient = ?");
        }
        
        Integer index = 1;
        m.setValue(index++, lastName);
        m.setValue(index++, firstName);
        m.setValue(index++, email);
        m.setValue(index++, new java.sql.Date(dob.getTime()), Types.DATE);
        m.setValue(index++, couple, Types.BOOLEAN);
        if (!password.isEmpty()) {
            m.setValue(index++, password);
        }
        m.setValue(index++, get_id());
        m.executeUpdate();

        // Now updating name
        m = new MariaDB("Call new_job_patient(?,?)");
        m.setValue(1,job);
        m.setValue(2,get_id());
        m.executeUpdate();
        return "";
    }
}
