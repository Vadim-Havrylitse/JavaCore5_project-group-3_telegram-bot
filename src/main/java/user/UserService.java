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

    void changeSchedule(Message message, CommandNotification time);

    void changeBank(Message message, CommandBank bank);

    void changeAccuracy(Message message, CommandAccuracy accuracy);

    void changeCurrency(Message message, CommandCurrency currency);

    User getUser(Message message);

    void addUser(Message message);

    boolean isUserPresent (Message message);
}
