package keyboard;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import telegramService.TelegramApi;
import user.UserService;

public interface Commands {

    String getTitle();
    String getCallbackData();

    void pressButton(TelegramApi bot, CallbackQuery callbackQuery, UserService userService);
}
