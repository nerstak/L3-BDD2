package oo;

import GUI.Tab.Therapist.SeePatient;
import Project.Main;
import Project.MariaDB;
import Project.Utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Therapist extends User
{

    public Therapist(Integer _id)
    {
        super(_id);
    }

    // TODO: 03/05/2020 : le faire en sql, ça sera mieux je pense 
    private int CalculateDate(String dob)
    {
        int result = 0;
        
        if(!dob.isEmpty())
        {
            // instantiate the date at NOW()
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd 'at' HH:mm:ss");
            String strDate = dateFormat.format(date);

            // age = actual time - dob
            result = Integer.parseInt(strDate.substring(0, 4)) - Integer.parseInt(dob.substring(0, 4));

            return result;
        }
        
        return result;
    }

    public String addPatient(String firstName, String lastName, String email, String dobString, Boolean couple, String password, String job, String mean) {
        MariaDB m;
        Date dob;

        // Error to return
        if (firstName.isEmpty()) {
            return "First Name";
        }
        if (lastName.isEmpty()) {
            return "Last Name";
        }
        if (!Utilities.isValidMail(email)) {
            return "Mail Address";
        }
        if(job.isEmpty())
        {
            return "Job";
        }
        try {
            dob = new SimpleDateFormat("yyyy-MM-dd").parse(dobString);
        } catch (Exception e) {
            return "date of birth";
        }

        // Insert the new Patient
        m = new MariaDB("INSERT INTO patient (nom, prenom, email, password, dob, couple, categorie, moyen) VALUES(?,?,?,?,?,?,?,?)");
        m.setValue(1, lastName);
        m.setValue(2, firstName);
        m.setValue(3, email);
        m.setValue(4, password);
        m.setValue(5, dob);
        m.setValue(6, couple);

        int age = CalculateDate(dobString);
        if(age < 11)
            m.setValue(7, "enfant");
        else if(age >= 18)
            m.setValue(7, "adulte");
        else
            m.setValue(7, "adolescent");

        if(mean.isEmpty())
            m.setValue(8, "NULL");
        else
            m.setValue(8, mean);

        m.executeQuery();

        return "";
    }

    public static void refresh()
    {
        Main.disposeAllWindows();
        Main.createWindowsTherapist();
        Main.therapistWindow.Load();
    }

}
