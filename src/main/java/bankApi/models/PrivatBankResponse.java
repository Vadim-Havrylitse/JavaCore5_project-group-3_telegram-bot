package bankApi.models;

import java.util.List;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class PrivatBankResponse implements BaseBankResponseInterface {
    private String date;
    private String bank;
    private Integer baseCurrency;
    private String baseCurrencyLit;
    private List<ExchangeRate> exchangeRate;
}
