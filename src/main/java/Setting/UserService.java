package Setting;

import keyboard.comandsWithMark.CommandNotification;
import org.telegram.telegrambots.meta.api.objects.Message;


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
}



