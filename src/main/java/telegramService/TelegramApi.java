package telegramService;

import keyboard.Commands;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import user.UserService;
import user.UserServiceImpl;
import keyboard.Keyboard;
import keyboard.comands.CommandMain;
import keyboard.comands.CommandSettings;
import keyboard.comands.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utils.PropertiesLoad;

import java.util.ArrayList;
import java.util.List;

public class TelegramApi extends TelegramLongPollingBot {

    private final UserService userService = UserService.of();

    public static List<Commands> commandsBase = new ArrayList<>();

    static {
        commandsBase.addAll(List.of(CommandMain.values()));
        commandsBase.addAll(List.of(CommandSettings.values()));
        commandsBase.addAll(List.of(CommandBank.values()));
        commandsBase.addAll(List.of(CommandCurrency.values()));
        commandsBase.addAll(List.of(CommandNotification.values()));
        commandsBase.addAll(List.of(CommandAccuracy.values()));

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
            if (!userService.isUserPresent(update.getMessage())){
                    userService.addUser(update.getMessage());
                }

            for (Commands el : commandsBase){
                if (el.getCallbackData().equals(update.getMessage().getText())){
                    CallbackQuery myCallbackQuery = new CallbackQuery();
                    myCallbackQuery.setData(update.getMessage().getText());
                    myCallbackQuery.setMessage(update.getMessage());
                    el.pressButton(this,myCallbackQuery, userService);
                    return;
                }
            }

            if (update.getMessage().getText().equals("/start")){
                message.setText("Добро пожаловать! Этот бот предназначен для информирования Вас о курсе нужных валют от нужных вам банков.");
            }else {
                message.setText("Для работы со мной пользуйтесь клавиатурой под сообщениями.");
            }
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

            for (Commands el : commandsBase){
                if (el.getCallbackData().equals(updateCallbackData)){
                    el.pressButton(this, update.getCallbackQuery(), userService);
                    break;
                }
            }
        }
    }
}
