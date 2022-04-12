package Setting;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegramService.TelegramApi;

public class AppLauncher {


        private static final TelegramApi bot = new TelegramApi();

        public static TelegramApi getBot () {
            return bot;
        }

        public static void main (String[]args) throws TelegramApiException {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        }
    }


