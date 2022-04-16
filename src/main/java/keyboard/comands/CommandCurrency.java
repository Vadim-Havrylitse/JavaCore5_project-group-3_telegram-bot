package keyboard.comands;

import keyboard.Commands;
import user.UserService;
import keyboard.Keyboard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import telegramService.TelegramApi;

@AllArgsConstructor
@Getter
public enum CommandCurrency implements Commands {
    USD("USD",
            "USD"),
    EUR("EUR",
            "EUR"),
    BTC("BTC",
            "BTC");

    private final String title;
    private final String callbackData;

    @SneakyThrows
    @Override
    public void pressButton(TelegramApi bot, CallbackQuery callbackQuery, UserService userService) {
        EditMessageReplyMarkup answerMessage = new EditMessageReplyMarkup();

        userService.changeCurrency(callbackQuery.getMessage(), this);

        answerMessage.setChatId(callbackQuery.getMessage().getChatId().toString());
        answerMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        answerMessage.setReplyMarkup(
                Keyboard.createKeyboardWithMark(userService.getUser(callbackQuery.getMessage()).getCurrency(), CommandCurrency.values()));

        bot.execute(answerMessage);
    }
}
