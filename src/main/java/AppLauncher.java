
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegramService.TelegramApi;

public class AppLauncher {
    @SneakyThrows
    public static void main(String[] args)  {


        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new TelegramApi());

    }
}
