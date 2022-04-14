package user;

import keyboard.comandsWithMark.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private final Long chatId;
    private List<CommandsWithMark> bank;
    private CommandAccuracy accuracy;
    private List<CommandsWithMark> currency;
    private CommandNotification notificationTime;

    {
        bank = new ArrayList<>(List.of(CommandBank.PRIVAT));
        accuracy = CommandAccuracy.TWO;
        currency = new ArrayList<>(List.of(CommandCurrency.USD));
        notificationTime = CommandNotification.NOTIFICATION_OFF;
    }

    public User(Long id) {
        this.chatId = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + chatId +
                ", bank=" + bank.toString() +
                ", accuracy=" + accuracy +
                ", currency=" + currency.toString() +
                ", notification=" + notificationTime +
                '}';
    }
}
