package Setting;

import keyboard.comandsWithMark.CommandNotification;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegramService.TelegramApi;
import user.User;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Planner {
    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10);

    public void schedulerStart(TelegramApi telegramApi) {

        Calendar calendar = new GregorianCalendar();

        for (User user :
                UserService.getUserService().getAllUsers().values()) {
            if (user.getNotificationTime() != CommandNotification.NOTIFICATION_OFF) {
                calendar.set(Calendar.HOUR_OF_DAY, Byte.parseByte(user.getNotificationTime().getCallbackData().substring(0,2)));
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                Date date = calendar.getTime();
                long delay = date.getTime() - System.currentTimeMillis();
                if (delay < 0) {
                    delay = delay + 24 * 60 * 60 * 1000;
                }

                Runnable runnable = () -> {
                    try {
                        telegramApi.execute(SendMessage.builder()
                                .chatId(user.getChatId().toString())
                                .build());
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                };
                executor.scheduleAtFixedRate(runnable, delay, 24 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);
            }
        }
    }
}

