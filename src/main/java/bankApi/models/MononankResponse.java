package bankApi.models;

import lombok.Data;

@Data
public class MononankResponse {
    private Integer currencyCodeA;
    private Integer currencyCodeB;
    private Long date;
    private Double rateSell;
    private Double rateBuy;
    private Double rateCross;
}
