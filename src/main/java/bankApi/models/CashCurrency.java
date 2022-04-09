package bankApi.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CashCurrency {

    private Currency currency;
    private BankName bankName;
    private LocalDate date;
    private Double valueBuy;
    private Double valueSell;
}
