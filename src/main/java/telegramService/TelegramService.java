package telegramService;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramService {
    TelegramService service = new TelegramServiceImpl();

    void checkUpdateMessage (Update updateMessageTest);

    void checkUpdatePressButtons (Update callbackData);

    static TelegramService of(){
        return service;
    }
}
