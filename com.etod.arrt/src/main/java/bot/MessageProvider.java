package bot;

import dto.JobLog;
import dto.JobLogRaw;
import util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class MessageProvider {

    public static final String defaultPattern = "dd.MM.yyyy HH:mm:ss";
    public static final String simpleDate = "dd.MM.yyyy";
    public static final String simpleTime = "HH:mm:ss";

    public static String getJobLoggedMessage(JobLog jl) {

        SimpleDateFormat dPattern = new SimpleDateFormat(defaultPattern);
        StringBuilder sb = new StringBuilder();
        return sb.append("✅ Зафиксировано время:\n")
                .append("Начало:       <b>").append(dPattern.format(jl.getStartInterval())).append("</b>\n")
                .append("Окончание: <b>").append(dPattern.format(jl.getEndInterval())).append("</b>\n")
                .append("Количество часов: <b>").append(jl.getHours()).append("</b>").toString();
    }

    public static String getDateIsErrorMessage(JobLogRaw jlr) {
        StringBuilder sb = new StringBuilder();
        return sb.append("Вы ошиблись в выборе даты.\n")
                .append("У месяца ").append(jlr.getMonthOfDate()).append(" нет дня ").append(jlr.getDayOfDate()).toString();
    }

    public static String getMonthResultsMessage(Map<Date, JobLog> jls, Map<Integer, Date> interval) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat patternSimpleDate = new SimpleDateFormat(simpleDate);
        SimpleDateFormat patternSimpleTime = new SimpleDateFormat(simpleTime);

        double sum = 0;
        sb.append("Зафиксированы следующие часы работы: \n\n");
        Set<Date> set = jls.keySet();
        int month = interval.get(1).getMonth();

        //get month days
        int monthStartDate = 1;
        int monthEndDate = DateUtil.getLastDayOfMonth(month);

        for (int i = monthStartDate; i <= monthEndDate; i++) {
            Date d = DateUtil.getDate(i, month);

            if (set.contains(d)) {
                JobLog jl = jls.get(d);
                sb.append(patternSimpleDate.format(jl.getJobDate())).append("   ").append(patternSimpleTime.format(jl.getStartInterval())).append(" - ").append(patternSimpleTime.format(jl.getEndInterval())).append("    ")
                        .append("<b>").append(jl.getHours()).append("</b> ч.\n");
                sum = sum + jl.getHours();
            }
        }

        sb.append("\n").append("Общее количество часов: ").append(sum);
        return sb.toString();
    }

    public static String getReportMessage(Date startInterval) {
        return "Отчет отработанного времени " + DateUtil.getPeriodString(startInterval);
    }
}
