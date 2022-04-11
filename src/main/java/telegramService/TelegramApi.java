package telegramService;

import keyboard.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utils.PropertiesLoad;

import java.util.HashMap;
import java.util.Map;

public class TelegramApi extends TelegramLongPollingBot {

    public static Map<Commands, Commands[]> commandsBase = new HashMap<>();

    static {
        commandsBase.put(CommandMain.GETINFO, CommandMain.values());
        commandsBase.put(CommandMain.SETTINGS, CommandSettings.values());
        commandsBase.put(CommandSettings.BANK, CommandBank.values());
        commandsBase.put(CommandSettings.CURRENCY, CommandCurrency.values());
        commandsBase.put(CommandSettings.NOTIFICATION, CommandNumberOfSymbols.values());
        commandsBase.put(CommandSettings.NUMBEROFSYNBOLS, CommandMain.values());
        commandsBase.put(CommandBank.MONO, null);
        commandsBase.put(CommandBank.PRIVAT, null);
        commandsBase.put(CommandBank.NBU, null);
        commandsBase.put(CommandCurrency.BTC, null);
        commandsBase.put(CommandCurrency.EUR, null);
        commandsBase.put(CommandCurrency.USD, null);
    }

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
            SendMessage message = new SendMessage();
                message.setText(update.getMessage().getText());
                message.setChatId(update.getMessage().getChatId().toString());
                message.setReplyMarkup(Keyboard.createKeyboardInline(TelegramApi.commandsBase.get(CommandMain.GETINFO)));
                message.enableMarkdown(true);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        if (update.hasCallbackQuery()){
            String updateCallbackData = update.getCallbackQuery().getData();
            SendMessage message = new SendMessage();
            System.out.println(update.getCallbackQuery().toString());
            for (Commands el : TelegramApi.commandsBase.keySet()) {
                if (el.getCallbackData().equals(updateCallbackData)) {
                    message = el.pressButton(message, TelegramApi.commandsBase.get(el));
                    break;
                }
            }
            message.setChatId(update.getCallbackQuery().getFrom().getId().toString());
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }
}
