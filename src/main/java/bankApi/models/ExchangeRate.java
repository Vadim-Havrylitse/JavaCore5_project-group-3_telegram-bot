package bankApi.models;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ExchangeRate implements BaseBankResponse {
    private String baseCurrency;
    private String currency;
    private Double saleRateNB;
    private Double purchaseRateNB;
    private Double saleRate;
    private Double purchaseRate;

}
