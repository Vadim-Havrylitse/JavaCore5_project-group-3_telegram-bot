package bankApi.service;

import bankApi.models.BankName;
import bankApi.models.CashCurrency;
import bankApi.models.Currency;
import bankApi.models.PrivateBankResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

public class PrivatApiService implements BaseBankApiInterf {

    private final static String PRIVATE_URL = "";

    @Override
    public PrivateBankResponse getBankCurrency(Currency currency) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PRIVATE_URL))
                .header("Content-Type", "application/json; charset=UTF-8")
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        //request and get answer from the bank
        PrivateBankResponse response = null;

        return response;
    }

    @Override
    public CashCurrency getCurrentCurrency(Currency currency) {
        String key = BankName.PRIVAT.name() + currency;
        if (!CashService.getCashCurrencyMap().isEmpty() && CashService.getCashCurrencyMap().containsKey(key)) {
            CashCurrency lastCashCurrency = CashService.getCashCurrencyMap().get(key);
            if (lastCashCurrency != null && lastCashCurrency.getDate().equals(LocalDate.now())) {
                return lastCashCurrency;
            }
        } else {
            PrivateBankResponse bankResponse = getBankCurrency(currency);
            CashCurrency newCashCurrency = new CashCurrency();
            CashService.getCashCurrencyMap().put(newCashCurrency.getBankName().name() + newCashCurrency.getCurrency(), newCashCurrency);
            return newCashCurrency;
        }
        return null;
    }
}
