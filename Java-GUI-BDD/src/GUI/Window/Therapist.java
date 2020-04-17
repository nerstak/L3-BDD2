package GUI.Window;

public class Therapist extends GUI.PanelBase {
    // Declare Final TabBase here

    public Therapist() {
        // Link every tab to every panel

        addLogOutButtonToTab();
    }

    public void Load() {
        // Load each tab
        setFrame("Therapist Panel");
    }
}
