package Setting;

import keyboard.comandsWithMark.*;
import org.telegram.telegrambots.meta.api.objects.Message;
import user.User;


import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static UserService userService;


    public static UserService getUserService() {
        return userService;
    }

    public List<User> getUserList() {
        return userList;
    }

    private final List<User> userList;

    private UserService() {
        this.userList = new ArrayList<>();
    }

    public static UserService create() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public User getUser(Message message) {
        for (User user : userList) {
            if (user.getChatId().equals(message.getChatId())) {
                return user;
            }
        }
        return null;
    }

    public void addUser(Message message) {
        User userTemp = null;
        for (User user : userList) {
            if (user.getChatId().equals(message.getChatId())) {
                userTemp = user;
            }
        }
        if (userTemp == (null)) {
            userList.add(new User(message.getChatId()));
        }
    }

    public void changeSchedule(Message message, CommandNotification time) {
        getUser(message).setNotificationTime(time);
    }

    public void changeBank(Message message, CommandBank bank) {
        List<CommandsWithMark> selectedBank = getUser(message).getBank();
        if (selectedBank.contains(bank)){
            selectedBank.remove(bank);
        }else {
            selectedBank.add(bank);
        }
        getUser(message).setBank(selectedBank);
    }

    public void changeAccuracy(Message message, CommandAccuracy accuracy) {
        getUser(message).setAccuracy(accuracy);
    }

    public void changeCurrency(Message message, CommandCurrency currency) {
        List<CommandsWithMark> selectedCurrency = getUser(message).getCurrency();
        if (selectedCurrency.contains(currency)){
            selectedCurrency.remove(currency);
        }else {
            selectedCurrency.add(currency);
        }
        getUser(message).setCurrency(selectedCurrency);
    }





}



