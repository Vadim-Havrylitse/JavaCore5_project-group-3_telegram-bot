package keyboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Getter
@AllArgsConstructor
public enum CommandSettings implements Commands {
    NUMBEROFSYNBOLS("Кол-во знаков после зяпятой",
            "/numberOfSymbols",
            "Выберите желаемое количество:"),
    BANK("Банк",
            "/bank",
            "Выберите желаемый банк:"),
    CURRENCY("Валюта",
            "/currency",
            "Выберите желаемую валюту:"),
    NOTIFICATION("Время оповещения",
            "/notification",
            "Выберите время оповещения:");

    private final String title;
    private final String callbackData;
    private final String messageAfterPressButton;

    @Override
    public SendMessage pressButton(SendMessage message, Commands[] commands) {
        message.setReplyMarkup(Keyboard.createKeyboardInline(commands));
        message.setText(this.messageAfterPressButton);
        return message;
    }
}
