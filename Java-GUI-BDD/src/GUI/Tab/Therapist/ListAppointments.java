package GUI.Tab.Therapist;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class ListAppointments extends GUI.Common.tableAppointment implements DocumentListener {
    private JTextField _searchFieldDate;

    public ListAppointments() {
        SetElements();
        DisplayElements(1);
    }

    @Override
    protected void SetElements() {
        // Search field
        listComponents.add(new JLabel("Search by date"));
        _searchFieldDate = new JTextField();
        _searchFieldDate.getDocument().addDocumentListener(this);
        listComponents.add(_searchFieldDate);

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

    private void search() {

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
