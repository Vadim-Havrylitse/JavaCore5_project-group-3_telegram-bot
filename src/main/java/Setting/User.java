package Setting;

import java.util.Objects;

public class User {


    private final Long chatId;
    private byte notificationTime;

    public User(Long chatId) {
        this.chatId = chatId;
    }

    public Long getChatId() {
        return chatId;
    }

    public byte getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(byte notificationTime) {
        this.notificationTime = notificationTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationTime);
    }


}

