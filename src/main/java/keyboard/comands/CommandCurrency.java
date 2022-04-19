package keyboard.comands;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Arrays;

import keyboard.Commands;
import keyboard.Keyboard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import telegramService.TelegramApi;
import user.UserService;

@AllArgsConstructor
@Getter
public enum CommandCurrency implements Commands {
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

        return Arrays.stream(values())
                .anyMatch(v -> v.getTitle().equals(currency));
    }

    @SneakyThrows
    @Override
    public void pressButton(TelegramApi bot, CallbackQuery callbackQuery, UserService userService) {
        Long chatId = callbackQuery.getMessage().getChatId();

        userService.changeCurrency(chatId, this);

        EditMessageReplyMarkup answerMessage = new EditMessageReplyMarkup();
        answerMessage.setChatId(callbackQuery.getMessage().getChatId().toString());
        answerMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        answerMessage.setReplyMarkup(
                Keyboard.createKeyboardWithMark(userService.getUser(chatId).getCurrency(), CommandCurrency.values()));

        bot.execute(answerMessage);
    }
}
