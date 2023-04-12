package bot;

import dto.JobLog;

import java.text.SimpleDateFormat;

public class MessageProvider {

    public static final String defaultPattern = "dd.MM.yyyy HH:mm:ss";

    public static String getJobLoggedMessage(JobLog jl) {

        SimpleDateFormat dPattern = new SimpleDateFormat(defaultPattern);
        StringBuilder sb = new StringBuilder();
        return sb.append("✅ Зафиксировано время:\n")
                .append("Начало:       <b>").append(dPattern.format(jl.getStartInterval())).append("</b>\n")
                .append("Окончание: <b>").append(dPattern.format(jl.getEndInterval())).append("</b>\n")
                .append("Количество часов: <b>").append(jl.getHours()).append("</b>").toString();
    }
}