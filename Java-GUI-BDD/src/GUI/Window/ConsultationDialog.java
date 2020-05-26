package GUI.Window;

import Project.Utilities;
import oo.Appointment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class ConsultationDialog extends JDialog implements ActionListener {
    private JTextArea gestureTextArea, keywordsTextArea, positionTextArea;
    private JButton confirmButton;
    private JComboBox anxietyComboBox;

    private boolean allowEdit = true;
    private final Appointment appointment;

    public ConsultationDialog(Appointment a) {
        this.appointment = a;
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Consultation Editor");

        setElements();
        fillFields();
        setAvailability();
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

        // Elements
        gbc.gridy = 0;
        gbc.gridx = 0;
        this.add(new JLabel("Gesture: "), gbc);
        gestureTextArea = new JTextArea(5, 35);
        gbc.gridx = 1;
        this.add(gestureTextArea, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        this.add(new JLabel("Keywords: "), gbc);
        keywordsTextArea = new JTextArea(5, 35);
        gbc.gridx = 1;
        this.add(keywordsTextArea, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        this.add(new JLabel("Position: "), gbc);
        positionTextArea = new JTextArea(5, 35);
        gbc.gridx = 1;
        this.add(positionTextArea, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        this.add(new JLabel("Anxiety: "), gbc);
        anxietyComboBox = new JComboBox(Utilities.listNbToString(0, 10));
        gbc.gridx = 1;
        this.add(anxietyComboBox, gbc);

        gbc.gridy++;
        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(this);
        this.add(confirmButton, gbc);

        lineWrapTextArea(true);
        setBounds(0, 0, 500, 500);
    }

    /**
     * Set line Wrap for all textarea
     * @param b boolean
     */
    private void lineWrapTextArea(Boolean b) {
        gestureTextArea.setLineWrap(b);
        keywordsTextArea.setLineWrap(b);
        positionTextArea.setLineWrap(b);
    }

    /**
     * Set fields to their value
     */
    private void fillFields() {
        gestureTextArea.setText(appointment.getGesture());
        keywordsTextArea.setText(appointment.getKeywords());
        positionTextArea.setText(appointment.getPosition());

        anxietyComboBox.setSelectedIndex(appointment.getAnxiety());
    }

    /**
     * Set fields availability according to date or status
     */
    private void setAvailability() {
        if(appointment.getStatus().equals("Cancelled") || new Date().before(appointment.getAppointmentTime())) {
            allowEdit = false;
            gestureTextArea.setEnabled(false);
            keywordsTextArea.setEnabled(false);
            positionTextArea.setEnabled(false);
            anxietyComboBox.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {
            if(allowEdit) {
                // Update values
                appointment.setGesture(gestureTextArea.getText());
                appointment.setKeywords(keywordsTextArea.getText());
                appointment.setPosition(positionTextArea.getText());
                appointment.setAnxiety(Integer.parseInt((String) anxietyComboBox.getSelectedItem()));

                appointment.updateConsultation();
            }

            dispose();
        }
    }


}
