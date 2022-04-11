package keyboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@AllArgsConstructor
@Getter
public enum CommandMain implements Commands {

    GETINFO("Получить инфо",
            "/getInfo",
            "Курс .........."),
    SETTINGS("Настройки",
            "/settings",
            "Выберите нужный вам раздел настроек:");

    private final String title;
    private final String callbackData;
    private final String messageAfterPressButton;

    public static SendMessage pressSettings(SendMessage message){
        message.setReplyMarkup(Keyboard.createKeyboardInline(CommandSettings.values()));
        message.setText(SETTINGS.messageAfterPressButton);
        return message;
    }

    //must use User in this method
    public static SendMessage pressGetInfo (SendMessage message){
        message.setText(GETINFO.messageAfterPressButton);
        message.setReplyMarkup(Keyboard.createKeyboardInline(CommandMain.values()));
        return message;
    }

    @Override
    public SendMessage pressButton(SendMessage message, Commands[] commands) {
        message.setReplyMarkup(Keyboard.createKeyboardInline(commands));
        message.setText(this.messageAfterPressButton);
        return message;
    }
}
