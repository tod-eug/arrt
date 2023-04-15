package bot.keyboards;

import bot.commands.SysConstants;
import org.apache.commons.collections4.ListUtils;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Keyboards {

    public static InlineKeyboardMarkup getKeyboard(String rootCallbackType, String callbackType, List<String> list) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        if (list != null) {
            if (!list.isEmpty()) {

                List<List<String>> dividedList = ListUtils.partition(list, 5);

                for (List<String> l : dividedList) {
                    List<InlineKeyboardButton> rowInline = new ArrayList<>();
                    for (String s : l) {
                        InlineKeyboardButton button = new InlineKeyboardButton();
                        button.setText(s);
                        button.setCallbackData(rootCallbackType + SysConstants.DELIMITER + callbackType + SysConstants.DELIMITER + s);
                        rowInline.add(button);
                    }
                    rowsInline.add(rowInline);
                }
            }
        }

        keyboardMarkup.setKeyboard(rowsInline);
        return keyboardMarkup;
    }
}
