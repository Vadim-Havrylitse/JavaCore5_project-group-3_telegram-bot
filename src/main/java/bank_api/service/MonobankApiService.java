package bank_api.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import bank_api.models.BankName;
import bank_api.models.CashCurrency;
import bank_api.models.Currency;
import bank_api.models.MonobankResponse;

public class MonobankApiService implements BaseBankApiInterface<MonobankResponse> {
    private static final String MONO_URL = "https://api.monobank.ua/bank/currency";

    @Override
    public List<MonobankResponse> getBankCurrency() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(MONO_URL))
                .header("Content-Type", "application/json; charset=UTF-8")
                .GET()
                .build();
        List<MonobankResponse> response = new ArrayList<>();

        try {
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() == 200 && !httpResponse.body().isEmpty()) {
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
        List<MonobankResponse> bankResponse = getBankCurrency();
        bankResponse.forEach(this::setCurrencyToCash);
        return CashService.getCashCurrencyMap().get(key);
    }

    private void setCurrencyToCash(MonobankResponse bankResponse) {
        // нужно проверять есть ли получаемая валюта от банка среди енама наших валют
        String bankResponseCodeA = bankResponse.getCurrencyCodeA().toString();
        Optional<Currency> currency = Arrays.stream(Currency.values()).filter(x -> x.codeISOL.equals(bankResponseCodeA)).findFirst();
        // нужно также проверять чтобы только к гривне были все результаты
        if (currency.isPresent() && bankResponse.getCurrencyCodeB().equals(Integer.valueOf("980"))) {
            CashCurrency cashCurrency = new CashCurrency();
            cashCurrency.setCurrency(currency.get());
            cashCurrency.setDate(LocalDate.now());
            cashCurrency.setBankName(BankName.MONO);
            // нужно предусмотреть что у банка нужные нам валюты могут быть, но продажа/покупка заморожена и приходит значение null
            cashCurrency.setValueBuy(bankResponse.getRateBuy() != null ? bankResponse.getRateBuy() : Double.valueOf(0));
            cashCurrency.setValueSale(bankResponse.getRateSell() != null ? bankResponse.getRateSell() : Double.valueOf(0));
            CashService.getCashCurrencyMap().put(getKey(cashCurrency.getCurrency()), cashCurrency);
        }
    }

    private String getKey(Currency currency) {
        return BankName.MONO.name() + currency.codeISOL;
    }
}
