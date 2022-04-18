package bank.service.cache;

import bank.models.CurrencyInfoDTO;

import java.util.HashMap;
import java.util.Map;

public class BankCacheService {

    //Key for Map is string value bankName+currency, for example: "PrivatBank"+"EUR", the key is PrivatBankEUR
    private static Map<String, CurrencyInfoDTO> cashCurrencyMap;

    private BankCacheService() {
    }

    public static synchronized Map<String, CurrencyInfoDTO> getCashCurrencyMap() {

        if (cashCurrencyMap == null) {
            cashCurrencyMap = new HashMap<>();
        }
        return cashCurrencyMap;
    }
}
