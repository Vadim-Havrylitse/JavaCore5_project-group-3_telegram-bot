package keyboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@AllArgsConstructor
@Getter
public enum CommandCurrency implements Commands{
    USD("USD",
            "/usd",
            "Выбрали доллар"),
    EUR("EUR",
            "/eur",
            "Выбрали евро"),
    BTC("BTC",
            "/btc",
            "Выбрали биткоин");

    private final String title;
    private final String callbackData;
    private final String messageAfterPressButton;

    @Override
    public SendMessage pressButton(SendMessage message, Commands[] commands) {
        message.setText(this.messageAfterPressButton);
        return message;
    }
}
