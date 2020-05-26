package GUI.Window;

import Project.JUtilities;
import Project.Utilities;
import oo.Job;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PatientDialog extends JDialog {
    protected JTable _table;
    protected DefaultTableModel _modelTable;

    protected final ArrayList<Job> history;
    protected ArrayList<String> columns;

    public PatientDialog(int patientID) {
        history = Job.recoverHistoryJob(patientID);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Job History");

        setElements();
        formatTable();
        setModal(true);
        setVisible(true);
    }

    /**
     * Place elements in interface
     */
    private void setElements() {
        // Characteristics
        GridBagConstraints gbc = new GridBagConstraints();
        getContentPane().setLayout(new GridBagLayout());
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().setBackground(Color.LIGHT_GRAY);

        loadColumns();

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
        // Elements
        gbc.gridy = 0;
        gbc.gridx = 0;
        this.add(_tableScrollPane,gbc);
        setBounds(0, 0, 500, 500);
    }

    /**
     * Load data into a table after formatting
     */
    private void putValuesInTable(ArrayList<String[]> values) {
        _modelTable = new DefaultTableModel(values.toArray(new Object[][]{}), columns.toArray());
        _table.setModel(_modelTable);
        JUtilities.resizeColumnWidth(_table);
        JUtilities.setCellsAlignment(_table, SwingConstants.CENTER);
    }

    /**
     * Load data into a table after formatting
     */
    private void formatTable() {
        ArrayList<String[]> values = new ArrayList<>();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        for (Job j : history) {
            String endDate = "";
            if(j.getEndDate() != null) {
                endDate = formatDate.format(j.getEndDate());
            }

            values.add(new String[]{
                    Utilities.capitalizeFirstLetter(j.getName()),
                    formatDate.format(j.getStartDate()),
                    endDate
            });
        }

        putValuesInTable(values);
    }

    /**
     * Set columns
     */
    private void loadColumns() {
        columns = new ArrayList<>() {{
            add("Job Name");
            add("Starting date");
            add("Ending date");
        }};
    }


}
