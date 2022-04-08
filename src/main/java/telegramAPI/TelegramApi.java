package telegramAPI;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utils.PropertiesLoad;

public class TelegramApi extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return PropertiesLoad.getProperty("telegramBotUsername");
    }

    @Override
    public String getBotToken() {
        return PropertiesLoad.getProperty("telegramBotToken");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                execute(new SendMessage(update.getMessage().getChatId().toString(), update.getMessage().getText()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
