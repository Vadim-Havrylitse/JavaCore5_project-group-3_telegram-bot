package bankApi.models;

import lombok.Data;

@Data
public class MononankResponse {
    private String ccy;
    private String base_ccy;
    private String buy;
    private String sale;
}
