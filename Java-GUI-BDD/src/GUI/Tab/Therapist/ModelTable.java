package GUI.Tab.Therapist;

import Project.Database.Prepared;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelTable extends AbstractTableModel {
    private static final String[] _columnPatient = {"","First Name", "Last Name", "Email", "DoB", "Category", "Mean"};


    public static String[] getColumnPatient(){
        return _columnPatient;
    }

    private static int getSize() {
        int size = 0;

        try
        {
            Prepared p = new Prepared("SELECT id_patient FROM patient");
            ResultSet rs = p.executeQuery();
            while(rs.next())
                size++;

        }
        catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return size;
    }

    public static String[][] getPatients(){
        // Init the table to do not too much line
        // fill the table

        String[][] patients = new String[getSize()][_columnPatient.length];
        try
        {
            int i = 0;

            Prepared p = new Prepared("SELECT id_patient, nom, prenom, email, categorie, moyen, dob from patient");
            ResultSet rs = p.executeQuery();

            while(rs.next())
            {
                patients[i][0] = String.valueOf(rs.getInt(1));
                patients[i][1] = rs.getString(3);
                patients[i][2] = rs.getString(2);
                patients[i][3] = rs.getString(4);
                patients[i][4] = rs.getString(7);
                patients[i][5] = rs.getString(5);
                patients[i][6] = rs.getString(6);
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
