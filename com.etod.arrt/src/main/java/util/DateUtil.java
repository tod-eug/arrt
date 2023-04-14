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


    public static boolean isDateCorrect(String dayS, String monthS) {
        boolean correct = true;
        int day = Integer.parseInt(dayS);
        int month = parseMonth(monthS);
        switch (month) {
            case 1:
                if (day == 29 || day == 30 || day == 31)
                    correct = false;
                break;
            case 3:
            case 5:
            case 8:
            case 10:
                if (day == 31)
                    correct = false;
                break;
            default:
                break;
        }
        return correct;
    }

    public static Date parseDate(String dayS, String monthS) {
        Date jobDate = new Date();
        int day = Integer.parseInt(dayS);
        int month = parseMonth(monthS);
        jobDate = DateUtils.truncate(jobDate, Calendar.DAY_OF_MONTH);
        jobDate = DateUtils.setDays(jobDate, day);
        jobDate = DateUtils.setMonths(jobDate, month);
        return jobDate;
    }

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

    private static int parseMonth(String month) {
        switch (month) {
            case "Янв":
                return 0;
            case "Фев":
                return 1;
            case "Мар":
                return 2;
            case "Апр":
                return 3;
            case "Май":
                return 4;
            case "Июн":
                return 5;
            case "Июл":
                return 6;
            case "Авг":
                return 7;
            case "Сен":
                return 8;
            case "Окт":
                return 9;
            case "Ноя":
                return 10;
            case "Дек":
                return 11;
            default:
                return -1;
        }
    }
}
