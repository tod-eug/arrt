package bot.commands;

import bot.ArbeitenBot;
import bot.PermissionsChecker;
import bot.ReplyConstants;
import bot.keyboards.Keyboards;
import dto.JobLogRaw;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Date;

public class TodayCommand implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return "today";
    }

    @Override
    public String getDescription() {
        return "today";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

        MessageProcessor mp = new MessageProcessor();
        SendMessage sm = new SendMessage();
        sm.setChatId(message.getChatId());

        if (PermissionsChecker.isAllowed(message.getFrom().getId())) {
            JobLogRaw jl = new JobLogRaw();
            jl.setJobDate(new Date());
            ArbeitenBot.stateMap.put(message.getFrom().getId(), jl);

            sm.setText(ReplyConstants.CHOOSE_INITIAL_HOUR);
            sm.setReplyMarkup(Keyboards.getKeyboard(SysConstants.JOB_LOG_ROOT_CALLBACK_TYPE, SysConstants.INITIAL_HOURS_CALLBACK_TYPE, SysConstants.INITIAL_HOURS));
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
