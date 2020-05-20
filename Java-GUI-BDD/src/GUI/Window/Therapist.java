package GUI.Window;

import GUI.Tab.Therapist.ListAppointments;
import GUI.Tab.Therapist.NewAppointment;
import GUI.Tab.Therapist.NewPatient;
import GUI.Tab.Therapist.SeePatient;
import GUI.TabBase;

import java.awt.event.KeyEvent;

public class Therapist extends GUI.PanelBase {
    // Declare Final TabBase here
    private final TabBase _seePatientsPanel;
    private final TabBase _newPatientPanel;
    private final TabBase _newAppointmentPanel;
    private final TabBase _listAppointments;

    public Therapist()
    {
        int i = 0;
        // Link every tab to every panel
         _seePatientsPanel = new SeePatient();
        tabbedPane.addTab("All Patients", _seePatientsPanel);
        tabbedPane.setMnemonicAt(i++, KeyEvent.VK_1);
        
        _newPatientPanel = new NewPatient();
        tabbedPane.addTab("Create account", _newPatientPanel);
        tabbedPane.setMnemonicAt(i++, KeyEvent.VK_1);

        _newAppointmentPanel = new NewAppointment();
        tabbedPane.addTab("Create appointment", _newAppointmentPanel);
        tabbedPane.setMnemonicAt(i++, KeyEvent.VK_1);

        _listAppointments = new ListAppointments();
        tabbedPane.addTab("List of appointments", _listAppointments);
        tabbedPane.setMnemonicAt(i++, KeyEvent.VK_1);

        addLogOutButtonToTab();
    }

    public void LoadTabs() {
        _seePatientsPanel.Load();
        _newAppointmentPanel.Load();
        _listAppointments.Load();
    }

    public void Load() {
        LoadTabs();
        setFrame("Therapist Panel");
    }


}