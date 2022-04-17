package Setting;


import keyboard.comandsWithMark.*;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private static UserService userService;
    private static List<User> users = new ArrayList<>();
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String SOURCE = "src/main/resources/userList.json";

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

     public String getFileContent () {
         String fileContent = "";
         if (!Files.exists(Paths.get(SOURCE))) {
             try {
                 Files.createFile(Paths.get(SOURCE));
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
         try {
             fileContent = Files.lines(Paths.get(SOURCE)).collect(Collectors.joining());
         } catch (IOException e) {
             e.printStackTrace();
         }
         return fileContent;
     }

     public void writeUserIntoFile(User user) {
         String fileContent = getFileContent();
         StringBuffer newContent = new StringBuffer(fileContent);
         if (fileContent.equals("")) {
             newContent.append("[\n").
                     append(GSON.toJson(user)).
                     append("\n]");
         } else {
             newContent.deleteCharAt(newContent.length() -1).
                     append(",\n").
                     append(GSON.toJson(user)).
                     append("\n]");
         }
         try {
             Files.writeString(Paths.get(SOURCE), newContent);
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

    public List<User> getAllUsers() {
        String fileContent = getFileContent();
        if (!fileContent.equals("")) {
            users = GSON.fromJson(fileContent, new TypeToken<List<User>>() {
            }.getType());
        } else {
            User defaultUser = new User(1L);
            users.add(defaultUser);
            writeUserIntoFile(defaultUser);
        }
        return users;
    }

    public User getUser(Message message) {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getChatId() == user.getChatId()) {
                return user;
            }
        }
        return addUser(message);
    }

    public User addUser(Message message) {
        if (isUserPresent(message)) { return getUser(message);}
        User newUser = new User(message.getChatId());
        users.add(newUser);
        writeUserIntoFile(newUser);
        return newUser;
    }

    public boolean isUserPresent (Message message) {
        List<User> users = getAllUsers();
            return  users.stream().map(User::getChatId).anyMatch(id->id==message.getChatId());
    }

}



