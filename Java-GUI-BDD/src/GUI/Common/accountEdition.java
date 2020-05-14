package GUI.Common;

import GUI.TabBase;

import javax.swing.*;

/**
 * Class holding the GUI of the account edition
 */
public abstract class accountEdition extends TabBase {
    // GUI
    protected JTextField _firstNameField, _lastNameField, _emailField, _dobField, _jobField;
    protected JTextArea _addressField;
    protected JPasswordField _passwordField;
    protected JComboBox _relationComboBox;

    protected void SetElements() {
        // First Name
        listComponents.add(new JLabel("First name"));
        _firstNameField = new JTextField(15);
        listComponents.add(_firstNameField);

        // Last Name
        listComponents.add(new JLabel("Last name"));
        _lastNameField = new JTextField(15);
        listComponents.add(_lastNameField);

        // DoB
        listComponents.add(new JLabel("Date of birth (YYYY-MM-DD)"));
        _dobField = new JTextField(15);
        listComponents.add(_dobField);

        // Email
        listComponents.add(new JLabel("Email address"));
        _emailField = new JTextField(15);
        listComponents.add(_emailField);

        // Relation
        listComponents.add(new JLabel("Relation status"));
        _relationComboBox = new JComboBox(new String[]{"Single", "In relation"});
        _relationComboBox.setEditable(false);
        listComponents.add(_relationComboBox);

        // Job
        listComponents.add(new JLabel("Job"));
        _jobField = new JTextField(15);
        listComponents.add(_jobField);


        // Address
        listComponents.add(new JLabel("Address"));
        _addressField = new JTextArea(5,15);
        _addressField.setLineWrap(true);
        listComponents.add(new JScrollPane(_addressField));

        // Password
        listComponents.add(new JLabel("Password"));
        _passwordField = new JPasswordField(15);
        listComponents.add(_passwordField);
    }

    protected void emptyFields() {
        _firstNameField.setText(null);
        _lastNameField.setText(null);
        _dobField.setText(null);
        _emailField.setText(null);
        _relationComboBox.setSelectedIndex(1);
        _jobField.setText(null);
        _addressField.setText(null);
        _passwordField.setText(null);
    }
}
