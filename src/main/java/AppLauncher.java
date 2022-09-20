
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegramService.TelegramApi;
@Slf4j
public class AppLauncher {
    @SneakyThrows
    public static void main(String[] args)  {
        log.info("Starting the application...");
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new TelegramApi());

    }
}
//package placement