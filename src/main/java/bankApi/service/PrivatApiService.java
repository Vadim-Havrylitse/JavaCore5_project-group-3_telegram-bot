package bankApi.service;

import bankApi.models.BankName;
import bankApi.models.CashCurrency;
import bankApi.models.Currency;
import bankApi.models.PrivatBankResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrivatApiService implements BaseBankApiInterface<PrivatBankResponse> {

    private final static String PRIVATE_URL = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";

    @Override
    public List<PrivatBankResponse> getBankCurrency() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PRIVATE_URL))
                .header("Content-Type", "application/json; charset=UTF-8")
                .GET()
                .build();
        List<PrivatBankResponse> response = new ArrayList<>();
        try {
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if(httpResponse.statusCode() == 200 && !httpResponse.body().isEmpty()) {
                response = gsonMapper.mapJsonToListPrivatBankResponse(httpResponse.body());
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
        CashCurrency cashCurrency = new CashCurrency();
        cashCurrency.setCurrency(Currency.valueOf(bankResponse.getCcy()));
        cashCurrency.setDate(LocalDate.now());
        cashCurrency.setBankName(BankName.PRIVAT);
        cashCurrency.setValueBuy(Double.valueOf(bankResponse.getBuy()));
        cashCurrency.setValueSale(Double.valueOf(bankResponse.getSale()));
        CashService.getCashCurrencyMap().put(getKey(cashCurrency.getCurrency()), cashCurrency);
       }

       private String getKey(Currency currency){
        return BankName.PRIVAT.name() + currency;
       }
}
