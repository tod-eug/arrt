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

    public boolean saveJob(String userId, JobLog jl) {

        SimpleDateFormat createDateDefaultPattern = new SimpleDateFormat(DatabaseHelper.createDateDefaultPattern);
        SimpleDateFormat jobDatePattern = new SimpleDateFormat(DatabaseHelper.jobDatePattern);
        SimpleDateFormat timeIntervalPattern = new SimpleDateFormat(DatabaseHelper.timeIntervalPattern);

        String jobDate = jobDatePattern.format(DateUtils.truncate(jl.getJobDate(), Calendar.DAY_OF_MONTH));
        Date startDate = DateUtil.updateJobTime(jl.getJobDate(), jl.getStartIntervalHours(), jl.getStartIntervalMinutes());
        Date endDate = DateUtil.updateJobTime(jl.getJobDate(), jl.getEndIntervalHours(), jl.getEndIntervalMinutes());

        String startInterval = createDateDefaultPattern.format(startDate);
        String endInterval = timeIntervalPattern.format(endDate);

        String hours = DateUtil.calculateHours(startDate, endDate);
        UUID id = UUID.randomUUID();
        String createdDate = timeIntervalPattern.format(new Date());

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
