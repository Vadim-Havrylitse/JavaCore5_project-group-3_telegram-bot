package keyboard.comandsWithMark;

import Setting.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Getter
@AllArgsConstructor
public enum CommandBank implements CommandsWithMark {
    PRIVAT("PrivatBank",
            "PrivatBank"),
    MONO("Monobank",
            "Monobank"),
    NBU("NBU",
            "NBU");

    private final String title;
    private final String callbackData;

    @Override
    public EditMessageReplyMarkup pressButton(CallbackQuery callbackQuery, UserService userService) {
        return null;
    }
}
