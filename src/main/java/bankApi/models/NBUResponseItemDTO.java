package bankApi.models;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NBUResponseItemDTO {
    private Integer r030;
    private String txt;
    private Double rate;
    private String cc;
    private String exchangedate;
}
