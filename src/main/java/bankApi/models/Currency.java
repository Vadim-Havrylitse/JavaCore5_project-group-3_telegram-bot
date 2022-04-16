package bankApi.models;

public enum Currency {
    USD,
    EUR,
    GBP;

    public static boolean currencyExists(String currency){
        if (currency == null){
            return false;
        }
        switch (currency){
            case "USD":
            case "EUR":
            case "GBP": return  true;
            default: return false;
        }
    }
}
