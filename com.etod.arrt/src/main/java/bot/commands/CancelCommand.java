package bot.commands;

import bot.ArbeitenBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class CancelCommand implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return "cancel";
    }

    @Override
    public String getDescription() {
        return "cancel";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        MessageProcessor mp = new MessageProcessor();
        ArbeitenBot.stateMap.remove(message.getFrom().getId());
        DeleteMessage dm = new DeleteMessage();
        dm.setChatId(message.getChatId());
        dm.setMessageId(message.getMessageId());
        mp.deleteMsg(absSender, dm);
    }
}
