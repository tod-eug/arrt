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

        Duration total = Duration.ofMinutes(ChronoUnit.MINUTES.between(end, start));
        double hours = total.getSeconds() / 3600d;
        return f.format(hours);
    }

    public static Date parseDate(String startHours, String startMinutes) {
        Date date = new Date();
        Integer hours = parseInt(startHours);
        Integer minutes = parseInt(startMinutes);
        date = DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
        date = DateUtils.setYears(date, 1900);
        date = DateUtils.setMonths(date, 1);
        date = DateUtils.setDays(date, 1);
        date = DateUtils.setHours(date, hours);
        date = DateUtils.setMinutes(date, minutes);
        date = DateUtils.setSeconds(date, 0);
        return date;
    }
}
