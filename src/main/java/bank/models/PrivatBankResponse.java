package bank.models;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class PrivatBankResponse implements BankResponseInterface {
    private String date;
    private String bank;
    private Integer baseCurrency;
    private String baseCurrencyLit;
    private List<ExchangeRate> exchangeRate;
}
