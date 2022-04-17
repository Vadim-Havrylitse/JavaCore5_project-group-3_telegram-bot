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

@AllArgsConstructor
@Getter
public enum CommandMain implements Commands {

    GETINFO("Получить инфо",
            "/getinfo",
            "Курс .........."),
    SETTINGS("Настройки",
            "/settings",
            "Выберите нужный вам раздел настроек:");

    private final String title;
    private final String callbackData;
    private final String messageAfterPressButton;

    @SneakyThrows
    @Override
    public void pressButton(TelegramApi bot, CallbackQuery callbackQuery, UserService userService) {
        SendMessage answerMessage = new SendMessage();
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        switch (this){
            case GETINFO:
                keyboardMarkup = Keyboard.createKeyboardInOneColumn(CommandMain.values());
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

}
