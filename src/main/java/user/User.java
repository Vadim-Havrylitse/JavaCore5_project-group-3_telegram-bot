package user;

public class User {
    int id;
    Bank bank;
    Accuracy accuracy;
    Currency currency;
    Notification notification;

    {
        bank = Bank.PRIVAT;
        accuracy = Accuracy.TWO;
        currency = Currency.USD;
        notification = Notification.TWELVE;
    }

    User (int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Accuracy getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Accuracy accuracy) {
        this.accuracy = accuracy;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", bank=" + bank +
                ", accuracy=" + accuracy +
                ", currency=" + currency +
                ", notification=" + notification +
                '}';
    }
}
