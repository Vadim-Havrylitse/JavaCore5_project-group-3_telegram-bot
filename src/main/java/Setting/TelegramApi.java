package Setting;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import Setting.Planner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import utils.PropertiesLoad;

public class TelegramApi extends TelegramLongPollingBot {
    private final Buttons keyboards = new Buttons();
    private final UserService userService = UserService.create();


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
        if (update.hasCallbackQuery()) {
            handleCallback(update.getCallbackQuery());
        } else if (update.hasMessage()) {
            try {
                handleMessage(update.getMessage());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleCallback(CallbackQuery callbackQuery) {            //ответ на вызов кнопки
        Message message = callbackQuery.getMessage();
        try {
            if (callbackQuery.getData().equals("schedule")) {
                execute(SendMessage.builder()
                        .text("Choose notify time")
                        .chatId(message.getChatId().toString())
                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(keyboards.getTimeAlert(message)).build())
                        .build());
            }

            List<String> times = new ArrayList<>(); //лист на оповещения
            times.add("09:00");
            times.add("10:00");
            times.add("11:00");
            times.add("12:00");
            times.add("13:00");
            times.add("14:00");
            times.add("15:00");
            times.add("16:00");
            times.add("17:00");
            times.add("18:00");
            for (String time : times) {
                if (callbackQuery.getData().equals(time)) {
                    userService.changeSchedule(message, Byte.parseByte(time.substring(0, 2)));
                    execute(EditMessageReplyMarkup.builder()
                            .chatId(message.getChatId().toString())
                            .messageId(message.getMessageId())
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(keyboards.getTimeAlert(message)).build())
                            .build());
                }
            }
            if (callbackQuery.getData().equals("0")) { // точка входа
                userService.changeSchedule(message, (byte) 0);
                execute(EditMessageReplyMarkup.builder()
                        .chatId(message.getChatId().toString())
                        .messageId(message.getMessageId())
                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(keyboards.getTimeAlert(message)).build())
                        .build());
            }
            if (callbackQuery.getData().equals("/start")) { //возвращение домой
                Planner scheduler = new Planner();
                scheduler.schedulerStart();
                execute(SendMessage.builder()
                        .text("Test Test Test Test Test Test Test")
                        .chatId(message.getChatId().toString())
                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(keyboards.getMainMenu()).build())
                        .build());
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void handleMessage(Message message) throws TelegramApiException {
        //стартуем!
        if (message.hasText() && message.hasEntities()) {
            Optional<MessageEntity> commandEntity = message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
            if (commandEntity.isPresent()) {
                String command = message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
                if (command.equals("/start")) {
                    userService.addUser(message);
                    execute(SendMessage.builder()
                            .text("Test Test Test Test Test Test Test")
                            .chatId(message.getChatId().toString())
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(keyboards.getMainMenu()).build())
                            .build());


                }
            }
        }
    }


}
