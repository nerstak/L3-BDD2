package GUI.Tab.Therapist;

import GUI.TabBase;
import GUI.Window.PatientDialog;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Tab of list of all patients
 */
public class SeePatient extends TabBase {
    private String[][] _patients;
    private JTable _table;
    private JScrollPane _scrollPane;

    public SeePatient() {
        SetElements();
        DisplayElements(1);
    }


    public void SetElements() {
        // Table of all patients
        RefreshTable();
        listComponents.add(_scrollPane);
    }



    public void Load(){}

    private void LoadTable() {
        _table = new JTable(_patients, ModelTable.getColumnPatient()){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        _table.getTableHeader().setReorderingAllowed(false);
        _table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        _table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                handleDialog();
            }
            }
        });

        _scrollPane = new JScrollPane(_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, _scrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }


    public void RefreshTable() {
        _patients = ModelTable.getPatients();
        LoadTable();
    }


    /**
     * Open history dialog
     */
    private void handleDialog() {
        int rowPatient = _table.getSelectionModel().getMinSelectionIndex();
        int idPatient = Integer.parseInt(String.valueOf(_table.getValueAt(rowPatient, 0)));
        new PatientDialog(idPatient);
    }
}
