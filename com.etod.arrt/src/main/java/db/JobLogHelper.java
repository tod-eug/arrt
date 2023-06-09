package db;

import db.mappers.JobLogMapper;
import dto.JobLog;
import dto.JobLogRaw;
import org.apache.commons.lang3.time.DateUtils;
import util.DateUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class JobLogHelper {

    public static UUID saveJob(String userId, JobLogRaw jl) {

        SimpleDateFormat createDateDefaultPattern = new SimpleDateFormat(DatabaseHelper.createDateDefaultPattern);
        SimpleDateFormat jobDatePattern = new SimpleDateFormat(DatabaseHelper.jobDatePattern);
        SimpleDateFormat timeIntervalPattern = new SimpleDateFormat(DatabaseHelper.timeIntervalPattern);

        String jobDate = jobDatePattern.format(DateUtils.truncate(jl.getJobDate(), Calendar.DAY_OF_MONTH));
        Date startDate = DateUtil.updateJobTime(jl.getJobDate(), jl.getStartIntervalHours(), jl.getStartIntervalMinutes());
        Date endDate = DateUtil.updateJobTime(jl.getJobDate(), jl.getEndIntervalHours(), jl.getEndIntervalMinutes());

        String startInterval = createDateDefaultPattern.format(startDate);
        String endInterval = timeIntervalPattern.format(endDate);

        String hours = DateUtil.calculateHours(startDate, endDate).replace(",", ".");
        UUID id = UUID.randomUUID();
        String createdDate = timeIntervalPattern.format(new Date());

        String insertQuery = String.format("insert into job_history (id, user_id, date, start_time, end_time, hours, create_date) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                id, userId, jobDate, startInterval, endInterval, hours, createdDate);

        DatabaseHelper dbHelper = new DatabaseHelper();
        try {
            dbHelper.getPreparedStatement(insertQuery).execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            dbHelper.closeConnections();
        }
        return id;
    }

    public static Map<Date, JobLog> getJobs(String userId, Date from, Date to) {

        Map<Date, JobLog> result = new HashMap<>();
        SimpleDateFormat timeIntervalPattern = new SimpleDateFormat(DatabaseHelper.timeIntervalPattern);

        String startInterval = timeIntervalPattern.format(from);
        String endInterval = timeIntervalPattern.format(to);

        String selectQuery = String.format("select * from public.job_history where user_id = '%s' and start_time >= '%s' and end_time <= '%s' order by date;", userId, startInterval, endInterval);

        DatabaseHelper dbHelper = new DatabaseHelper();
        try {
            ResultSet st = dbHelper.getPreparedStatement(selectQuery).executeQuery();
            while(st.next()) {
                result.put(st.getDate("date"), JobLogMapper.mapJobLog(st.getString("date"), st.getString("start_time"), st.getString("end_time"), st.getString("hours")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHelper.closeConnections();
        }
        return result;
    }

    public static JobLog getJob(String jobItemId) {

        JobLog result = null;

        String selectQuery = String.format("select * from public.job_history where id = '%s';", jobItemId);

        DatabaseHelper dbHelper = new DatabaseHelper();
        try {
            ResultSet st = dbHelper.getPreparedStatement(selectQuery).executeQuery();
            while(st.next()) {
                result = JobLogMapper.mapJobLog(st.getString("date"), st.getString("start_time"), st.getString("end_time"), st.getString("hours"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHelper.closeConnections();
        }
        return result;
    }
}
