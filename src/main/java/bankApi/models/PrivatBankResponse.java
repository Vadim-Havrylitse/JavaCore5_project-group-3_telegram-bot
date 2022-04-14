package bankApi.models;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class PrivatBankResponse implements BaseBankResponseInterface {
    private String ccy;
    private String base_ccy;
    private String buy;
    private String sale;

}
