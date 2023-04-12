package db.mappers;

import db.DatabaseHelper;
import dto.JobLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JobLogMapper {

    public JobLog mapJobLog(String jobDate, String startInterval, String endInterval, String hours) {

        SimpleDateFormat jobDatePattern = new SimpleDateFormat(DatabaseHelper.jobDatePattern);
        SimpleDateFormat timeIntervalPattern = new SimpleDateFormat(DatabaseHelper.timeIntervalPattern);

        Double hoursP = Double.parseDouble(hours.replace(",", "."));
        Date jobDateP;
        Date startTime;
        Date endTime;

        try {
            jobDateP = jobDatePattern.parse(jobDate);
            startTime = timeIntervalPattern.parse(startInterval);
            endTime = timeIntervalPattern.parse(endInterval);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new JobLog(jobDateP, startTime, endTime, hoursP);
    }
}
