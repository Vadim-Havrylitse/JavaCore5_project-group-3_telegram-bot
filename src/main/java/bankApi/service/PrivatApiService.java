package bankApi.service;

import bankApi.models.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PrivatApiService implements BaseBankApiInterface<PrivatBankResponse> {

    private final static String PRIVATE_URL_FORMAT = "https://api.privatbank.ua/p24api/exchange_rates?json&date=%s";

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
            if(httpResponse.statusCode() == 200 && !httpResponse.body().isEmpty()) {
                response = Collections.singletonList(gsonMapper.mapJsonToListPrivatBankResponse(httpResponse.body()));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return response;
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
            List<PrivatBankResponse> bankResponse = getBankCurrency();
            bankResponse.forEach(this::setCurrencyToCash);
            return CashService.getCashCurrencyMap().get(key);
       }

       private void setCurrencyToCash(PrivatBankResponse bankResponse){

        List<ExchangeRate> exchangeRates = bankResponse.getExchangeRate();
        exchangeRates.forEach(exchangeRate -> {
            if (Currency.currencyExists(exchangeRate.getCurrency())){
                CashCurrency cashCurrency = new CashCurrency();
                cashCurrency.setCurrency(Currency.valueOf(exchangeRate.getCurrency()));
                cashCurrency.setDate(LocalDate.now());
                cashCurrency.setBankName(BankName.PRIVAT);
                cashCurrency.setValueBuy(exchangeRate.getPurchaseRate());
                cashCurrency.setValueSale(exchangeRate.getSaleRate());
                CashService.getCashCurrencyMap().put(getKey(cashCurrency.getCurrency()), cashCurrency);
            }
        });
       }

       private String getKey(Currency currency){
        return BankName.PRIVAT.name() + currency;
       }
}
