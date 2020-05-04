package GUI.Window;

import GUI.Tab.Patient.Edit;
import GUI.Tab.Patient.ListAppointments;
import GUI.TabBase;

import java.awt.event.KeyEvent;

public class Patient extends GUI.PanelBase {
    // Declare Final TabBase here
    private final TabBase _editPanel;
    private final TabBase _listAppointments;

    public Patient() {
        int i = 0;
        // Link every tab to every panel
        _editPanel = new Edit();
        tabbedPane.addTab("Edit account", _editPanel);
        tabbedPane.setMnemonicAt(i++, KeyEvent.VK_1);

        _listAppointments = new ListAppointments();
        tabbedPane.addTab("List of appointments", _listAppointments);
        tabbedPane.setMnemonicAt(i++, KeyEvent.VK_1);

        addLogOutButtonToTab();
    }

    public void Load() {
        // Load each tab
        _editPanel.Load();
        _listAppointments.Load();
        setFrame("Patient Panel");
    }
}
