package oo;

import Project.Database.Prepared;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Job {
    private final Date startDate;
    private final Date endDate;
    private final String name;

    public Job(Date startDate, Date endDate, String name) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getName() {
        return name;
    }

    /**
     * Recover job history of a patient
     * @param idPatient Patient to recover history for
     * @return List of job (empty if unknown user)
     */
    public static ArrayList<Job> recoverHistoryJob(int idPatient) {
        ArrayList<Job> history = new ArrayList<>();
        if(idPatient >= 0) {
            Prepared p = new Prepared("SELECT id_patient, nom, date_debut, date_fin " +
                    "FROM v_historique_job_complet " +
                    "WHERE id_patient = ? " +
                    "ORDER BY date_debut DESC ");

            p.setValue(1, idPatient);

            try {
                ResultSet r = p.executeQuery();
                while(r.next()) {
                    Job j = new Job(r.getDate(3),
                            r.getDate(4),
                            r.getString(2)
                    );
                    history.add(j);
                }
            } catch (SQLException e) {
                System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            }
        }

        return history;
    }
}
