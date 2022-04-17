package monobankApi.models;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class MonoBankResponse implements BaseBankResponse{
    private String ccy;
    private String base_ccy;
    private String buy;
    private String sale;
}
