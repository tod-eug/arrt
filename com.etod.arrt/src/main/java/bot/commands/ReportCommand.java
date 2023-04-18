package bot.commands;

import bot.PermissionsChecker;
import bot.ReplyConstants;
import bot.keyboards.Keyboards;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import util.DateUtil;

public class ReportCommand implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return "report";
    }

    @Override
    public String getDescription() {
        return "report";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        MessageProcessor mp = new MessageProcessor();
        SendMessage sm = new SendMessage();
        sm.setChatId(message.getChatId());

        if (PermissionsChecker.isAllowed(message.getFrom().getId())) {

            sm.setText(ReplyConstants.WHAT_PERIOD_FOR_REPORT);
            sm.setReplyMarkup(Keyboards.getKeyboard(SysConstants.REPORT_ROOT_CALLBACK_TYPE, SysConstants.REPORT_PR_MONTH_CALLBACK_TYPE, DateUtil.getMonthsAsStrings(5)));
        } else {
            sm.setText(ReplyConstants.NOT_ALLOWED);
        }

        mp.sendMsg(absSender, sm);
        DeleteMessage dm = new DeleteMessage();
        dm.setChatId(message.getChatId());
        dm.setMessageId(message.getMessageId());
        mp.deleteMsg(absSender, dm);
    }
}
