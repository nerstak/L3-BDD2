package Project;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Class that holds different functions that are too general too be implemented in a specific class
 */
public class Utilities {
    public static final DateFormat appointmentFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * Set an array of string from a number to another
     * @param b Integer Starting number
     * @param e Integer Ending number
     * @return Array of string
     */
    public static String[] listNbToString(Integer b, Integer e) {
        String[] y = new String[e - b + 1];
        for (int i = 0; i < e - b + 1; i++) {
            y[i] = String.valueOf(i + b);
        }
        return y;
    }

    /**
     * Verify if a string is an email address
     * @param email String to verify
     * @return boolean of validity
     */
    public static boolean isValidMail(String email) {
        // Obviously not my expression, but I needed a strong regex to be sure the verification would be reliable
        String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        return email.matches(regex);
    }

    public static String capitalizeFirstLetter(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
