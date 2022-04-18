package bank.service.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bank.models.CurrencyInfoDTO;
import bank.models.ExchangeRate;
import bank.models.PrivatBankResponse;
import bank.service.cache.BankCacheService;
import keyboard.comands.CommandBank;
import keyboard.comands.CommandCurrency;

public class PrivatApiService implements BankApiInterface<PrivatBankResponse> {

    private static final String PRIVATE_URL_FORMAT = "https://api.privatbank.ua/p24api/exchange_rates?json&date=%s";

    @Override
    public List<PrivatBankResponse> getBankCurrency() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String currentDate = formatter.format(LocalDate.now());
        String privatUrl = String.format(PRIVATE_URL_FORMAT, currentDate);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(privatUrl))
                .header("Content-Type", "application/json; charset=UTF-8")
                .GET()
                .build();
        List<PrivatBankResponse> response = new ArrayList<>();
        try {
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (httpResponse.statusCode() == 200 && !httpResponse.body().isEmpty()) {
                response = Collections.singletonList(gsonMapper.mapJsonToListPrivatBankResponse(httpResponse.body()));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public CurrencyInfoDTO getCurrentCurrency(CommandCurrency currency) {
        String key = getKey(currency);

        if (!BankCacheService.getCashCurrencyMap().isEmpty() && BankCacheService.getCashCurrencyMap().containsKey(key)) {
            CurrencyInfoDTO lastCashCurrency = BankCacheService.getCashCurrencyMap().get(key);

            if (lastCashCurrency != null && lastCashCurrency.getDate().equals(LocalDate.now())) {
                return lastCashCurrency;
            }
        }
        List<PrivatBankResponse> bankResponse = getBankCurrency();
        bankResponse.forEach(this::setCurrencyToCash);

        return BankCacheService.getCashCurrencyMap().get(key);
    }

    private void setCurrencyToCash(PrivatBankResponse bankResponse) {

        List<ExchangeRate> exchangeRates = bankResponse.getExchangeRate();
        exchangeRates.forEach(exchangeRate -> {
            if (CommandCurrency.currencyExists(exchangeRate.getCurrency())) {
                CurrencyInfoDTO cashCurrency = new CurrencyInfoDTO();
                cashCurrency.setCurrency(CommandCurrency.valueOf(exchangeRate.getCurrency()));
                cashCurrency.setDate(LocalDate.now());
                cashCurrency.setBankName(CommandBank.PRIVAT);
                cashCurrency.setValueBuy(exchangeRate.getPurchaseRate());
                cashCurrency.setValueSale(exchangeRate.getSaleRate());
                BankCacheService.getCashCurrencyMap().put(getKey(cashCurrency.getCurrency()), cashCurrency);
            }
        });
    }

    private String getKey(CommandCurrency currency) {
        return CommandBank.PRIVAT.name() + currency;
    }
}
