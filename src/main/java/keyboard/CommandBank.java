package keyboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Getter
@AllArgsConstructor
public enum CommandBank implements Commands{
    PRIVAT("PrivatBank",
            "/privatBank",
            "Вы выбрали ПриватБанк"),
    MONO("Monobank",
            "/monobank",
            "Вы выбрали МоноБанк"),
    NBU("NBU",
            "/nbu",
            "Вы выбрали Национальный Банк Украины");

    private final String title;
    private final String callbackData;
    private final String messageAfterPressButton;

    @Override
    public SendMessage pressButton(SendMessage message, Commands[] commands) {
        message.setText(this.messageAfterPressButton);
        return message;
    }
}
