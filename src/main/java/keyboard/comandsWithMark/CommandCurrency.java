package keyboard.comandsWithMark;

import Setting.UserService;
import keyboard.Keyboard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@AllArgsConstructor
@Getter
public enum CommandCurrency implements CommandsWithMark {
    USD("USD",
            "USD", "840"),
    EUR("EUR",
            "EUR", "978"),
    GBP("GBP",
            "GBP", "826");

    private final String title;
    private final String callbackData;
    private final String codeISOL;

    public static boolean currencyExists(String currency) {
        if (currency == null) {
            return false;
        }
        return switch (currency) {
            case "USD", "EUR", "GBP" -> true;
            default -> false;
        };
    }

    @Override
    public EditMessageReplyMarkup pressButton(CallbackQuery callbackQuery, UserService userService) {
        EditMessageReplyMarkup answerMessage = new EditMessageReplyMarkup();

        userService.changeCurrency(callbackQuery.getMessage(), this);

        answerMessage.setChatId(callbackQuery.getMessage().getChatId().toString());
        answerMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        answerMessage.setReplyMarkup(
                Keyboard.createKeyboardWithMark(userService.getUser(callbackQuery.getMessage()).getCurrency(), CommandCurrency.values()));

        return answerMessage;
    }
}
