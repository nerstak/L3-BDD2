package GUI.Common;

import Project.JUtilities;
import Project.Main;
import oo.Appointment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

/**
 * Table of appointment
 */
public class tableAppointment extends GUI.TabBase {
    // GUI
    protected JTable _table;
    protected DefaultTableModel _modelTable;


    protected ArrayList<Appointment> allAppointments;
    protected ArrayList<Appointment> searchedAppointments;

    protected ArrayList<String> columns;

    @Override
    public void Load() {
        loadColumns();
    }


    /**
     * Load data into a table after formatting
     * Overridable
     */
    protected void formatTable() {
    }

    /**
     * Put loaded data in table
     * @param values Loaded and formatted data
     */
    protected void putValuesInTable(ArrayList<String[]> values) {
        _modelTable = new DefaultTableModel(values.toArray(new Object[][]{}), columns.toArray());
        _table.setModel(_modelTable);
        JUtilities.resizeColumnWidth(_table);
        JUtilities.setCellsAlignment(_table, SwingConstants.CENTER);
    }

    /**
     * Load data
     */
    protected void loadData() {
        allAppointments = Appointment.recoverAppointments(Main.user.get_id());
        searchedAppointments = allAppointments;
    }

    /**
     * Set columns
     */
    private void loadColumns() {
        columns = new ArrayList<>() {{
            add("Date");
            add("Status");
            add("Type");
            add("Price");
            add("Payment");
            add("Payed");
        }};

        if (Main.user.getType().equals("Therapist")) {
            columns.add(2, "Mail");
            columns.add(0, "ID");
        }
    }
}
