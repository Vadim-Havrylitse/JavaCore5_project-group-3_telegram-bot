package bankApi.service;

import bankApi.models.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static bankApi.service.BaseBankApiInterface.gsonMapper;
import static bankApi.service.BaseBankApiInterface.httpClient;

public class MonobankApiService implements BaseBankApiInterface<MononankResponse> {
    private static final String MONO_URL = "https://api.monobank.ua/bank/currency";

    @Override
    public List<MononankResponse> getBankCurrency() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(MONO_URL))
                .header("Content-Type", "application/json; charset=UTF-8")
                .GET()
                .build();
        List<MononankResponse> response = new ArrayList<>();
        try {
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if(httpResponse.statusCode() == 200 && !httpResponse.body().isEmpty()) {
                response = gsonMapper.mapJsonToListMonobankResponse(httpResponse.body());
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
        List<MononankResponse> bankResponse = getBankCurrency();
        bankResponse.forEach(this::setCurrencyToCash);
        return CashService.getCashCurrencyMap().get(key);
    }

    private void setCurrencyToCash(MononankResponse bankResponse){
        CashCurrency cashCurrency = new CashCurrency();
        cashCurrency.setCurrency(Currency.valueOf(bankResponse.getCcy()));
        cashCurrency.setDate(LocalDate.now());
        cashCurrency.setBankName(BankName.MONO);
        cashCurrency.setValueBuy(Double.valueOf(bankResponse.getBuy()));
        cashCurrency.setValueSale(Double.valueOf(bankResponse.getSale()));
        CashService.getCashCurrencyMap().put(getKey(cashCurrency.getCurrency()), cashCurrency);
    }

    private String getKey(Currency currency){
        return BankName.MONO.name() + currency;
    }
}
