package user;


import keyboard.Commands;
import keyboard.comands.CommandAccuracy;
import keyboard.comands.CommandBank;
import keyboard.comands.CommandCurrency;
import keyboard.comands.CommandNotification;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserServiceImpl implements UserService{

    private static Map<Long, User> userService;

    public UserServiceImpl() {
        userService = new ConcurrentHashMap<>();
    }

    public synchronized void changeSchedule(Long chatId, CommandNotification time) {
        getUser(chatId).setNotificationTime(time);
    }

    public synchronized void changeBank(Long chatId, CommandBank bank) {
        List<Commands> selectedBank = getUser(chatId).getBank();
        if (selectedBank.contains(bank)){
            selectedBank.remove(bank);
        }else {
            selectedBank.add(bank);
        }
        getUser(chatId).setBank(selectedBank);
    }

    public synchronized void changeAccuracy(Long chatId, CommandAccuracy accuracy) {
        getUser(chatId).setAccuracy(accuracy);
    }

    public synchronized void changeCurrency(Long chatId, CommandCurrency currency) {
        List<Commands> selectedCurrency = getUser(chatId).getCurrency();
        if (selectedCurrency.contains(currency)){
            selectedCurrency.remove(currency);
        }else {
            selectedCurrency.add(currency);
        }
        getUser(chatId).setCurrency(selectedCurrency);
    }

    public User getUser(Long chatId) {
        return userService.get(chatId);
    }

    public void addUser(Long chatId) {
        userService.put(chatId, new User(chatId));
    }

    public boolean isUserPresent (Long chatId) {
            return  userService.containsKey(chatId);
    }

}



