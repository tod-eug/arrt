package db;

import org.apache.commons.lang3.time.DateUtils;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class JobLogHelper {

    public boolean saveJob(String userId, Date jobDateD, Date startIntervalD, Date endIntervalD) {

        SimpleDateFormat createDateDefaultPattern = new SimpleDateFormat(DatabaseHelper.createDateDefaultPattern);
        SimpleDateFormat jobDatePattern = new SimpleDateFormat(DatabaseHelper.jobDatePattern);
        SimpleDateFormat timeIntervalPattern = new SimpleDateFormat(DatabaseHelper.timeIntervalPattern);


        String jobDate = jobDatePattern.format(DateUtils.truncate(jobDateD, Calendar.DAY_OF_MONTH));
        String startInterval = createDateDefaultPattern.format(setMeaninglessDay(startIntervalD));
        String endInterval = timeIntervalPattern.format(setMeaninglessDay(endIntervalD));
        String createdDate = timeIntervalPattern.format(new Date());

        UUID id = UUID.randomUUID();

        String insertQuery = String.format("insert into users (id, user_id, date, start_time, end_time, create_date) VALUES ('%s', '%s', '%s', '%s', '%s', '%s');",
                id, userId, jobDate, startInterval, endInterval, createdDate);

        DatabaseHelper dbHelper = new DatabaseHelper();
        try {
            dbHelper.getPreparedStatement(insertQuery).execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            dbHelper.closeConnections();
        }
        return true;
    }

    private Date setMeaninglessDay(Date date) {
        DateUtils.setYears(date, 1900);
        DateUtils.setMonths(date, 1);
        DateUtils.setDays(date, 1);
        return date;
    }
}
