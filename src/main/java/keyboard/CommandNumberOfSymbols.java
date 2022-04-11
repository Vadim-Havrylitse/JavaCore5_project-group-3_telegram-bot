package keyboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@AllArgsConstructor
@Getter
public enum CommandNumberOfSymbols implements Commands{

    TWO("2",
            "/2",
            "Ок, выбрали 2"),
    THREE("3",
            "/3",
            "Ок, выбрали 3"),
    FOUR("4",
            "/4",
            "Ок, выбрали 4");

    private final String title;
    private final String callbackData;
    private final String messageAfterPressButton;

    @Override
    public SendMessage pressButton(SendMessage message, Commands[] commands) {
        return null;
    }
}
