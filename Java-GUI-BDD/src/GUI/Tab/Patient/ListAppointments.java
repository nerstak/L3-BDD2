package GUI.Tab.Patient;

import Project.Main;
import oo.Appointment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ListAppointments extends GUI.TabBase implements ActionListener {
    private ArrayList<Appointment> listAppointments;

    public ListAppointments() {
        SetElements();
        DisplayElements(2);
    }

    @Override
    protected void SetElements() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void Load() {
        listAppointments = Appointment.recoverAppointments(Main.user.get_id());
    }

}
