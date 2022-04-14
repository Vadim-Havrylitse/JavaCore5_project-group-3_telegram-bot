package bankApi.service;

import bankApi.models.CashCurrency;

import java.util.HashMap;
import java.util.Map;

public class CashService {

    //Key for Map is string value bankName+currency, for example: "PrivatBank"+"EUR", the key is PrivatBankEUR
    private static Map<String, CashCurrency> cashCurrencyMap;

    private CashService() {
    }

    public static synchronized Map<String, CashCurrency> getCashCurrencyMap() {

        if (cashCurrencyMap == null) {
            cashCurrencyMap = new HashMap<>();
        }
        return cashCurrencyMap;
    }
}
