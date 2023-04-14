package bot;

import bot.commands.SysConstants;

public class PermissionsChecker {

    public static boolean isAllowed(Long userId) {
        boolean allowed = false;
        for (long l : SysConstants.allowedUsers) {
            if (userId == l) {
                allowed = true;
            }
        }
        return allowed;
    }
}
