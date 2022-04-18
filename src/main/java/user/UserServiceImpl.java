package user;


import keyboard.Commands;
import keyboard.comands.CommandAccuracy;
import keyboard.comands.CommandBank;
import keyboard.comands.CommandCurrency;
import keyboard.comands.CommandNotification;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserServiceImpl implements UserService{

    private static Map<Long, User> userService;

    public UserServiceImpl() {
        userService = new ConcurrentHashMap<>();
    }

    public synchronized void changeSchedule(Message message, CommandNotification time) {
        getUser(message).setNotificationTime(time);
    }

    public synchronized void changeBank(Message message, CommandBank bank) {
        List<Commands> selectedBank = getUser(message).getBank();
        if (selectedBank.contains(bank)){
            selectedBank.remove(bank);
        }else {
            selectedBank.add(bank);
        }
        getUser(message).setBank(selectedBank);
    }

    public synchronized void changeAccuracy(Message message, CommandAccuracy accuracy) {
        getUser(message).setAccuracy(accuracy);
    }

    public synchronized void changeCurrency(Message message, CommandCurrency currency) {
        List<Commands> selectedCurrency = getUser(message).getCurrency();
        if (selectedCurrency.contains(currency)){
            selectedCurrency.remove(currency);
        }else {
            selectedCurrency.add(currency);
        }
        getUser(message).setCurrency(selectedCurrency);
    }

//    public List<User> getAllUsers() {
//        return List.copyOf(userService.values());
//    }

    public User getUser(Message message) {
        return userService.get(message.getChatId());
    }

    public void addUser(Message message) {
        Long id = message.getChatId();
        userService.put(id, new User(id));
    }

    public boolean isUserPresent (Message message) {
            return  userService.containsKey(message.getChatId());
    }

}



