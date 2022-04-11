package keyboard;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface Commands {

    String getTitle();
    String getCallbackData();
    String getMessageAfterPressButton();

    SendMessage pressButton(SendMessage message, Commands[] commands);

}
