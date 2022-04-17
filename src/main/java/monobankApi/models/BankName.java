package monobankApi.models;

public enum BankName {

    PRIVAT("PrivatBank"),
    MONO("Monobank"),
    NBU("NBU");

    private String name;

    BankName(String name) {
        this.name = name;
    }
}
