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
            "2",
            "Ок, выбрали 2"),
    THREE("3",
            "3",
            "Ок, выбрали 3"),
    FOUR("4",
            "4",
            "Ок, выбрали 4");

    private final String title;
    private final String callbackData;
    private final String messageAfterPressButton;

    @SneakyThrows
    @Override
    public void pressButton(TelegramApi bot, CallbackQuery callbackQuery, UserService userService) {
        EditMessageReplyMarkup answerMessage = new EditMessageReplyMarkup();

        userService.changeAccuracy(callbackQuery.getMessage(), this);

        answerMessage.setChatId(callbackQuery.getMessage().getChatId().toString());
        answerMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        answerMessage.setReplyMarkup(
                Keyboard.createKeyboardWithMark(userService.getUser(callbackQuery.getMessage()).getAccuracy(), CommandAccuracy.values()));

        bot.execute(answerMessage);
    }
}
