package Setting;

import keyboard.comands.CommandMain;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import telegramService.TelegramApi;
import user.UserService;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Planner {

    private static final Map<Long, ScheduledExecutorService> schedulerPool = new ConcurrentHashMap<>();

    public static void schedulerReload(TelegramApi bot, CallbackQuery callbackQuery, UserService userService) {
        long chatId = callbackQuery.getMessage().getChatId();

        if (schedulerPool.containsKey(chatId)){
            schedulerStop(chatId);
        }

        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, Byte.parseByte(callbackQuery.getData().substring(0,2)));
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime();
        long delay = date.getTime() - System.currentTimeMillis();
        if (delay < 0) {
            delay = delay + 24 * 60 * 60 * 1000;
        }

        Runnable runnable = () -> CommandMain.GETINFO.pressButton(bot, callbackQuery, userService);

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(runnable, delay, 24 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);
        schedulerPool.put(chatId,executor);
    }

    public static void schedulerStop(long chatId){
        schedulerPool.get(chatId).shutdown();
        schedulerPool.remove(chatId);
    }
}

