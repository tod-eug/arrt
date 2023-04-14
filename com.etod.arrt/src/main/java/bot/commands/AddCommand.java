package bot.commands;

import bot.ArbeitenBot;
import bot.PermissionsChecker;
import bot.ReplyConstants;
import bot.keyboards.Keyboards;
import dto.JobLogRaw;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class AddCommand implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "add";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

        MessageProcessor mp = new MessageProcessor();
        SendMessage sm = new SendMessage();
        sm.setChatId(message.getChatId());

        if (PermissionsChecker.isAllowed(message.getFrom().getId())) {
            JobLogRaw jl = new JobLogRaw();
            ArbeitenBot.stateMap.put(message.getFrom().getId(), jl);

            sm.setText(ReplyConstants.CHOOSE_DAY);
            sm.setReplyMarkup(Keyboards.getKeyboard(SysConstants.DAYS_CALLBACK_TYPE, SysConstants.DAYS));
        } else {
            sm.setText(ReplyConstants.NOT_ALLOWED);
        }

        mp.sendMsg(absSender, sm);
    }
}
