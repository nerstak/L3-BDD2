package GUI.Window;

import GUI.Tab.Therapist.NewAppointment;
import GUI.Tab.Therapist.NewPatient;
import GUI.TabBase;

import java.awt.event.KeyEvent;

public class Therapist extends GUI.PanelBase {
    // Declare Final TabBase here
    private final TabBase _newPatientPanel;
    private final TabBase _newAppointmentPanel;

    public Therapist()
    {
        int i = 0;
        // Link every tab to every panel
        _newPatientPanel = new NewPatient();
        tabbedPane.addTab("Create account", _newPatientPanel);
        tabbedPane.setMnemonicAt(i++, KeyEvent.VK_1);

        _newAppointmentPanel = new NewAppointment();
        tabbedPane.addTab("Create appointment", _newAppointmentPanel);
        tabbedPane.setMnemonicAt(i++, KeyEvent.VK_1);

        addLogOutButtonToTab();
    }

    public void Load() {
        // Load each tab
        _newAppointmentPanel.Load();
        setFrame("Therapist Panel");
    }
}
