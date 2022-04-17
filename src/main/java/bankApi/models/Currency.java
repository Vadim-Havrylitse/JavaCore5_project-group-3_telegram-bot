package bankApi.models;

import lombok.Getter;

@Getter
public enum Currency {
    USD("840"),
    EUR("978"),
    GBP("826");

    public static boolean currencyExists(String currency) {
        if (currency == null) {
            return false;
        }
        return switch (currency) {
            case "USD", "EUR", "GBP" -> true;
            default -> false;
        };
    }

    public final String codeISOL;

    Currency(String codeISOL) {
        this.codeISOL = codeISOL;
    }
}
