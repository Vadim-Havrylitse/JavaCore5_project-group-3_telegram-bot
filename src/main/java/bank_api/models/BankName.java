package bank_api.models;

public enum BankName {

    PRIVAT("PrivatBank"),
    MONO("Monobank"),
    NBU("NBU");

    private String name;

    BankName(String name) {
        this.name = name;
    }
}
