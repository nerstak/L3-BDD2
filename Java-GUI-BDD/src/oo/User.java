package oo;

import Project.Database.Prepared;
import Project.Utilities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private String _type;
    private Integer _id;

    public User() {
        _type = "Patient";
    }

    public User(Integer _id) {
        this._id = _id;
        _type = "Patient";

        if (_id < 0) {
            _type = "Therapist";
        }
    }

    public String getType() {
        return _type;
    }

    public Integer get_id() {
        return _id;
    }


    public static Integer verifyUserMail(String mail)
    {
        Integer value = -1;

        if (Utilities.isValidMail(mail)) {
            Prepared m = new Prepared("SELECT id_patient FROM patient WHERE email=?");
            m.setValue(1, mail);
            try {
                ResultSet x = m.executeQuery();
                if (x.next()) {
                    value = x.getInt(1);
                }
            } catch (SQLException e) {
                System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            }

        }

        return value;
    }


    /**
     * Verify credentials
     *
     * @param mail     Mail to check
     * @param password Password to check
     * @return Id of patient, -1 if false or not found
     */

    public static Integer verifyUserCredentials(String mail, String password) {
        // TODO: Hash passwords
        Integer value = -1;
        if (Utilities.isValidMail(mail)) {
            Prepared m = new Prepared("SELECT id_patient, password FROM patient WHERE email=?");
            m.setValue(1, mail);
            try {
                ResultSet x = m.executeQuery();
                if (x.next()) {
                    value = x.getInt(1);
                    String pass = x.getString(2);
                    if (!pass.equals(password)) {
                        value = -1;
                    }
                }
            } catch (SQLException e) {
                System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            }

        }
        return value;
    }
}
