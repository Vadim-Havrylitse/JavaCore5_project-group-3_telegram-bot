package keyboard.comandsMain;

import Setting.UserService;
import keyboard.Keyboard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@AllArgsConstructor
@Getter
public enum CommandMain implements CommandsMain {

    GETINFO("Получить инфо",
            "/getInfo",
            "Курс .........."),
    SETTINGS("Настройки",
            "/settings",
            "Выберите нужный вам раздел настроек:");

    private final String title;
    private final String callbackData;
    private final String messageAfterPressButton;

    @Override
    public SendMessage pressButton(CallbackQuery callbackQuery, UserService userService) {
        SendMessage answerMessage = new SendMessage();
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        switch (this){
            case GETINFO:
                keyboardMarkup = Keyboard.createKeyboardInOneColumn(CommandMain.values());
                break;
            case SETTINGS:
                keyboardMarkup = Keyboard.createKeyboardInOneColumn(CommandSettings.values());
                break;
        }
        answerMessage.setChatId(callbackQuery.getMessage().getChatId().toString());
        answerMessage.setReplyMarkup(keyboardMarkup);
        answerMessage.setText(this.messageAfterPressButton);
        return answerMessage;
    }

}
