package keyboard.comandsMain;

import Setting.UserService;
import keyboard.Keyboard;
import keyboard.comandsWithMark.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Getter
@AllArgsConstructor
public enum CommandSettings implements CommandsMain {
    NUMBEROFSYNBOLS("Кол-во знаков после зяпятой",
            "NumberOfSymbols",
            "Выберите желаемое количество:"),
    BANK("Банк",
            "Bank",
            "Выберите желаемый банк:"),
    CURRENCY("Валюта",
            "Currency",
            "Выберите желаемую валюту:"),
    NOTIFICATION("Время оповещения",
            "Notification",
            "Выберите время оповещения:");

    private final String title;
    private final String callbackData;
    private final String messageAfterPressButton;

    @Override
    public SendMessage pressButton(CallbackQuery callbackQuery, UserService userService) {
        SendMessage answerMessage = new SendMessage();
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        switch (this){
            case NUMBEROFSYNBOLS:
                //userService.getUser(callbackQuery.getMessage()).getNumberOfSymbols;
                keyboardMarkup = Keyboard.createKeyboardWithMark("2", CommandNumberOfSymbols.values());
                break;
            case BANK:
                //userService.getUser(callbackQuery.getMessage()).getBank;
                keyboardMarkup = Keyboard.createKeyboardWithMark("PrivatBank", CommandBank.values());
                break;
            case CURRENCY:
                //userService.getUser(callbackQuery.getMessage()).getCurrency;
                keyboardMarkup = Keyboard.createKeyboardWithMark("USD", CommandCurrency.values());
                break;
            case NOTIFICATION:
                keyboardMarkup = Keyboard.createKeyboardForTimeAlert(callbackQuery.getMessage(), userService, CommandNotification.values());
                break;
        }
        answerMessage.setChatId(callbackQuery.getMessage().getChatId().toString());
        answerMessage.setReplyMarkup(keyboardMarkup);
        answerMessage.setText(this.messageAfterPressButton);
        return answerMessage;
    }

}
