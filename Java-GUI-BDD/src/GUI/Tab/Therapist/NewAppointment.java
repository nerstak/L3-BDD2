package GUI.Tab.Therapist;


import Project.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class NewAppointment extends GUI.TabBase implements ActionListener {
    // GUI
    private JTextField _patient1Text, _patient2Text, _patient3Text;
    private JComboBox _typeComboBox, _yearComboBox, _monthComboBox, _dayComboBox, _timeComboBox;
    private JButton _submitButton;
    private DefaultComboBoxModel _modelCombobox;
    private JPanel _datePanel;

    // Local variables
    private final LocalDateTime _actualDate = LocalDateTime.now();
    private String[] _typeString;
    private LocalDateTime _daySelected;

    public NewAppointment() {
        SetElements();
        DisplayElements(2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    protected void SetElements() {
        listComponents.add(new JLabel("First patient:"));
        _patient1Text = new JTextField(10);
        listComponents.add(_patient1Text);

        listComponents.add(new JLabel("Second patient:"));
        _patient2Text = new JTextField(10);
        listComponents.add(_patient2Text);

        listComponents.add(new JLabel("Third patient:"));
        _patient3Text = new JTextField(10);
        listComponents.add(_patient3Text);

        /* // TODO : Base list of appointment on database
        // Selecting type of appointment;
        listComponents.add(new JLabel("Type of appointment"));
        _modelCombobox = new DefaultComboBoxModel(HERE); // Get list of type of appointment
        _typeComboBox = new JComboBox(_modelCombobox);
        listComponents.add(_typeComboBox);
         */

        // Selecting day and time
        setDatePanel();

        listComponents.add(new JLabel("Time"));
        _timeComboBox = new JComboBox(generateHours(8, 20));
        listComponents.add(_timeComboBox);
        listComponents.add(new JLabel());


        // Button
        _submitButton = new JButton("Add appointment");
        _submitButton.addActionListener(this);
        listComponents.add(_submitButton);
    }

    private void setDatePanel() {
        _datePanel = new JPanel() {{
            setBackground(Color.LIGHT_GRAY);
        }};
        listComponents.add(new JLabel("YYYY-MM-DD: "));
        _yearComboBox = new JComboBox(Utilities.listNbToString(_actualDate.getYear(), _actualDate.getYear() + 5));
        _datePanel.add(_yearComboBox);
        _monthComboBox = new JComboBox(Utilities.listNbToString(1, 12));
        _datePanel.add(_monthComboBox);
        _dayComboBox = new JComboBox(Utilities.listNbToString(1, 31));
        _datePanel.add(_dayComboBox);
        listComponents.add(_datePanel);
    }

    /**
     * Generate list of half hours from a rounded one
     *
     * @param b Int corresponding to starting round hour
     * @param e Ending round hour
     * @return List of string
     */
    private String[] generateHours(int b, int e) {
        String[] y = new String[(e - b) * 2];
        for (int i = 0; i < (e - b) * 2; i++) {
            String min = "00";
            if (i % 2 != 0) {
                min = "30";
            }
            y[i] = (b + i / 2) + "h" + min;
        }
        return y;
    }
}
