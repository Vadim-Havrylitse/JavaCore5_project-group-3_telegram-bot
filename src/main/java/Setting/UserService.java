package Setting;

import keyboard.comandsWithMark.CommandAccuracy;
import keyboard.comandsWithMark.CommandBank;
import keyboard.comandsWithMark.CommandCurrency;
import keyboard.comandsWithMark.CommandNotification;
import org.telegram.telegrambots.meta.api.objects.Message;
import user.User;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public boolean isUserPresent (Message message) throws IOException {
    // вытащили из файла строку

        String src = "g:/IdeaProjects/test/src/main/java/userList.json";

        String result = Files.lines(Paths.get(src)).collect(Collectors.joining());

    //из него сделали лист юзеров usersList


        List<User> usersList = new ArrayList<>();
        boolean result = usersList.stream().
                anyMatch(user-> user.getChatId().equals(message.getChatId()));
        return result;
    }



}



