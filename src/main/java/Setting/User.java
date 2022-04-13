package Setting;

import keyboard.comandsWithMark.CommandNotification;

import java.util.Objects;

public class User {


    private final Long chatId;
    private CommandNotification notificationTime;

    public User(Long chatId) {
        this.chatId = chatId;
        this.notificationTime = CommandNotification.NOTIFICATION_OFF;
    }

    public Long getChatId() {
        return chatId;
    }

    public CommandNotification getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(CommandNotification notificationTime) {
        this.notificationTime = notificationTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationTime);
    }


}

