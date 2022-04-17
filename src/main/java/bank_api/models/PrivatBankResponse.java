package bank_api.models;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class PrivatBankResponse implements BaseBankResponseInterface {
    private String date;
    private String bank;
    private Integer baseCurrency;
    private String baseCurrencyLit;
    private List<ExchangeRate> exchangeRate;
}
