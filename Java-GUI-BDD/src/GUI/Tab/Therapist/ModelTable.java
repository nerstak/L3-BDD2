package GUI.Tab.Therapist;

import Project.MariaDB;

import javax.swing.table.AbstractTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelTable extends AbstractTableModel {


    private static String[] _columnPatient = {"Prénom", "Nom", "Email"};


    public static String[] getColumnPatient(){
        return _columnPatient;
    }

    public static String[][] getPatients(){
        String[][] patients = new String[20][3];
        patients[0][0] = "Prénom";
        patients[0][1] = "Nom";
        patients[0][2] = "Email";

        try
        {
            int i = 1;

            MariaDB m = new MariaDB("SELECT nom, prenom, email from patient");
            ResultSet rs = m.executeQuery();

            while(rs.next())
            {
                patients[i][0] = rs.getString(1);
                patients[i][1] = rs.getString(2);
                patients[i][2] = rs.getString(3);
                i++;
            }
        }
        catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }

        return patients;
    }


    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return _columnPatient.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
}
