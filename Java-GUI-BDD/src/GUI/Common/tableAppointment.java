package GUI.Common;

import Project.JUtilities;
import Project.Main;
import Project.Utilities;
import oo.Appointment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class tableAppointment extends GUI.TabBase {
    // GUI
    protected JTable _table;
    protected DefaultTableModel _modelTable;


    protected ArrayList<Appointment> allAppointments;
    protected ArrayList<Appointment> searchedAppointments;

    ArrayList<String> columns = loadColumns();


    /**
     * Load data into a table
     */
    protected void loadTable() {
        ArrayList<String[]> values = new ArrayList<>();
        for (Appointment a : searchedAppointments) {
            values.add(new String[]{
                    Utilities.appointmentFormat.format(a.getAppointmentTime()),
                    Utilities.capitalizeFirstLetter(a.getStatus()),
                    Utilities.capitalizeFirstLetter(a.getType()),
                    a.getPrice() + "€",
                    a.getPayment(),
                    a.isPayed() ? "Payed" : "Unpayed"
            });
        }

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
     *
     * @return
     */
    private ArrayList<String> loadColumns() {
        ArrayList<String> c = new ArrayList<>() {{
            add("Date");
            add("Status");
            add("Type");
            add("Price");
            add("Payment");
            add("Payed");
        }};
        return c;
    }

}
