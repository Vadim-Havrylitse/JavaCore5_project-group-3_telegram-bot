package telegramService;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramService {

    void checkUpdateMessage (Update updateMessageTest);

    void checkUpdatePressButtons (Update callbackData);

    static TelegramService of(){
        return new TelegramServiceImpl();
    }
}
