package GUI.Tab.Patient;

import Project.Main;
import oo.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that holds Edit User information
 */
public class Edit extends GUI.TabBase implements ActionListener {
    // GUI
    private JTextField _firstNameField, _lastNameField, _emailField, _dobField, _jobField;
    private JPasswordField _passwordField;
    private JButton _updateButton;
    private JComboBox _relationComboBox;

    /**
     * Set the characteristics and position of elements in the window
     */
    @Override
    protected void SetElements() {
        // First Name
        listComponents.add(new JLabel("First name"));
        _firstNameField = new JTextField(10);
        listComponents.add(_firstNameField);

        // Last Name
        listComponents.add(new JLabel("Last name"));
        _lastNameField = new JTextField(10);
        listComponents.add(_lastNameField);

        // DoB
        listComponents.add(new JLabel("Date of birth (YYYY-MM-DD)"));
        _dobField = new JTextField(10);
        listComponents.add(_dobField);

        // Email
        listComponents.add(new JLabel("Email address"));
        _emailField = new JTextField(10);
        listComponents.add(_emailField);

        // Relation
        listComponents.add(new JLabel("Relation status"));
        _relationComboBox = new JComboBox(new String[]{"Single", "In relation"});
        _relationComboBox.setEditable(false);
        listComponents.add(_relationComboBox);

        // Job
        listComponents.add(new JLabel("Job"));
        _jobField = new JTextField(10);
        listComponents.add(_jobField);

        /*
        //TODO: Rework address field
        // Address
        listComponents.add(new JLabel("Address"));
        _addressField = new JTextField(10);
        listComponents.add(_addressField);
         */

        // Password
        listComponents.add(new JLabel("Password"));
        _passwordField = new JPasswordField(10);
        listComponents.add(_passwordField);

        // Button
        listComponents.add(new JLabel());
        _updateButton = new JButton("Update");
        _updateButton.addActionListener(this);
        listComponents.add(_updateButton);
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == _updateButton) {

        }
    }

    /**
     * Update values of user
     */
    private <T extends User> void updateValues() {
        /*T u = (T) Main.user;

        u.setName(_nameField.getText());
        u.setIdCard(_idCardField.getText());
        u.setEmail(_emailField.getText().toLowerCase().toLowerCase());
        u.setPhoneNumber(_phoneNumberField.getText());
        u.setAddress(_addressField.getText());

        // Is the password going to change
        String password = String.valueOf(_passwordField.getPassword());
        if(!password.isEmpty()) {
            u.setPassword(password);
        }
        */

    }
}
