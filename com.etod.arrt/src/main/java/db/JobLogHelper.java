package db;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class JobLogHelper {

    public boolean saveJob(String userId, Date jobDateD, Date startIntervalD, Date endIntervalD) {

        SimpleDateFormat createDateDefaultPattern = new SimpleDateFormat(DatabaseHelper.createDateDefaultPattern);
        SimpleDateFormat jobDatePattern = new SimpleDateFormat(DatabaseHelper.jobDatePattern);
        SimpleDateFormat timeIntervalPattern = new SimpleDateFormat(DatabaseHelper.timeIntervalPattern);

        String jobDate = jobDatePattern.format(jobDateD);
        String startInterval = createDateDefaultPattern.format(startIntervalD);
        String endInterval = timeIntervalPattern.format(endIntervalD);
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
}
