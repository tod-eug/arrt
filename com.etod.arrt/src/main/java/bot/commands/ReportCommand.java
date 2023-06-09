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

        SendMessage sm = new SendMessage();
        sm.setChatId(message.getChatId());

        if (PermissionsChecker.isAllowed(message.getFrom().getId())) {

            sm.setText(ReplyConstants.WHAT_PERIOD_FOR_RESULTS);
            sm.setReplyMarkup(Keyboards.getKeyboard(SysConstants.RESULTS_ROOT_CALLBACK_TYPE, SysConstants.REPORT_CALLBACK_TYPE, DateUtil.getMonthsAsStrings(5)));
        } else {
            sm.setText(ReplyConstants.NOT_ALLOWED);
        }

        MessageProcessor.sendMsg(absSender, sm);
        DeleteMessage dm = new DeleteMessage();
        dm.setChatId(message.getChatId());
        dm.setMessageId(message.getMessageId());
        MessageProcessor.deleteMsg(absSender, dm);
    }
}
