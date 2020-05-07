package Project;

import GUI.Window.Login;
import Project.Database.MariaDB;
import oo.User;

public class Main {
    // Windows
    public static Login loginWindow;
    public static GUI.Window.Patient patientWindow;
    public static GUI.Window.Therapist therapistWindow;

    // Variables
    public static User user = null;

    public static void main(String[] args) {
        if (MariaDB.openConnection("login","loginPassword")) {
            createWindows();
        }
    }

    /**
     * Load the login window (and restart the others)
     */
    public static void loadLogin() {
        disposeWindows();
        MariaDB.closeConnection();
        MariaDB.openConnection("login","loginPassword");
        Main.user = null;
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
