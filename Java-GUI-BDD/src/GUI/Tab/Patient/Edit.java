package GUI.Tab.Patient;

import Project.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that holds Edit User information
 */
public class Edit extends GUI.Common.accountEdition implements ActionListener {
    // GUI
    private JButton _updateButton;

    /**
     * Set the characteristics and position of elements in the window
     */
    @Override
    protected void SetElements() {
        super.SetElements(); // That's not extremely clean, but hey, it works

        // Button
        listComponents.add(new JLabel());
        _updateButton = new JButton("Update");
        _updateButton.addActionListener(this);
        listComponents.add(_updateButton);

        fieldsAvailability(false);
    }

    public Edit() {
        SetElements();

        DisplayElements(2);
    }


    public void Load() {
        oo.Patient u = (oo.Patient) Main.user;
        _firstNameField.setText(u.get_firstName());
        _lastNameField.setText(u.get_lastName());
        _emailField.setText(u.get_mail());
        _dobField.setText(u.get_dob().toString());
        _relationComboBox.setSelectedIndex(u.get_relation() ? 1 : 0);
        _jobField.setText(u.get_job());
        _addressField.setText(u.get_address());
    }

    /**
     * Change field availability
     * @param b Boolean
     */
    private void fieldsAvailability(Boolean b) {
        _lastNameField.setEditable(b);
        _firstNameField.setEditable(b);
        _dobField.setEditable(b);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == _updateButton) {
            // Update values
            String result = ((oo.Patient) Main.user).updateFields(
                    _firstNameField.getText(),
                    _lastNameField.getText(),
                    _emailField.getText(),
                    _dobField.getText(),
                    _relationComboBox.getSelectedIndex() == 1,
                    String.valueOf(_passwordField.getPassword()),
                    _jobField.getText(),
                    _addressField.getText()
            );
            if (!result.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Incorrect " + result, "Error", JOptionPane.ERROR_MESSAGE);
                if (result.equals("database error")) {
                    Load();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Information updated", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
