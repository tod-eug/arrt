package db;

import dto.JobLog;
import org.apache.commons.lang3.time.DateUtils;
import util.DateUtil;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class JobLogHelper {

    public boolean saveJob(String userId, Date jobDateD, JobLog jl) {

        SimpleDateFormat createDateDefaultPattern = new SimpleDateFormat(DatabaseHelper.createDateDefaultPattern);
        SimpleDateFormat jobDatePattern = new SimpleDateFormat(DatabaseHelper.jobDatePattern);
        SimpleDateFormat timeIntervalPattern = new SimpleDateFormat(DatabaseHelper.timeIntervalPattern);

        Date startDate = DateUtil.parseDate(jl.getStartIntervalHours(), jl.getStartIntervalMinutes());
        Date endDate = DateUtil.parseDate(jl.getEndIntervalHours(), jl.getEndIntervalMinutes());

        String jobDate = jobDatePattern.format(DateUtils.truncate(jobDateD, Calendar.DAY_OF_MONTH));
        String startInterval = createDateDefaultPattern.format(startDate);
        String endInterval = timeIntervalPattern.format(endDate);
        String createdDate = timeIntervalPattern.format(new Date());

        String hours = DateUtil.calculateHours(startDate, endDate);

        UUID id = UUID.randomUUID();

        String insertQuery = String.format("insert into job_history (id, user_id, date, start_time, end_time, hours, create_date) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                id, userId, jobDate, startInterval, endInterval, hours, createdDate);

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
