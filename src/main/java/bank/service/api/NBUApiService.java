package bank.service.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import bank.models.CurrencyInfoDTO;
import bank.models.NBUResponseItemDTO;
import bank.service.cache.BankCacheService;
import keyboard.comands.CommandBank;
import keyboard.comands.CommandCurrency;

public class NBUApiService implements BankApiInterface<NBUResponseItemDTO> {
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
    public CurrencyInfoDTO getCurrentCurrency(CommandCurrency currency) {
        String key = getKey(currency);//

        if (!BankCacheService.getCashCurrencyMap().isEmpty() && BankCacheService.getCashCurrencyMap().containsKey(key)) {
            CurrencyInfoDTO lastCashCurrency = BankCacheService.getCashCurrencyMap().get(key);
            if (lastCashCurrency != null && lastCashCurrency.getDate().equals(LocalDate.now())) {
                return lastCashCurrency;
            }
        }
        List<NBUResponseItemDTO> bankResponse = getBankCurrency();
        bankResponse.forEach(this::setCurrencyToCash);

        return BankCacheService.getCashCurrencyMap().get(key);
    }

    private void setCurrencyToCash(NBUResponseItemDTO bankResponse) {
        if (isAppIncludeCurrency(bankResponse.getCc())) {
            CurrencyInfoDTO cashCurrency = new CurrencyInfoDTO();
            cashCurrency.setCurrency(CommandCurrency.valueOf(bankResponse.getCc()));
            cashCurrency.setDate(LocalDate.now());
            cashCurrency.setBankName(CommandBank.NBU);
            cashCurrency.setValueBuy(bankResponse.getRate());

            BankCacheService.getCashCurrencyMap().put(getKey(cashCurrency.getCurrency()), cashCurrency);
        }
    }

    private boolean isAppIncludeCurrency(String responseCurrency) {
        return Arrays.stream(CommandCurrency.values())
                .map(Enum::name)
                .anyMatch(Predicate.isEqual(responseCurrency));
    }

    private String getKey(CommandCurrency currency) {
        return CommandBank.NBU.name() + currency;
    }

    private boolean isResponseValid(HttpResponse<String> response) {
        return response.statusCode() >= 200
                && response.statusCode() < 300
                && !response.body().isEmpty();
    }
}
