package keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;
import java.util.function.Function;

public class Keyboard {

    public static InlineKeyboardMarkup createKeyboardInline (Commands[] arrCommands){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsButton = new ArrayList<>();
        List<InlineKeyboardButton> rowButton = new ArrayList<>();
        for(Commands element : arrCommands){
            rowButton.add(inlineButton.apply(element));
            rowsButton.add(rowButton);
            rowButton = new ArrayList<>();
        }
        keyboardMarkup.setKeyboard(rowsButton);
        return keyboardMarkup;
    }

    private static Function<Commands, InlineKeyboardButton> inlineButton = element -> {
        InlineKeyboardButton button = new InlineKeyboardButton(element.getTitle());
        button.setCallbackData(element.getCallbackData());
        return button;
    };

}
