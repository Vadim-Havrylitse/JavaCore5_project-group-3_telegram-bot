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
        Long chatId = callbackQuery.getMessage().getChatId();

        userService.changeBank(chatId, this);

        EditMessageReplyMarkup answerMessage = new EditMessageReplyMarkup();

        answerMessage.setChatId(chatId.toString());
        answerMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        answerMessage.setReplyMarkup(
                Keyboard.createKeyboardWithMark(
                        userService.getUser(chatId).getBank(), CommandBank.values()));

       bot.execute(answerMessage);
    }
}
