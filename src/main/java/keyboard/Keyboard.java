package keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import user.UserService;

import java.util.ArrayList;
import java.util.List;

public class Keyboard {


    public static InlineKeyboardMarkup createKeyboardInOneColumn(Commands[] arrCommands){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsButton = new ArrayList<>();
        for(Commands element : arrCommands){
            rowsButton.add(
                    List.of(InlineKeyboardButton.builder()
                                    .callbackData(element.getCallbackData())
                                    .text(element.getTitle())
                                    .build()));
        }
        keyboardMarkup.setKeyboard(rowsButton);
        return keyboardMarkup;
    }

    public static InlineKeyboardMarkup createKeyboardWithMark(Commands commandsFromUser, Commands[] arrCommandsForButtons){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsButton = new ArrayList<>();
        for (Commands element : arrCommandsForButtons) {
            rowsButton.add(
                    List.of(
                            InlineKeyboardButton.builder()
                                    .text(textOfTheSelectedOrUnselectedButton(commandsFromUser, element))
                                    .callbackData(element.getCallbackData())
                                    .build()));
        }
        keyboardMarkup.setKeyboard(rowsButton);
        return keyboardMarkup;
    }

    public static InlineKeyboardMarkup createKeyboardWithMark(List<Commands> commandFromUser, Commands[] arrCommandsForButtons){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsButton = new ArrayList<>();
        for (Commands element : arrCommandsForButtons) {
            rowsButton.add(
                    List.of(
                            InlineKeyboardButton.builder()
                                    .text(textOfTheSelectedOrUnselectedButton(commandFromUser, element))
                                    .callbackData(element.getCallbackData())
                                    .build()));
        }
        keyboardMarkup.setKeyboard(rowsButton);
        return keyboardMarkup;
    }


    private static String textOfTheSelectedOrUnselectedButton (List<Commands> commandsFromUser, Commands element){
        String answer;
        if (commandsFromUser.stream().anyMatch(x -> x.getTitle().equals(element.getTitle()))){
            answer = "✅ "+element.getTitle();
        } else answer = element.getTitle();
        return answer;
    }

    private static String textOfTheSelectedOrUnselectedButton (Commands commandsFromUser, Commands element){
        String answer;
        if (commandsFromUser.getTitle().equals(element.getTitle())){
            answer = "✅ "+element.getTitle();
        } else answer = element.getTitle();
        return answer;
    }

    public static InlineKeyboardMarkup createKeyboardForTimeAlert(Long chatId, UserService userService, Commands[] arrTimeAlert) {
        Commands timeAlert = userService.getUser(chatId).getNotificationTime();

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsList = new ArrayList<>();
        for (int i = 0; i < ((int) Math.ceil(arrTimeAlert.length / 3f)); i++) {
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                if ((i * 3 + j) == arrTimeAlert.length) {
                    break;
                }
                Commands command = arrTimeAlert[i * 3 + j];
                String commandTitle = command.getTitle();
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(timeAlert.equals(command) ? "✅" + commandTitle : commandTitle);
                button.setCallbackData(command.getCallbackData());
                keyboardButtonsRow.add(button);
            }
            rowsList.add(keyboardButtonsRow);
        }
        keyboardMarkup.setKeyboard(rowsList);
        return keyboardMarkup;
    }


}
