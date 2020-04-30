package GUI.Tab.Therapist;


import oo.Patient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO: Empty form after successful submission
public class NewPatient extends GUI.Common.accountEdition implements ActionListener {
    // GUI
    private JButton _createButton;
    private JTextField _meanField;

    @Override
    protected void SetElements() {
        super.SetElements();

        listComponents.add(new JLabel("How did it hear of us?"));
        _meanField = new JTextField(10);
        listComponents.add(_meanField);

        // Button
        listComponents.add(new JLabel());
        _createButton = new JButton("Create");
        _createButton.addActionListener(this);
        listComponents.add(_createButton);
    }

    public NewPatient() {
        SetElements();

        DisplayElements(2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == _createButton) {
            String result = Patient.createPatient(_firstNameField.getText(),
                    _lastNameField.getText(),
                    _emailField.getText(),
                    _dobField.getText(),
                    _relationComboBox.getSelectedIndex() == 1,
                    _jobField.getText(),
                    String.valueOf(_passwordField.getPassword()),
                    _meanField.getText(),
                    _addressField.getText()
            );
            if(!result.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Incorrect " + result, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Patient created", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
