package GUI.Tab.Therapist;

import GUI.TabBase;
import Project.Main;
import GUI.Window.Therapist;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SeePatient<T extends Therapist> extends TabBase implements ListSelectionListener, DocumentListener
{
    private JTextField _search;
    //private JButton _add;

    private String[][] _patients;
    private JTable _table;
    private JScrollPane _scrollPane;

    private int _selectedRow;

    public SeePatient() {
        SetElements();
        DisplayElements(1);
    }


    public void SetElements()
    {
        // Search Button
        listComponents.add(new JLabel("Recherche par Prénom"));
        _search = new JTextField();
        _search.getDocument().addDocumentListener(this);
        listComponents.add(_search);

        // Table of all patients
        _patients = ModelTable.getPatients();

        LoadTable();
        listComponents.add(_scrollPane);
    }



    public void Load(){
        oo.Therapist u = (oo.Therapist) Main.user;
    }

    private void LoadTable()
    {
        _table = new JTable(_patients, ModelTable.getColumnPatient());
        _table.getSelectionModel().addListSelectionListener(this);
        _table.getTableHeader().setReorderingAllowed(false);
        _table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        _scrollPane = new JScrollPane(_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, _scrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }


    public void RefreshTable()
    {
        _patients = ModelTable.getPatients();
        LoadTable();
    }


    private void search() {
        /*
        if (_search.getText().isEmpty()) {
            _patients = ModelTable.getPatients();
        } else {

            _patients = ModelTable.getPatientsByName(_search.getText());
        }

        LoadTable();
*/
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
       /** if(!_table.getSelectionModel().isSelectionEmpty())
        {
            _selectedRow = _table.getSelectionModel().getMinSelectionIndex();
            Integer IDPatient = 0;

            try {
                MariaDB m = new MariaDB("SELECT id_patient FROM patient WHERE prenom = ? AND nom = ? AND email = ? AND dob = ? AND categorie = ? AND moyen = ?;");
                m.setValue(1, (String) _table.getValueAt(_selectedRow, 0));     // set the firstname
                m.setValue(2, (String) _table.getValueAt(_selectedRow, 1));     // set the lastname
                m.setValue(3, (String) _table.getValueAt(_selectedRow, 2));     // set the mail
                m.setValue(4, (String) _table.getValueAt(_selectedRow, 3));     // set the dob
                m.setValue(5, (String) _table.getValueAt(_selectedRow, 4));     // set the categorie
                m.setValue(6, (String) _table.getValueAt(_selectedRow, 5));     // set the moyen

                ResultSet rs = m.executeQuery();
                if(rs.next())
                {
                    IDPatient = rs.getInt(1);           // get the ID of the patient
                    Main.user = new oo.Patient(IDPatient);
                    loadWindow();
                }
                System.out.println(IDPatient);

            }
            catch (SQLException f) {
                System.err.format("\nSQL State: %s\n%s", f.getSQLState(), f.getMessage());
            }
        } **/
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

    private void loadWindow() {
        Main.therapistWindow.dispose();
        Main.therapistWindow.Load();
    }
}
