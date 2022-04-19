package bank.service.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import bank.models.CurrencyInfoDTO;
import bank.models.MonobankResponse;
import bank.service.cache.BankCacheService;
import keyboard.comands.CommandBank;
import keyboard.comands.CommandCurrency;

public class MonobankApiService implements BankApiInterface<MonobankResponse> {
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
    public CurrencyInfoDTO getCurrentCurrency(CommandCurrency currency) {
        String key = getKey(currency);

        if (!BankCacheService.getCashCurrencyMap().isEmpty() && BankCacheService.getCashCurrencyMap().containsKey(key)) {
            CurrencyInfoDTO lastCashCurrency = BankCacheService.getCashCurrencyMap().get(key);
            if (lastCashCurrency != null && lastCashCurrency.getDate().equals(LocalDate.now())) {
                return lastCashCurrency;
            }
        }
        List<MonobankResponse> bankResponse = getBankCurrency();
        bankResponse.forEach(this::setCurrencyToCash);

        return BankCacheService.getCashCurrencyMap().get(key);
    }

    private void setCurrencyToCash(MonobankResponse bankResponse) {
        // you need to check if the received currency from the bank is among the enam of our currencies
        String bankResponseCodeA = bankResponse.getCurrencyCodeA().toString();
        Optional<CommandCurrency> currency = Arrays.stream(CommandCurrency.values()).filter(x -> x.getCodeISOL().equals(bankResponseCodeA)).findFirst();

        // you also need to check that all the results are only for the hryvnia
        if (currency.isPresent() && bankResponse.getCurrencyCodeB().equals(Integer.valueOf("980"))) {
            CurrencyInfoDTO cashCurrency = new CurrencyInfoDTO();
            cashCurrency.setCurrency(currency.get());
            cashCurrency.setDate(LocalDate.now());
            cashCurrency.setBankName(CommandBank.MONO);

            // we need to foresee that the bank may have the currencies we need, but the sale / purchase is frozen and the null value comes
            cashCurrency.setValueBuy(bankResponse.getRateBuy() != null ? bankResponse.getRateBuy() : Double.valueOf(0));
            cashCurrency.setValueSale(bankResponse.getRateSell() != null ? bankResponse.getRateSell() : Double.valueOf(0));
            BankCacheService.getCashCurrencyMap().put(getKey(cashCurrency.getCurrency()), cashCurrency);
        }
    }

    private String getKey(CommandCurrency currency) {
        return CommandBank.MONO.name() + currency.getCodeISOL();
    }
}
