package util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
        jobDate.setDate(day);
        jobDate.setMonth(month);
        return jobDate;
    }

    public static Date getDate(int day, int month) {
        Date d = new Date();
        d = DateUtils.truncate(d, Calendar.DAY_OF_MONTH);
        d.setMonth(month);
        d.setDate(day);
        return d;
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

    public static List<String> getMonthsAsStrings(int monthsAmount) {
        List<String> result = new ArrayList<>();

        Date d = new Date();
        int month = d.getMonth();
        for (int i = monthsAmount - 1; i >= 0; i--) {
            result.add(getMonthName(month - i));
        }
        return result;
    }

    public static Map<Integer, Date> getStartEndByMonthString(String monthS) {
        Map<Integer, Date> result = new HashMap<>();

        Date start = new Date();
        start = DateUtils.truncate(start, Calendar.DAY_OF_MONTH);
        start.setDate(1);
        start.setMonth(parseMonth(monthS.substring(0, 3)));

        Date end = new Date();
        end = DateUtils.truncate(end, Calendar.DAY_OF_MONTH);
        end.setMonth(parseMonth(monthS.substring(0, 3)));
        end.setDate(getLastDayOfMonth(parseMonth(monthS.substring(0, 3))));

        result.put(1, start);
        result.put(2, end);
        return result;
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

    public static String getMonthName(int month) {
        while (month < 0) {
            month = month + 12;
        }
        switch (month) {
            case 0:
                return "Январь";
            case 1:
                return "Февраль";
            case 2:
                return "Март";
            case 3:
                return "Апрель";
            case 4:
                return "Май";
            case 5:
                return "Июнь";
            case 6:
                return "Июль";
            case 7:
                return "Август";
            case 8:
                return "Сентябрь";
            case 9:
                return "Октябрь";
            case 10:
                return "Ноябрь";
            case 11:
                return "Декабрь";
            default:
                return "";
        }
    }

    public static int getLastDayOfMonth(int month) {
        switch (month) {
            case 1:
                return 28;
            case 3:
            case 5:
            case 8:
            case 10:
                return 30;
            default:
                return 31;
        }
    }

    public static String getDayOfTheWeek(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                return "Пн";
            case 2:
                return "Вт";
            case 3:
                return "Ср";
            case 4:
                return "Чт";
            case 5:
                return "Пт";
            case 6:
                return "Сб";
            case 7:
                return "Вс";
            default:
                return "";
        }

    }
}
