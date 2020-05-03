package GUI.Tab.Therapist;


import Project.Main;
import GUI.Tab.Therapist.SeePatient;
import oo.Therapist;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewPatient extends GUI.TabBase implements ActionListener
{

    private JTextField _firstNameField, _lastNameField, _emailField, _dobField, _jobField, _meanField;
    private JPasswordField _passwordField;
    private JButton _addButton;
    private JComboBox _relationComboBox;



    protected void SetElements()
    {
        // First Name
        listComponents.add(new JLabel("First name"));
        _firstNameField = new JTextField(10);
        if(!_firstNameField.getText().isEmpty())
            _firstNameField.setText("");
        listComponents.add(_firstNameField);

        // Last Name
        listComponents.add(new JLabel("Last name"));
        _lastNameField = new JTextField(10);
        if(!_lastNameField.getText().isEmpty())
            _lastNameField.setText("");
        listComponents.add(_lastNameField);

        // DoB
        listComponents.add(new JLabel("Date of birth (YYYY-MM-DD)"));
        _dobField = new JTextField(10);
        if(!_dobField.getText().isEmpty())
            _dobField.setText("");
        listComponents.add(_dobField);

        // Email
        listComponents.add(new JLabel("Email address"));
        _emailField = new JTextField(10);
        if(!_emailField.getText().isEmpty())
            _emailField.setText("");
        listComponents.add(_emailField);

        // Relation
        listComponents.add(new JLabel("Relation status"));
        _relationComboBox = new JComboBox(new String[]{"Single", "In relation"});
        _relationComboBox.setEditable(false);
        listComponents.add(_relationComboBox);

        // Job
        listComponents.add(new JLabel("Job"));
        _jobField = new JTextField(10);
        if(!_jobField.getText().isEmpty())
            _jobField.setText("");
        listComponents.add(_jobField);

        /*
        //TODO: Rework address field
        // Address
        listComponents.add(new JLabel("Address"));
        _addressField = new JTextField(10);
        listComponents.add(_addressField);
         */

        // Mean
        listComponents.add(new JLabel("Mean"));
        _meanField = new JTextField(10);
        if(!_meanField.getText().isEmpty())
            _meanField.setText("");
        listComponents.add(_meanField);

        // Password
        listComponents.add(new JLabel("Password"));
        _passwordField = new JPasswordField(10);
        if(String.valueOf(_passwordField.getPassword()) != "")
            _passwordField.setText("");
        listComponents.add(_passwordField);

        // Button
        listComponents.add(new JLabel());
        _addButton = new JButton("Add");
        _addButton.addActionListener(this);
        listComponents.add(_addButton);
    }

    public NewPatient() {
        SetElements();

        DisplayElements(2);
    }

    public void Load(){
        oo.Therapist u = (oo.Therapist) Main.user;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == _addButton)
        {
            String result = ((oo.Therapist) Main.user).addPatient(
                    _firstNameField.getText(),
                    _lastNameField.getText(),
                    _emailField.getText(),
                    _dobField.getText(),
                    _relationComboBox.getSelectedIndex() == 1,
                    String.valueOf(_passwordField.getPassword()),
                    _jobField.getText(),
                    _meanField.getText()
            );
            if (!result.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Incorrect " + result, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Patient added", "Success", JOptionPane.INFORMATION_MESSAGE);
                Therapist.refresh();
            }
        }
    }
}
