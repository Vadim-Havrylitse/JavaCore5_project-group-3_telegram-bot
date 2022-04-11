package user;

public enum Bank {
    PRIVAT("PrivatBank"),
    MONO("Monobank"),
    NBU("NBU");

    private String name;

    Bank(String name) {
        this.name = name;
    }
}
