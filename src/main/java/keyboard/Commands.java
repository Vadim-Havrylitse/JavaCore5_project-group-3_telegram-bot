package keyboard;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import user.UserService;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import telegramService.TelegramApi;

public interface Commands {

    String getTitle();
    String getCallbackData();

    void pressButton(TelegramApi bot, CallbackQuery callbackQuery, UserService userService);
}
