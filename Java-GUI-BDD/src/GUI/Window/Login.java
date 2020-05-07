package GUI.Window;

import Project.Database.MariaDB;
import Project.Main;
import oo.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that holds window information
 */
public class Login<T extends User> extends JFrame implements ActionListener {
    // GUI
    private JLabel loginLabel, passwordLabel;
    private JButton loginButton, quitButton;
    private JTextField mailField;
    private JPasswordField passwordField;

    /**
     * Set the characteristics and position of elements in the window
     */
    private void SetElements() {
        setTitle("Login Page");
        setSize(300, 200);
        setLocation(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.LIGHT_GRAY);

        loginLabel = new JLabel("Email");
        passwordLabel = new JLabel("Password");

        mailField = new JTextField();
        mailField.setColumns(10);
        passwordField = new JPasswordField();
        passwordField.setColumns(10);

        loginButton = new JButton("Login");
        quitButton = new JButton("Quit");
        this.getRootPane().setDefaultButton(loginButton);
    }

    public Login() {
        SetElements();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        loginButton.addActionListener(this);
        quitButton.addActionListener(this);


        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(loginLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(mailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(quitButton, gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        add(loginButton, gbc);

        setVisible(true);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            Boolean error = false;
            Integer id = User.verifyUserMail(mailField.getText()); // Getting id of patient corresponding to mail
            if(id != -1) {
                // Patient
                if(User.verifyUserCredentials("Patient", mailField.getText(), String.valueOf(passwordField.getPassword())) != -1)
                {
                    Main.user = new oo.Patient(id);
                    loadWindow(Main.user);
                }
                else {
                    error = true;
                }
            } else {
                // Therapist
                if(User.verifyUserCredentials("Therapist", mailField.getText(), String.valueOf(passwordField.getPassword())) != -1) {
                    Main.user = new oo.Therapist(-1);
                    loadWindow(Main.user);
                } else {
                    error = true;
                }
            }

            if(error) {
                JOptionPane.showMessageDialog(this, "Incorrect credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (e.getSource() == quitButton) {
            dispose();
        }
    }

    /**
     * Load the correct window that replaces the login window
     *
     * @param u User created
     */
    private void loadWindow(User u) {
        MariaDB.closeConnection();
        if (u.getType().equals("Patient")) {
            MariaDB.openConnection("patient","patientPassword");
            Main.patientWindow.Load();
        } else {
            MariaDB.openConnection("therapist","therapistPassword");
            Main.therapistWindow.Load();
        }
        setVisible(false);
        dispose();
    }
}
