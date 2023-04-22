package bot;

import bot.commands.*;
import bot.keyboards.Keyboards;
import db.JobLogHelper;
import db.UsersHelper;
import dto.JobLog;
import dto.JobLogRaw;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import output.pdf.PdfsWriter;
import util.DateUtil;
import util.PropertiesProvider;

import java.io.File;
import java.util.*;

public class ArbeitenBot extends TelegramLongPollingCommandBot {

    public static Map<Long, JobLogRaw> stateMap = new HashMap<>();

    public ArbeitenBot() {
        super();
        register(new StartCommand());
        register(new TodayCommand());
        register(new AddCommand());
        register(new ResultsCommand());
        register(new ReportCommand());
        register(new CancelCommand());
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
        if (update.hasMessage()) {
            deleteMessage(update.getMessage().getChatId(), update.getMessage().getMessageId());
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

            switch (parsedCallback[0]) {
                case SysConstants.JOB_LOG_ROOT_CALLBACK_TYPE:
                    processJobLogCallCallbackQuery(parsedCallback, userId, chatId, messageId, update.getCallbackQuery().getFrom());
                    break;
                case SysConstants.RESULTS_ROOT_CALLBACK_TYPE:
                    processReportCallbackQuery(parsedCallback, userId, chatId, messageId, update.getCallbackQuery().getFrom());
                    break;
            }
    }

    private void processJobLogCallCallbackQuery(String[] parsedCallback, Long userId, Long chatId, int messageId, User user) {
        JobLogRaw jlr = stateMap.get(userId);
        if (jlr != null) {
            String value = parsedCallback[2];

            switch (parsedCallback[1]) {
                case SysConstants.DAYS_CALLBACK_TYPE:
                    jlr.setDayOfDate(value);
                    editMessage(chatId, messageId, ReplyConstants.CHOOSE_MONTH, false,
                            Keyboards.getKeyboard(SysConstants.JOB_LOG_ROOT_CALLBACK_TYPE, SysConstants.MONTHS_CALLBACK_TYPE, SysConstants.MONTHS));
                    stateMap.put(userId, jlr);
                    break;
                case SysConstants.MONTHS_CALLBACK_TYPE:
                    jlr.setMonthOfDate(value);
                    if (DateUtil.isDateCorrect(jlr.getDayOfDate(), jlr.getMonthOfDate())) {
                        jlr.setJobDate(DateUtil.parseDate(jlr.getDayOfDate(), jlr.getMonthOfDate()));
                        editMessage(chatId, messageId, ReplyConstants.CHOOSE_INITIAL_HOUR, false,
                                Keyboards.getKeyboard(SysConstants.JOB_LOG_ROOT_CALLBACK_TYPE, SysConstants.INITIAL_HOURS_CALLBACK_TYPE, SysConstants.INITIAL_HOURS));
                        stateMap.put(userId, jlr);
                    } else
                        editMessage(chatId, messageId, MessageProvider.getDateIsErrorMessage(jlr), false, null);
                    break;
                case SysConstants.INITIAL_HOURS_CALLBACK_TYPE:
                    jlr.setStartIntervalHours(value);
                    editMessage(chatId, messageId, ReplyConstants.CHOOSE_INITIAL_MINUTES, false,
                            Keyboards.getKeyboard(SysConstants.JOB_LOG_ROOT_CALLBACK_TYPE, SysConstants.INITIAL_MINUTES_CALLBACK_TYPE, SysConstants.INITIAL_MINUTES));
                    stateMap.put(userId, jlr);
                    break;
                case SysConstants.INITIAL_MINUTES_CALLBACK_TYPE:
                    jlr.setStartIntervalMinutes(value);
                    editMessage(chatId, messageId, ReplyConstants.CHOOSE_END_HOUR, false,
                            Keyboards.getKeyboard(SysConstants.JOB_LOG_ROOT_CALLBACK_TYPE, SysConstants.END_HOURS_CALLBACK_TYPE, SysConstants.END_HOURS));
                    stateMap.put(userId, jlr);
                    break;
                case SysConstants.END_HOURS_CALLBACK_TYPE:
                    jlr.setEndIntervalHours(value);
                    editMessage(chatId, messageId, ReplyConstants.CHOOSE_END_MINUTES, false,
                            Keyboards.getKeyboard(SysConstants.JOB_LOG_ROOT_CALLBACK_TYPE, SysConstants.END_MINUTES_CALLBACK_TYPE, SysConstants.END_MINUTES));
                    stateMap.put(userId, jlr);
                    break;
                case SysConstants.END_MINUTES_CALLBACK_TYPE:
                    jlr.setEndIntervalMinutes(value);
                    jlr.setCompleted(true);
                    JobLogHelper jlh = new JobLogHelper();
                    UsersHelper uh = new UsersHelper();
                    String userUuid = uh.findUserByTgId(userId.toString(), user, chatId.toString());
                    UUID uuid = jlh.saveJob(userUuid, jlr);
                    JobLog finalJobLog;
                    if (uuid != null) {
                        finalJobLog = jlh.getJob(uuid.toString());
                        editMessage(chatId, messageId, MessageProvider.getJobLoggedMessage(finalJobLog), true, null);
                    } else
                        deleteMessage(chatId, messageId);
                    stateMap.remove(userId);
                    break;
            }
        } else {
            deleteMessage(chatId, messageId);
        }
    }

    private void processReportCallbackQuery(String[] parsedCallback, Long userId, Long chatId, int messageId, User user) {
        JobLogHelper jlh = new JobLogHelper();
        UsersHelper uh = new UsersHelper();
        Map<Integer, Date> map = DateUtil.getMonthIntervalString(parsedCallback[2]);
        String userUuid = uh.findUserByTgId(userId.toString(), user, chatId.toString());
        Map<Date, JobLog> jls = jlh.getJobs(userUuid, map.get(1), map.get(2));

        switch (parsedCallback[1]) {
            case SysConstants.RESULTS_CALLBACK_TYPE:
                editMessage(chatId, messageId, MessageProvider.getMonthResultsMessage(jls), true, null);
                break;
            case SysConstants.REPORT_CALLBACK_TYPE:
                File file = PdfsWriter.writePdfFile(map, jls);
                if (file != null) {
                    InputFile inputFile = new InputFile(file);

                    SendDocument document = new SendDocument();
                    document.setChatId(chatId);
                    document.setDocument(inputFile);
                    document.setCaption(MessageProvider.getReportMessage(map.get(1)));
                    try {
                        execute(document);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                break;
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
