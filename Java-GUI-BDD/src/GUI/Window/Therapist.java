package GUI.Window;

import GUI.Tab.Patient.Edit;
import GUI.TabBase;

public class Therapist extends GUI.PanelBase {
    // Declare Final TabBase here
    private final TabBase _editPanel;

    public Therapist()
    {
        _editPanel = new Edit();
        // Link every tab to every panel

        addLogOutButtonToTab();
    }

    public void Load() {
        // Load each tab
        _editPanel.Load();
        setFrame("Therapist Panel");
    }
}
