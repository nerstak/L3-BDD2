package Project;

import GUI.Window.Login;

public class Main {
    // Windows
    public static Login loginWindow;
    public static GUI.Window.Patient patientWindow;
    public static GUI.Window.Therapist therapistWindow;

    public static void main(String[] args) {
        createWindows();
    }

    /**
     * Load the login window (and restart the others)
     */
    public static void loadLogin() {
        disposeWindows();
        createWindows();
    }

    /**
     * Create windows such as they should at first launch
     */
    public static void createWindows() {
        loginWindow = new Login();
        patientWindow = new GUI.Window.Patient();
        therapistWindow = new GUI.Window.Therapist();
    }

    /**
     * Remove windows
     */
    private static void disposeWindows() {
        patientWindow.dispose();
        therapistWindow.dispose();
    }
}
