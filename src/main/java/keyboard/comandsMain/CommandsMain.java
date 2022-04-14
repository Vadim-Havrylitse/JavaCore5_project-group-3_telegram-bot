package keyboard.comandsMain;

import Setting.UserService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CommandsMain {

    String getTitle();
    String getCallbackData();

    SendMessage pressButton(CallbackQuery callbackQuery, UserService userService);
}
