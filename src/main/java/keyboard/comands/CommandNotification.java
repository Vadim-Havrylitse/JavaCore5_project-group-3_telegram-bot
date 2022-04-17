package keyboard.comands;

import Setting.Planner;
import keyboard.Commands;
import user.UserService;
import keyboard.Keyboard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import telegramService.TelegramApi;

@AllArgsConstructor
@Getter
public enum CommandNotification implements Commands {

    // callbackData must be parseByte()
    NINE("09:00", "09:00"),
    TEN("10:00", "10:00"),
    ELEVEN("11:00","11:00"),
    TWELVE("12:00","12:00"),
    THIRTEEN("13:00","13:00"),
    FOURTEEN("14:00","14:00"),
    FIFTEEN("15:00","15:00"),
    SIXTEEN("16:00","16:00"),
    SEVENTEEN("17:00","17:00"),
    EIGHTEEN("18:00","18:00"),
    NOTIFICATION_OFF("Turn Off", "Turn_Off");

    private final String title;
    private final String callbackData;

    @SneakyThrows
    @Override
    public void pressButton(TelegramApi bot, CallbackQuery callbackQuery, UserService userService) {

        Message message = callbackQuery.getMessage();

        if (callbackQuery.getData().equals(NOTIFICATION_OFF.callbackData)){
            userService.changeSchedule(message, NOTIFICATION_OFF);
            Planner.schedulerStop(callbackQuery.getMessage().getChatId());
        }else {
            for (CommandNotification time : CommandNotification.values()) {
                if (callbackQuery.getData().equals(time.callbackData)) {
                    userService.changeSchedule(message, time);
                    Planner.schedulerReload(bot, callbackQuery, userService);
                    break;
                }
            }
        }

        EditMessageReplyMarkup answerMessage = new EditMessageReplyMarkup();
        answerMessage.setMessageId(message.getMessageId());
        answerMessage.setChatId(message.getChatId().toString());
        answerMessage.setReplyMarkup(Keyboard.createKeyboardForTimeAlert(message, userService, CommandNotification.values()));

        bot.execute(answerMessage);
    }
}
