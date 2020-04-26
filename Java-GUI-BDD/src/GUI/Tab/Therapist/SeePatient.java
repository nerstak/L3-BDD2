package GUI.Tab.Therapist;

import GUI.TabBase;
import Project.Main;
import Project.MariaDB;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SeePatient extends TabBase
{
    private String[][] _patients;
    private JTable _table;
    private JScrollPane _scrollPane;

    public SeePatient() {

        _patients = ModelTable.getPatients();
        _table = new JTable(_patients, ModelTable.getColumnPatient());
        _scrollPane = new JScrollPane(_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, _scrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }


    public void Load(){
        oo.Therapist u = (oo.Therapist) Main.user;

        /*
        _firstNameField.setText(u.get_firstName());
        _lastNameField.setText(u.get_lastName());
        _emailField.setText(u.get_mail());*/
    }

}
