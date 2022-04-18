package keyboard.comands;

import keyboard.Commands;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import user.UserService;
import keyboard.Keyboard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import telegramService.TelegramApi;

@Getter
@AllArgsConstructor
public enum CommandSettings implements Commands {
    ACCURACY("Кол-во знаков после зяпятой",
            "/accuracy",
            "Выберите желаемое количество:"),
    BANK("Банк",
            "/bank",
            "Выберите желаемый банк:"),
    CURRENCY("Валюта",
            "/currency",
            "Выберите желаемую валюту:"),
    NOTIFICATION("Время оповещения",
            "/notification",
            "Выберите время оповещения:");

    private final String title;
    private final String callbackData;
    private final String messageAfterPressButton;

    @SneakyThrows
    @Override
    public void pressButton(TelegramApi bot, CallbackQuery callbackQuery, UserService userService) {
        SendMessage answerMessage = new SendMessage();
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        switch (this){
            case ACCURACY:
                //userServiceImpl.getUser(callbackQuery.getMessage()).getNumberOfSymbols;
                keyboardMarkup = Keyboard.createKeyboardWithMark(userService.getUser(callbackQuery.getMessage()).getAccuracy(), CommandAccuracy.values());
                break;
            case BANK:
                //userServiceImpl.getUser(callbackQuery.getMessage()).getBank;
                keyboardMarkup = Keyboard.createKeyboardWithMark(userService.getUser(callbackQuery.getMessage()).getBank(), CommandBank.values());
                break;
            case CURRENCY:
                //userServiceImpl.getUser(callbackQuery.getMessage()).getCurrency;
                keyboardMarkup = Keyboard.createKeyboardWithMark(userService.getUser(callbackQuery.getMessage()).getCurrency(), CommandCurrency.values());
                break;
            case NOTIFICATION:
                keyboardMarkup = Keyboard.createKeyboardForTimeAlert(callbackQuery.getMessage(), userService, CommandNotification.values());
                break;
        }
        answerMessage.setChatId(callbackQuery.getMessage().getChatId().toString());
        answerMessage.setReplyMarkup(keyboardMarkup);
        answerMessage.setText(this.messageAfterPressButton);

        bot.execute(answerMessage);

    }

}
