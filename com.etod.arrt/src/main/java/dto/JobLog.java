package dto;

import java.util.Date;

public class JobLog {

    private final Date jobDate;
    private final Date startInterval;
    private final Date endInterval;
    private final double hours;

    public JobLog(Date jobDate,
                  Date startInterval,
                  Date endInterval,
                  double hours) {
        this.jobDate = jobDate;
        this.startInterval = startInterval;
        this.endInterval = endInterval;
        this.hours = hours;
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

    public double getHours() {
        return hours;
    }
}
