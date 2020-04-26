package GUI.Common;

import GUI.TabBase;

import javax.swing.*;

/**
 * Class holding the GUI of the account edition
 */
public abstract class accountEdition extends TabBase {
    // GUI
    protected JTextField _firstNameField, _lastNameField, _emailField, _dobField, _jobField;
    protected JPasswordField _passwordField;
    protected JComboBox _relationComboBox;

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
    }
}
