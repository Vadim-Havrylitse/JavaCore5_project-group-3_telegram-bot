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
public enum CommandAccuracy implements Commands {

    TWO("2",
            "2"),
    THREE("3",
            "3"),
    FOUR("4",
            "4");

    private final String title;
    private final String callbackData;

    @SneakyThrows
    @Override
    public void pressButton(TelegramApi bot, CallbackQuery callbackQuery, UserService userService) {
        Long chatId = callbackQuery.getMessage().getChatId();

        userService.changeAccuracy(chatId, this);

        EditMessageReplyMarkup answerMessage = new EditMessageReplyMarkup();

        answerMessage.setChatId(chatId.toString());
        answerMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        answerMessage.setReplyMarkup(
                Keyboard.createKeyboardWithMark(userService.getUser(chatId).getAccuracy(), CommandAccuracy.values()));

        bot.execute(answerMessage);
    }
}
