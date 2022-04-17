package bank_api.models;

import lombok.Data;

@Data
public class MonobankResponse {
    private Integer currencyCodeA;
    private Integer currencyCodeB;
    private Long date;
    private Float rateSell;
    private Float rateBuy;
    private Float rateCross;
}
