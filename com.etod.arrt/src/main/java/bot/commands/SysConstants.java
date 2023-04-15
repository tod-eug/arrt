package bot.commands;

import java.util.Arrays;
import java.util.List;

public interface SysConstants {

    List<Long> allowedUsers = Arrays.asList(388460760L, 447166967L);
    String DELIMITER = "-";
    List<String> INITIAL_HOURS = Arrays.asList("06", "07", "08", "09");
    List<String> INITIAL_MINUTES = Arrays.asList("00", "15", "30", "45");
    List<String> END_HOURS = Arrays.asList("07", "08", "09", "10", "11", "12", "13", "14", "15");
    List<String> END_MINUTES = Arrays.asList("00", "15", "30", "45");
    List<String> DAYS = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
    List<String> MONTHS = Arrays.asList("Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек");
    String JOB_LOG_ROOT_CALLBACK_TYPE = "joblog";
    String REPORT_ROOT_CALLBACK_TYPE = "report";
    String INITIAL_HOURS_CALLBACK_TYPE = "inhrs";
    String INITIAL_MINUTES_CALLBACK_TYPE = "inmins";
    String END_HOURS_CALLBACK_TYPE = "endhrs";
    String END_MINUTES_CALLBACK_TYPE = "endmins";
    String DAYS_CALLBACK_TYPE = "datedays";
    String MONTHS_CALLBACK_TYPE = "datemonths";
    String REPORT_PR_MONTH_CALLBACK_TYPE = "previousmonth";

}
