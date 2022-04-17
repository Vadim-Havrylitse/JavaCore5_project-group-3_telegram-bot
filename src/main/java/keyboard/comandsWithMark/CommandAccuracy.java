package keyboard.comandsWithMark;

import Setting.UserService;
import keyboard.Keyboard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@AllArgsConstructor
@Getter
public enum CommandAccuracy implements CommandsWithMark {

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

    @Override
    public EditMessageReplyMarkup pressButton(CallbackQuery callbackQuery, UserService userService) {
        EditMessageReplyMarkup answerMessage = new EditMessageReplyMarkup();

        userService.changeAccuracy(callbackQuery.getMessage(), this);

        answerMessage.setChatId(callbackQuery.getMessage().getChatId().toString());
        answerMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        answerMessage.setReplyMarkup(
                Keyboard.createKeyboardWithMark(userService.getUser(callbackQuery.getMessage()).getAccuracy(), CommandAccuracy.values()));

        return answerMessage;
    }
}
