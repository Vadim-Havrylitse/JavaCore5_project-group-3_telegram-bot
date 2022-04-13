package keyboard.comandsWithMark;

import Setting.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@AllArgsConstructor
@Getter
public enum CommandCurrency implements CommandsWithMark {
    USD("USD",
            "USD"),
    EUR("EUR",
            "EUR"),
    BTC("BTC",
            "BTC");

    private final String title;
    private final String callbackData;

    @Override
    public EditMessageReplyMarkup pressButton(CallbackQuery callbackQuery, UserService userService) {
        return null;
    }
}
