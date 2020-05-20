package GUI.Tab.Therapist;

import Project.Utilities;
import oo.Appointment;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ListAppointments extends GUI.Common.tableAppointment implements DocumentListener, ActionListener {
    private JTextField _searchFieldDate;
    private JTextField _searchFieldMail;
    private JComboBox _searchComboBoxStatus;

    private String[] _statusString = new String[]{"", "Planned", "Cancelled", "Past"};

    public ListAppointments() {
        SetElements();
        DisplayElements(1);
    }

    @Override
    protected void SetElements() {
        // Search field
        listComponents.add(new JLabel("Search by date"));
        _searchFieldDate = new JTextField(55);
        _searchFieldDate.getDocument().addDocumentListener(this);
        listComponents.add(_searchFieldDate);

        listComponents.add(new JLabel("Search by mail"));
        _searchFieldMail = new JTextField();
        _searchFieldMail.getDocument().addDocumentListener(this);
        listComponents.add(_searchFieldMail);

        listComponents.add(new JLabel("Status"));
        _searchComboBoxStatus = new JComboBox(_statusString);
        _searchComboBoxStatus.addActionListener(this);
        listComponents.add(_searchComboBoxStatus);

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
    protected void formatTable() {
        ArrayList<String[]> values = new ArrayList<>();
        for (Appointment a : searchedAppointments) {
            values.add(new String[]{
                    Utilities.appointmentFormat.format(a.getAppointmentTime()),
                    Utilities.capitalizeFirstLetter(a.getStatus()),
                    a.getEmail(),
                    Utilities.capitalizeFirstLetter(a.getType()),
                    a.getPrice() + "€",
                    a.getPayment(),
                    a.isPayed() ? "Payed" : "Unpayed"
            });
        }

        putValuesInTable(values);
    }

    @Override
    public void Load() {
        super.Load();
        loadData();
        formatTable();
    }

    private void search() {
        searchedAppointments = allAppointments;
        String dateQuery = _searchFieldDate.getText();
        String mailQuery = _searchFieldMail.getText();
        String statusQuery = _searchComboBoxStatus.getSelectedItem().toString();

        if (!dateQuery.isEmpty()) {
            searchedAppointments = searchedAppointments.stream().filter(x -> {
                return Utilities.appointmentFormat.format(x.getAppointmentTime()).contains(dateQuery);
            }).collect(Collectors.toCollection(ArrayList::new));
        }

        if (!mailQuery.isEmpty()) {
            searchedAppointments = searchedAppointments.stream().filter(x -> {
                return x.getEmail().contains(mailQuery);
            }).collect(Collectors.toCollection(ArrayList::new));
        }

        if (!statusQuery.isEmpty()) {
            searchedAppointments = searchedAppointments.stream().filter(x -> {
                return x.getStatus().contains(statusQuery);
            }).collect(Collectors.toCollection(ArrayList::new));
        }


        formatTable();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        search();
    }
}
