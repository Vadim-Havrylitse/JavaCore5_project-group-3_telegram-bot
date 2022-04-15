
import bankApi.models.Currency;
import bankApi.models.MononankResponse;
import bankApi.service.BaseBankApiInterface;
import bankApi.service.MonobankApiService;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegramService.TelegramApi;

public class AppLauncher {

    public static void main(String[] args) throws TelegramApiException {
        BaseBankApiInterface<MononankResponse> bankServ = new MonobankApiService();
        System.out.println("bankServ.getCurrentCurrency(Currency.USD) = " + bankServ.getCurrentCurrency(Currency.USD));
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new TelegramApi());

    }
}
