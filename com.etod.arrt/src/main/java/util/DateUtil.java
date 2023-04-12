package util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class DateUtil {

    public static String calculateHours(Date startDate, Date endDate) {
        ZonedDateTime start = ZonedDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault());
        ZonedDateTime end = ZonedDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault());
        DecimalFormat f = new DecimalFormat("##.00");

        Duration total = Duration.ofMinutes(ChronoUnit.MINUTES.between(start, end));
        double hours = total.getSeconds() / 3600d;
        return f.format(hours);
    }

    public static Date updateJobTime(Date jobDate, String startHours, String startMinutes) {
        Integer hours = parseInt(startHours);
        Integer minutes = parseInt(startMinutes);
        jobDate = DateUtils.truncate(jobDate, Calendar.DAY_OF_MONTH);
        jobDate = DateUtils.setHours(jobDate, hours);
        jobDate = DateUtils.setMinutes(jobDate, minutes);
        jobDate = DateUtils.setSeconds(jobDate, 0);
        return jobDate;
    }
}
