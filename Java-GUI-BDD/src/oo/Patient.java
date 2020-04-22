package oo;

import Project.MariaDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Patient extends User {
    private String _firstName;
    private String _lastName;
    private String _mail;
    private Date _dob;
    private Boolean _relation;

    public Patient(Integer _id) {
        super(_id);

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
}
