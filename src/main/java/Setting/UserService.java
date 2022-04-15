package Setting;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import keyboard.comandsWithMark.CommandAccuracy;
import keyboard.comandsWithMark.CommandBank;
import keyboard.comandsWithMark.CommandCurrency;
import keyboard.comandsWithMark.CommandNotification;
import org.telegram.telegrambots.meta.api.objects.Message;
import user.User;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Collectors;

public class UserService {

    private static UserService userService;
    private static HashMap<Long, User> users = new HashMap<Long, User>();
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    public static UserService getUserService() {
        return userService;
    }

    public static UserService create() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
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

     public String getFileContent () {
         String fileContent = "";
         String src = "src/main/resources/userHashMap.json";
         if (!Files.exists(Paths.get(src))) {
             try {
                 Files.createFile(Paths.get(src));
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
         try {
             fileContent = Files.lines(Paths.get(src)).collect(Collectors.joining());
         } catch (IOException e) {
             e.printStackTrace();
         }
         return fileContent;
     }

     public void writeUserIntoFile(User user) {
         StringBuffer newContent = new StringBuffer(getFileContent());
         if (newContent.equals("")) {
             newContent.append("[\n]");
         } else {
             newContent.//deleteCharAt(newContent.length()).
                     append("[\n").
                     append(GSON.toJson(user)).
                     append("\n]");
         }
         System.out.println(newContent);
         String src = "src/main/resources/userHashMap.json";
         try {
             Files.writeString(Paths.get(src), newContent);
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

    public HashMap<Long, User> getAllUsers() {
        String fileContent = getFileContent();
        if (!fileContent.equals("")) {
            users = GSON.fromJson(fileContent, new TypeToken<HashMap<Long, User>>() {
            }.getType());
        } else {
            User defaultUser = new User(1L);
            users.put(1L, defaultUser);
            writeUserIntoFile(defaultUser);
        }
        System.out.println(users.toString());
        return users;
    }

    public User getUser(Message message) {
        HashMap<Long, User> users = getAllUsers();
        if (users.containsKey(message.getChatId())) {
            return users.get(message.getChatId());
        } else {
            return addUser(message);
        }
    }

    public User addUser(Message message) {
        if (isUserPresent(message)) { return getUser(message);}
        User newUser = new User(message.getChatId());
        users.put(message.getChatId(), newUser);
        writeUserIntoFile(newUser);
        return newUser;
    }

    public boolean isUserPresent (Message message) {
       HashMap<Long, User> usersHashMap = getAllUsers();
            return  usersHashMap.containsKey(message.getChatId());
    }

}



