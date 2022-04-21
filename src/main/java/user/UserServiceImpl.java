package user;


import keyboard.Commands;
import keyboard.comands.CommandAccuracy;
import keyboard.comands.CommandBank;
import keyboard.comands.CommandCurrency;
import keyboard.comands.CommandNotification;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Slf4j
public class UserServiceImpl implements UserService{

    private static Map<Long, User> userService;

    public UserServiceImpl() {
        userService = new ConcurrentHashMap<>();
    }

    public synchronized void changeSchedule(Long chatId, CommandNotification time) {
        log.info("Changing schedule for {} chatId", chatId);
        getUser(chatId).setNotificationTime(time);
    }

    public synchronized void changeBank(Long chatId, CommandBank bank) {
        log.info("Changing bank for {} chatId", chatId);
        List<Commands> selectedBank = getUser(chatId).getBank();
        if (selectedBank.contains(bank)){
            selectedBank.remove(bank);
        }else {
            selectedBank.add(bank);
        }
        getUser(chatId).setBank(selectedBank);
    }

    public synchronized void changeAccuracy(Long chatId, CommandAccuracy accuracy) {
        log.info("Changing accuracy for {} chatId", chatId);
        getUser(chatId).setAccuracy(accuracy);
    }

    public synchronized void changeCurrency(Long chatId, CommandCurrency currency) {
        log.info("Changing currency for {} chatId", chatId);
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



