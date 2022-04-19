package user;

import keyboard.comands.CommandAccuracy;
import keyboard.comands.CommandBank;
import keyboard.comands.CommandCurrency;
import keyboard.comands.CommandNotification;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface UserService {

   static UserService of(){
        return new UserServiceImpl();
    }

    void changeSchedule(Long chatId, CommandNotification time);

    void changeBank(Long chatId, CommandBank bank);

    void changeAccuracy(Long chatId, CommandAccuracy accuracy);

    void changeCurrency(Long chatId, CommandCurrency currency);

    User getUser(Long chatId);

    void addUser(Long chatId);

    boolean isUserPresent (Long chatId);
}
