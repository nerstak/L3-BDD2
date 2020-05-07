package GUI.Window;

import GUI.Tab.Patient.Edit;
import GUI.TabBase;

import java.awt.event.KeyEvent;

public class Patient extends GUI.PanelBase {
    // Declare Final TabBase here
    private final TabBase _editPanel;

    public Patient() {
        int i = 0;
        // Link every tab to every panel
        _editPanel = new Edit();
        tabbedPane.addTab("Edit account", _editPanel);
        tabbedPane.setMnemonicAt(i++, KeyEvent.VK_1);

        addLogOutButtonToTab();
    }

    public void Load() { // TODO : Put loading of data in tab in another public subfunction
        // Load each tab
        _editPanel.Load();
        setFrame("Patient Panel");
    }
}
