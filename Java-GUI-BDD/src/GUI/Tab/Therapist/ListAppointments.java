package GUI.Tab.Therapist;

import GUI.Window.ConsultationDialog;
import Project.Utilities;
import oo.Appointment;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ListAppointments extends GUI.Common.tableAppointment implements DocumentListener, ActionListener, ListSelectionListener {
    private JTextField _searchFieldDate, _searchFieldMail;
    private JComboBox _searchComboBoxStatus;
    private JButton _payedButton, _cancelButton, _doneButton;

    private final String[] _statusString = new String[]{"", "Planned", "Cancelled", "Past"};
    private int rowAppointment = -1;
    private Appointment selectedAppointment = null;
    private boolean appointmentChanged = false;

    public ListAppointments() {
        SetElements();
        DisplayElements(1);
    }

    @Override
    protected void SetElements() {
        // Search fields
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
        _table.getSelectionModel().addListSelectionListener(this);
        _table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    handleDialog();
                }
            }
        });
        _table.getTableHeader().setReorderingAllowed(false);

        JScrollPane _tableScrollPane = new JScrollPane(_table);
        _tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        listComponents.add(_tableScrollPane);

        // Cancel
        _cancelButton = new JButton("Cancel");
        _cancelButton.addActionListener(this);
        listComponents.add(_cancelButton);

        // Done button
        _doneButton = new JButton("Mark as done");
        _doneButton.addActionListener(this);
        listComponents.add(_doneButton);

        // Payed button
        _payedButton = new JButton("Mark as payed");
        _payedButton.addActionListener(this);
        listComponents.add(_payedButton);

        setFieldsAvailability(false);
    }

    @Override
    protected void formatTable() {
        ArrayList<String[]> values = new ArrayList<>();
        for (Appointment a : searchedAppointments) {
            values.add(new String[]{
                    String.valueOf(a.getIdAppointment()),
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

    /**
     * Function to search appointment
     */
    private void search() {
        searchedAppointments = allAppointments;
        // Getting values in search fields
        String dateQuery = _searchFieldDate.getText();
        String mailQuery = _searchFieldMail.getText();
        String statusQuery = _searchComboBoxStatus.getSelectedItem().toString();

        // Date filtering
        if (!dateQuery.isEmpty()) {
            searchedAppointments = searchedAppointments.stream().filter(x -> {
                return Utilities.appointmentFormat.format(x.getAppointmentTime()).contains(dateQuery);
            }).collect(Collectors.toCollection(ArrayList::new));
        }

        // Mail filtering
        if (!mailQuery.isEmpty()) {
            searchedAppointments = searchedAppointments.stream().filter(x -> {
                return x.getEmail().contains(mailQuery);
            }).collect(Collectors.toCollection(ArrayList::new));
        }

        // Status filtering
        if (!statusQuery.isEmpty()) {
            searchedAppointments = searchedAppointments.stream().filter(x -> {
                return x.getStatus().contains(statusQuery);
            }).collect(Collectors.toCollection(ArrayList::new));
        }

        formatTable();
    }

    /**
     * Display fields according to the status of the appointment
     * @param a Appointment
     */
    private void displayFields(Appointment a) {
        setFieldsAvailability(false);
        if (!a.isPayed() && a.getStatus().equals("Past") && !a.isPayed()) {
            _payedButton.setEnabled(true);
        }

        if (a.getStatus().equals("Planned")) {
            _cancelButton.setEnabled(true);
            _doneButton.setEnabled(true);
        }
    }

    /**
     * Change fields availability
     * @param b Boolean of availability
     */
    private void setFieldsAvailability(boolean b) {
        _payedButton.setEnabled(b);
        _cancelButton.setEnabled(b);
        _doneButton.setEnabled(b);
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
        if (e.getSource() == _searchComboBoxStatus) {
            // Combobox of search
            search();
        } else if (e.getSource() == _cancelButton) { // This and below: button to change value of appointment
            if (selectedAppointment != null) {
                appointmentChanged = true;
                selectedAppointment.setStatus("Cancelled");
            }
        } else if (e.getSource() == _doneButton) {
            if (selectedAppointment != null) {
                appointmentChanged = true;
                selectedAppointment.setStatus("Past");
            }
        } else if (e.getSource() == _payedButton) {
            selectedAppointment.setPayment(JOptionPane.showInputDialog(this,
                    "Mean of payment", "Mean of payment", JOptionPane.PLAIN_MESSAGE));
            if (!selectedAppointment.getPayment().isBlank()) {
                selectedAppointment.setPayed(true);
                appointmentChanged = true;
            }
        }
        updateAppointment();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!_table.getSelectionModel().isSelectionEmpty()) {
            // Storing selected appointment and its row in memory
            rowAppointment = _table.getSelectionModel().getMinSelectionIndex();
            int idAppointment = Integer.parseInt(String.valueOf(_table.getValueAt(rowAppointment, 0)));
            selectedAppointment = searchedAppointments.stream().filter(c -> c.getIdAppointment() == idAppointment).findAny().orElse(null);

            assert selectedAppointment != null;
            displayFields(selectedAppointment);
        } else {
            selectedAppointment = null;
            setFieldsAvailability(false);
        }
    }

    /**
     * Update an appointment
     */
    private void updateAppointment() {
        if (appointmentChanged && selectedAppointment != null) {
            selectedAppointment.updateAppointment();
            appointmentChanged = false;

            // Refreshing table
            loadData();
            search();
            _table.setRowSelectionInterval(rowAppointment, rowAppointment);
        }
    }

    private void handleDialog() {
        new ConsultationDialog(selectedAppointment);
    }
}
