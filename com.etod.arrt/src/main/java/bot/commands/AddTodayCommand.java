package bot.commands;

import bot.ArbeitenBot;
import dto.JobLog;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Date;

public class AddTodayCommand implements IBotCommand {
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
        JobLog jl = new JobLog();
        jl.setJobDate(new Date());
        ArbeitenBot.stateMap.put(message.getFrom().getId(), jl);
    }
}
