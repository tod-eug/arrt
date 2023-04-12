package dto;

import java.util.Date;

public class JobLogRaw {

    private boolean completed;
    private Date jobDate;
    private String startIntervalHours;
    private String startIntervalMinutes;
    private String endIntervalHours;
    private String endIntervalMinutes;
    private double hoursAmount;

    public JobLogRaw() {
        this.jobDate = null;
        this.startIntervalHours = "";
        this.startIntervalMinutes = "";
        this.endIntervalHours = "";
        this.endIntervalMinutes = "";
        hoursAmount = 0;
        this.completed = false;
    }

    public void setStartIntervalHours(String startIntervalHours) {
        this.startIntervalHours = startIntervalHours;
    }

    public void setEndIntervalHours(String endIntervalHours) {
        this.endIntervalHours = endIntervalHours;
    }

    public void setJobDate(Date jobDate) {
        this.jobDate = jobDate;
    }

    public Date getJobDate() {
        return jobDate;
    }

    public String getStartIntervalHours() {
        return startIntervalHours;
    }

    public String getEndIntervalHours() {
        return endIntervalHours;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getStartIntervalMinutes() {
        return startIntervalMinutes;
    }

    public void setStartIntervalMinutes(String startIntervalMinutes) {
        this.startIntervalMinutes = startIntervalMinutes;
    }

    public String getEndIntervalMinutes() {
        return endIntervalMinutes;
    }

    public void setEndIntervalMinutes(String endIntervalMinutes) {
        this.endIntervalMinutes = endIntervalMinutes;
    }

    public double getHoursAmount() {
        return hoursAmount;
    }

    public void setHoursAmount(double hoursAmount) {
        this.hoursAmount = hoursAmount;
    }
}
