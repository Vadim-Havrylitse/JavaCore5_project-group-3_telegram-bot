package bank_api.models;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ExchangeRate implements BaseBankResponseInterface {
    private String baseCurrency;
    private String currency;
    private Double saleRateNB;
    private Double purchaseRateNB;
    private Double saleRate;
    private Double purchaseRate;

}
