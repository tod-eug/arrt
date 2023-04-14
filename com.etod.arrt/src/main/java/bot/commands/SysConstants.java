package bot.commands;

import java.util.Arrays;
import java.util.List;

public interface SysConstants {

    List<Long> allowedUsers = Arrays.asList(388460760L, 447166967L);
    List<String> INITIAL_HOURS = Arrays.asList("06", "07", "08", "09");
    List<String> INITIAL_MINUTES = Arrays.asList("00", "15", "30", "45");
    List<String> END_HOURS = Arrays.asList("07", "08", "09", "10", "11", "12", "13", "14", "15");
    List<String> END_MINUTES = Arrays.asList("00", "15", "30", "45");
    String INITIAL_HOURS_CALLBACK_TYPE = "inhrs";
    String INITIAL_MINUTES_CALLBACK_TYPE = "inmins";
    String END_HOURS_CALLBACK_TYPE = "endhrs";
    String END_MINUTES_CALLBACK_TYPE = "endmins";
    String DELIMITER = "-";

}
