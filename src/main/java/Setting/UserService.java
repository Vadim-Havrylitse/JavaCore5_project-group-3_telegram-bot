package Setting;

import keyboard.comandsWithMark.CommandAccuracy;
import keyboard.comandsWithMark.CommandBank;
import keyboard.comandsWithMark.CommandCurrency;
import keyboard.comandsWithMark.CommandNotification;
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
        getUser(message).setBank(bank);
    }

    public void changeAccuracy(Message message, CommandAccuracy accuracy) {
        getUser(message).setAccuracy(accuracy);
    }

    public void changeCurrency(Message message, CommandCurrency currency) {
        getUser(message).setCurrency(currency);
    }





}



