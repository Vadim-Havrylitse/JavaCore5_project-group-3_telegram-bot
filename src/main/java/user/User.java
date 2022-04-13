package user;

import keyboard.comandsWithMark.CommandAccuracy;
import keyboard.comandsWithMark.CommandBank;
import keyboard.comandsWithMark.CommandCurrency;
import keyboard.comandsWithMark.CommandNotification;
import lombok.Data;

@Data
public class User {
    private final Long chatId;
    private CommandBank bank;
    private CommandAccuracy accuracy;
    private CommandCurrency currency;
    private CommandNotification notificationTime;

    {
        bank = CommandBank.PRIVAT;
        accuracy = CommandAccuracy.TWO;
        currency = CommandCurrency.USD;
        notificationTime = CommandNotification.NOTIFICATION_OFF;
    }

    public User(Long id) {
        this.chatId = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + chatId +
                ", bank=" + bank +
                ", accuracy=" + accuracy +
                ", currency=" + currency +
                ", notification=" + notificationTime +
                '}';
    }
}
