package GUI.Tab.Patient;

import oo.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that holds Edit User information
 */
public class Edit extends GUI.TabBase implements ActionListener {
    // GUI
    private JTextField _nameField, _idCardField, _emailField, _phoneNumberField, _addressField;
    private JPasswordField _passwordField;
    private JButton _updateButton;

    /**
     * Set the characteristics and position of elements in the window
     */
    @Override
    protected void SetElements() {
        // Name
        listComponents.add(new JLabel("Name"));
        _nameField = new JTextField(10);
        listComponents.add(_nameField);

        // ID Card
        listComponents.add(new JLabel("ID Card"));
        _idCardField = new JTextField(10);
        listComponents.add(_idCardField);

        // Email
        listComponents.add(new JLabel("Email address"));
        _emailField = new JTextField(10);
        listComponents.add(_emailField);

        // Address
        listComponents.add(new JLabel("Address"));
        _addressField = new JTextField(10);
        listComponents.add(_addressField);

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
