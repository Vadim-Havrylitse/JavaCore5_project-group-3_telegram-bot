package keyboard.comands;

import keyboard.Commands;
import user.UserService;
import keyboard.Keyboard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import telegramService.TelegramApi;

@Getter
@AllArgsConstructor
public enum CommandBank implements Commands {
    PRIVAT("PrivatBank",
            "PrivatBank"),
    MONO("Monobank",
            "Monobank"),
    NBU("NBU",
            "NBU");

    private final String title;
    private final String callbackData;

    @SneakyThrows
    @Override
    public void pressButton(TelegramApi bot, CallbackQuery callbackQuery, UserService userService) {
        EditMessageReplyMarkup answerMessage = new EditMessageReplyMarkup();
        Message message = callbackQuery.getMessage();
        userService.changeBank(message, this);

        answerMessage.setChatId(callbackQuery.getMessage().getChatId().toString());
        answerMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        answerMessage.setReplyMarkup(
                Keyboard.createKeyboardWithMark(
                        userService.getUser(callbackQuery.getMessage()).getBank(), CommandBank.values()));

       bot.execute(answerMessage);
    }
}
