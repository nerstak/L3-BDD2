package GUI.Tab.Patient;

import Project.Utilities;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ListAppointments extends GUI.Common.tableAppointment implements DocumentListener {
    private JTextField _searchField;

    public ListAppointments() {
        SetElements();
        DisplayElements(1);
    }

    @Override
    protected void SetElements() {
        // Search field
        listComponents.add(new JLabel("Search by date"));
        _searchField = new JTextField();
        _searchField.getDocument().addDocumentListener(this);
        listComponents.add(_searchField);

        // Table
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
    public void Load() {
        loadData();
        loadTable();
    }



    /**
     * Update list of appointment according to query
     */
    private void search() {
        String query = _searchField.getText();
        if (query.isEmpty()) { // If query is empty, we refill the table
            searchedAppointments = allAppointments;
        } else {
            // Recovering values with stream
            // Not a full lambda so it is easier to understand it
            searchedAppointments = allAppointments.stream().filter(x -> {
                return Utilities.appointmentFormat.format(x.getAppointmentTime()).contains(query);
            }).collect(Collectors.toCollection(ArrayList::new));
        }

        loadTable();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        search();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        search();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        search();
    }
}
