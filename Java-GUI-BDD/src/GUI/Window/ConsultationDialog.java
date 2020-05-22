package GUI.Window;

import oo.Appointment;

import javax.swing.*;
import java.awt.*;

public class ConsultationDialog extends JDialog {
    private JTextArea gestureTextArea, keywordsTextArea, positionTextArea;
    private JButton confirmButton;

    private GridBagConstraints gbc;
    private Appointment appointment;

    public ConsultationDialog(Appointment a) {
        this.appointment = a;
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Consultation Editor");


        setElements();
        fillFields();
        setModal(true);
        setVisible(true);
    }

    private void setElements() {
        gbc = new GridBagConstraints();
        getContentPane().setLayout(new GridBagLayout());
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        setBackground(Color.LIGHT_GRAY);

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

        gbc.gridy += 2;
        confirmButton = new JButton("Confirm");
        this.add(confirmButton, gbc);

        lineWrapTextArea(true);
        setBounds(0, 0, 500, 500);
    }

    private void lineWrapTextArea(Boolean b) {
        gestureTextArea.setLineWrap(b);
        keywordsTextArea.setLineWrap(b);
        positionTextArea.setLineWrap(b);
    }

    private void fillFields() {
        gestureTextArea.setText(appointment.getGesture());
        keywordsTextArea.setText(appointment.getKeywords());
        positionTextArea.setText(appointment.getPosition());
    }
}
