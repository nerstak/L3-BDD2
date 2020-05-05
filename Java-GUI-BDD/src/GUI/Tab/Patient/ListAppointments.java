package GUI.Tab.Patient;

import Project.JUtilities;
import Project.Main;
import Project.Utilities;
import oo.Appointment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListAppointments extends GUI.TabBase implements ActionListener {
    // GUI
    private JTable _table;
    private DefaultTableModel _modelTable;

    private ArrayList<Appointment> listAppointments;

    public ListAppointments() {
        SetElements();
        DisplayElements(1);
    }

    @Override
    protected void SetElements() {
        _modelTable = new DefaultTableModel();
        // Non editable table
        _table = new JTable(_modelTable) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        _table.getTableHeader().setReorderingAllowed(false);

        JScrollPane _tableScrollPane = new JScrollPane(_table);
        _tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        listComponents.add(_tableScrollPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void Load() {
        _modelTable = new DefaultTableModel(loadData().toArray(new Object[][]{}), loadColumns().toArray());
        _table.setModel(_modelTable);
        JUtilities.resizeColumnWidth(_table);
        JUtilities.setCellsAlignment(_table,SwingConstants.CENTER);

    }

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

    private ArrayList<String[]> loadData() {
        ArrayList<String[]> values = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");

        listAppointments = Appointment.recoverAppointments(Main.user.get_id());
        for (Appointment a: listAppointments) {
            values.add(new String[] {
                    dateFormat.format(a.getAppointmentTime()),
                    Utilities.capitalizeFirstLetter(a.getStatus()),
                    Utilities.capitalizeFirstLetter(a.getType()),
                    a.getPrice() + "€",
                    a.getPayment(),
                    a.isPayed() ? "Payed" : "Unpayed"
            });
        }
        return values;
    }
}
