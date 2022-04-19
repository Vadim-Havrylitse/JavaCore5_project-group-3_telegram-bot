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
            "Выберите желаемую точность в данных курса валют:"),
    BANK("Банк",
            "/bank",
            "Выберите желаемый банк:"),
    CURRENCY("Валюта",
            "/currency",
            "Выберите желаемую валюту:"),
    NOTIFICATION("Время ежедневного оповещения",
            "/notification",
            "В заданое время я каждый день буду присылать Вам актуальную информация о курсе валют.\n\n Выберите время ежедневного оповещения:");

    private final String title;
    private final String callbackData;
    private final String messageAfterPressButton;

    @SneakyThrows
    @Override
    public void pressButton(TelegramApi bot, CallbackQuery callbackQuery, UserService userService) {
        Long chatId = callbackQuery.getMessage().getChatId();

        SendMessage answerMessage = new SendMessage();
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        switch (this){
            case ACCURACY:
                keyboardMarkup = Keyboard.createKeyboardWithMark(userService.getUser(chatId).getAccuracy(), CommandAccuracy.values());
                break;
            case BANK:
                keyboardMarkup = Keyboard.createKeyboardWithMark(userService.getUser(chatId).getBank(), CommandBank.values());
                break;
            case CURRENCY:
                keyboardMarkup = Keyboard.createKeyboardWithMark(userService.getUser(chatId).getCurrency(), CommandCurrency.values());
                break;
            case NOTIFICATION:
                keyboardMarkup = Keyboard.createKeyboardForTimeAlert(chatId, userService, CommandNotification.values());
                break;
        }
        answerMessage.setChatId(callbackQuery.getMessage().getChatId().toString());
        answerMessage.setReplyMarkup(keyboardMarkup);
        answerMessage.setText(this.messageAfterPressButton);

        bot.execute(answerMessage);

    }

}
