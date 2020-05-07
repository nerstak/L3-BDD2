package GUI.Window;

import GUI.Tab.Patient.Edit;
import GUI.Tab.Therapist.NewPatient;
import GUI.Tab.Therapist.SeePatient;
import GUI.TabBase;
import Project.Main;
import oo.User;

import java.awt.event.KeyEvent;
import java.sql.SQLException;

public class Therapist extends GUI.PanelBase {
    // Declare Final TabBase here
    private final TabBase _seePatientsPanel;
    private final TabBase _newPatient;

    public Therapist() {
        int i = 0;

        _seePatientsPanel = new SeePatient();
        tabbedPane.addTab("All Patients", _seePatientsPanel);
        tabbedPane.setMnemonicAt(i++, KeyEvent.VK_1);

        _newPatient = new NewPatient();
        tabbedPane.addTab("New Patient", _newPatient);
        tabbedPane.setMnemonicAt(i++, KeyEvent.VK_1);
        // Link every tab to every panel


        addLogOutButtonToTab();
    }

    public void Load() {
        // Load each tab
        _seePatientsPanel.Load();
        _newPatient.Load();
        setFrame("Therapist Panel");
    }

}
