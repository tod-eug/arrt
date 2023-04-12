package bot;

import bot.commands.AddTodayCommand;
import bot.commands.StartCommand;
import bot.commands.SysConstants;
import bot.keyboards.Keyboards;
import db.JobLogHelper;
import db.UsersHelper;
import dto.JobLogRaw;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import util.PropertiesProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArbeitenBot extends TelegramLongPollingCommandBot {

    public static Map<Long, JobLogRaw> stateMap = new HashMap<>();

    public ArbeitenBot() {
        super();
        register(new StartCommand());
        register(new AddTodayCommand());
    }

    @Override
    public String getBotUsername() {
        return PropertiesProvider.configurationProperties.get("BotName");
    }

    @Override
    public String getBotToken() {
        return PropertiesProvider.configurationProperties.get("BotToken");
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            processCallbackQuery(update);
        }
    }

    @Override
    public void processInvalidCommandUpdate(Update update) {
        super.processInvalidCommandUpdate(update);
    }

    @Override
    public boolean filter(Message message) {
        return super.filter(message);
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    private void processCallbackQuery(Update update) {
        Long userId = update.getCallbackQuery().getFrom().getId();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();

        String[] parsedCallback = update.getCallbackQuery().getData().split(SysConstants.DELIMITER);
        String value = parsedCallback[1];

        //edit JobLog
        JobLogRaw jl = stateMap.get(userId);
        if (jl != null) {
            switch (parsedCallback[0]) {
                case SysConstants.INITIAL_HOURS_CALLBACK_TYPE:
                    jl.setStartIntervalHours(value);
                    editMessage(chatId, messageId, ReplyConstants.CHOOSE_INITIAL_MINUTES, false,
                            Keyboards.getKeyboard(SysConstants.INITIAL_MINUTES_CALLBACK_TYPE, SysConstants.INITIAL_MINUTES));
                    stateMap.put(userId, jl);
                    break;
                case SysConstants.INITIAL_MINUTES_CALLBACK_TYPE:
                    jl.setStartIntervalMinutes(value);
                    editMessage(chatId, messageId, ReplyConstants.CHOOSE_END_HOUR, false,
                            Keyboards.getKeyboard(SysConstants.END_HOURS_CALLBACK_TYPE, SysConstants.END_HOURS));
                    stateMap.put(userId, jl);
                    break;
                case SysConstants.END_HOURS_CALLBACK_TYPE:
                    jl.setEndIntervalHours(value);
                    editMessage(chatId, messageId, ReplyConstants.CHOOSE_END_MINUTES, false,
                            Keyboards.getKeyboard(SysConstants.END_MINUTES_CALLBACK_TYPE, SysConstants.END_MINUTES));
                    stateMap.put(userId, jl);
                    break;
                case SysConstants.END_MINUTES_CALLBACK_TYPE:
                    jl.setEndIntervalMinutes(value);
                    jl.setCompleted(true);
                    JobLogHelper jlh = new JobLogHelper();
                    UsersHelper uh = new UsersHelper();
                    String userUuid = uh.findUserByTgId(userId.toString(), update.getCallbackQuery().getFrom(), chatId.toString());
                    if (jlh.saveJob(userUuid, jl))
                        editMessage(chatId, messageId, ReplyConstants.JOB_LOGGED, true, null);
                    else
                        deleteMessage(chatId, messageId);
                    stateMap.remove(userId);
                    break;
            }



        } else {
            deleteMessage(chatId, messageId);
        }
    }

    private void editMessage(long chatId, int messageId, String text, boolean htmlParseMode, InlineKeyboardMarkup keyboard) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setText(text);
        if (keyboard != null)
            editMessageText.setReplyMarkup(keyboard);
        if (htmlParseMode)
            editMessageText.setParseMode(ParseMode.HTML);
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void deleteMessage(long chatId, int messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(Long.toString(chatId));
        deleteMessage.setMessageId(messageId);
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
