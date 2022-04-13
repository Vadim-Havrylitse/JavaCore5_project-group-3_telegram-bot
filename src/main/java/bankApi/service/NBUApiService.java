package bankApi.service;

import bankApi.models.BankName;
import bankApi.models.CashCurrency;
import bankApi.models.Currency;
import bankApi.models.NBUResponseItemDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class NBUApiService implements BaseBankApiInterface<NBUResponseItemDTO> {
    private static final String API_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

    @Override
    public List<NBUResponseItemDTO> getBankCurrency() {
        HttpRequest request = HttpRequest.newBuilder(URI.create(API_URL))
                .GET()
                .header("Content-Type", "application/json; charset=UTF-8")
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (isResponseValid(response)) {
                return gsonMapper.mapJsonToListNBUResponseItemDTO(response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    @Override
    public CashCurrency getCurrentCurrency(Currency currency) {
        String key = getKey(currency);

        if (!CashService.getCashCurrencyMap().isEmpty() && CashService.getCashCurrencyMap().containsKey(key)) {
            CashCurrency lastCashCurrency = CashService.getCashCurrencyMap().get(key);
            if (lastCashCurrency != null && lastCashCurrency.getDate().equals(LocalDate.now())) {
                return lastCashCurrency;
            }
        }
        List<NBUResponseItemDTO> bankResponse = getBankCurrency();
        bankResponse.forEach(this::setCurrencyToCash);

        return CashService.getCashCurrencyMap().get(key);
    }

    private void setCurrencyToCash(NBUResponseItemDTO bankResponse) {
        if (isAppIncludeCurrency(bankResponse.getCc())) {
            CashCurrency cashCurrency = new CashCurrency();
            cashCurrency.setCurrency(Currency.valueOf(bankResponse.getCc()));
            cashCurrency.setDate(LocalDate.now());
            cashCurrency.setBankName(BankName.NBU);
            cashCurrency.setValueBuy(Double.valueOf(bankResponse.getRate()));

            CashService.getCashCurrencyMap().put(getKey(cashCurrency.getCurrency()), cashCurrency);
        }
    }

    private boolean isAppIncludeCurrency(String responseCurrency) {
        return Arrays.stream(Currency.values())
                .map(Enum::name)
                .anyMatch(Predicate.isEqual(responseCurrency));
    }

    private String getKey(Currency currency) {
        return BankName.PRIVAT.name() + currency;
    }

    private boolean isResponseValid(HttpResponse<String> response) {
        return response.statusCode() >= 200
                && response.statusCode() < 300
                && !response.body().isEmpty();
    }
}