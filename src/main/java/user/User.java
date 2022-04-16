package user;

import keyboard.Commands;
import keyboard.comands.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private final Long chatId;
    private List<Commands> bank;
    private CommandAccuracy accuracy;
    private List<Commands> currency;
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
