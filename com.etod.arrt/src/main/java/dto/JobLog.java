package dto;

import java.util.Date;

public class JobLog {

    private boolean completed;
    private Date jobDate;
    private Date startInterval;
    private Date endInterval;

    public JobLog() {
        this.jobDate = null;
        this.startInterval = null;
        this.endInterval = null;
        this.completed = false;
    }

    public void setStartInterval(Date startInterval) {
        this.startInterval = startInterval;
    }

    public void setEndInterval(Date endInterval) {
        this.endInterval = endInterval;
    }

    public void setJobDate(Date jobDate) {
        this.jobDate = jobDate;
    }

    public Date getJobDate() {
        return jobDate;
    }

    public Date getStartInterval() {
        return startInterval;
    }

    public Date getEndInterval() {
        return endInterval;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
