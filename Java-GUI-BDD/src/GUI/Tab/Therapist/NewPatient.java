package GUI.Tab.Therapist;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewPatient extends GUI.Common.accountEdition implements ActionListener {
    // GUI
    private JButton _createButton;

    @Override
    protected void SetElements() {
        super.SetElements();

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

    }
}
