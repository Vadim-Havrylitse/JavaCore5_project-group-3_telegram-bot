package keyboard.comands;

import bank.service.UserCurrenciesInfoService;
import keyboard.Commands;

import user.User;
import user.UserService;
import keyboard.Keyboard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import telegramService.TelegramApi;

@AllArgsConstructor
@Getter
public enum CommandMain implements Commands {

    GETINFO("Получить инфо",
            "/getinfo",
            "Не удается получить курс. Попробуйте позже."),
    SETTINGS("Настройки",
            "/settings",
            "Выберите нужный вам раздел настроек:");

    private final String title;
    private final String callbackData;
    private String messageAfterPressButton;

    @SneakyThrows
    @Override
    public void pressButton(TelegramApi bot, CallbackQuery callbackQuery, UserService userService) {
        SendMessage answerMessage = new SendMessage();
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        switch (this){
            case GETINFO:
                keyboardMarkup = Keyboard.createKeyboardInOneColumn(CommandMain.values());

                answerMessage.setParseMode(ParseMode.HTML);
                messageAfterPressButton = buildGetInfoMessage(new User(callbackQuery.getMessage().getChatId()));
                break;
            case SETTINGS:
                keyboardMarkup = Keyboard.createKeyboardInOneColumn(CommandSettings.values());
                break;
        }
        answerMessage.setChatId(callbackQuery.getMessage().getChatId().toString());
        answerMessage.setReplyMarkup(keyboardMarkup);
        answerMessage.setText(this.messageAfterPressButton);

        bot.execute(answerMessage);
    }

    private String buildGetInfoMessage(User user) {
        UserCurrenciesInfoService userCurrenciesInfoService = new UserCurrenciesInfoService(user);

        return userCurrenciesInfoService.getInformation();
    }

}
