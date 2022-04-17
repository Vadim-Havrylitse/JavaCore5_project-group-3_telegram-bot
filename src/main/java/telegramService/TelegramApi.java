package telegramService;

import Setting.UserService;
import keyboard.Keyboard;
import keyboard.comandsMain.CommandMain;
import keyboard.comandsMain.CommandSettings;
import keyboard.comandsMain.CommandsMain;
import keyboard.comandsWithMark.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utils.PropertiesLoad;

import java.util.ArrayList;
import java.util.List;

public class TelegramApi extends TelegramLongPollingBot {

    private final UserService userService = UserService.create();

    public static List<CommandsMain> commandsMainBase = new ArrayList<>();
    public static List<CommandsWithMark> commandsWithMarkBase = new ArrayList<>();
    static {
        commandsMainBase.addAll(List.of(CommandMain.values()));
        commandsMainBase.addAll(List.of(CommandSettings.values()));

        commandsWithMarkBase.addAll(List.of(CommandBank.values()));
        commandsWithMarkBase.addAll(List.of(CommandCurrency.values()));
        commandsWithMarkBase.addAll(List.of(CommandNotification.values()));
        commandsWithMarkBase.addAll(List.of(CommandAccuracy.values()));

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
            if (!userService.isUserPresent(update.getMessage())){
                System.out.println("isUser = false");
                userService.addUser(update.getMessage());
            }
            SendMessage message = new SendMessage();
                message.setText(update.getMessage().getText());
                message.setChatId(update.getMessage().getChatId().toString());
                message.setReplyMarkup(Keyboard.createKeyboardInOneColumn(CommandMain.values()));
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        if (update.hasCallbackQuery()) {
            String updateCallbackData = update.getCallbackQuery().getData();

            for (CommandsMain el : commandsMainBase) {
                if (el.getCallbackData().equals(updateCallbackData)) {
                    try {
                        execute(el.pressButton(update.getCallbackQuery(), userService));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }

            for (CommandsWithMark el : commandsWithMarkBase){
                if (el.getCallbackData().equals(updateCallbackData)){
                    try {
                        execute(el.pressButton(update.getCallbackQuery(), userService));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
