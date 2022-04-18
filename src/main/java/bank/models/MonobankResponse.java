package bank.models;

import lombok.Data;

@Data
public class MonobankResponse implements BankResponseInterface {
    private Integer currencyCodeA;
    private Integer currencyCodeB;
    private Long date;
    private Float rateSell;
    private Float rateBuy;
    private Float rateCross;
}
