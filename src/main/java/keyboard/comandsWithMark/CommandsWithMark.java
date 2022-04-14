package keyboard.comandsWithMark;

import Setting.UserService;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CommandsWithMark {

    String getTitle();
    String getCallbackData();

    EditMessageReplyMarkup pressButton(CallbackQuery callbackQuery, UserService userService);

}
