package bank.models;

import java.time.LocalDate;

import keyboard.comands.CommandBank;
import keyboard.comands.CommandCurrency;
import lombok.Data;

@Data
public class CurrencyInfoDTO {
    private CommandCurrency currency;
    private CommandBank bankName;
    private LocalDate date;
    private Double valueBuy;
    private Double valueSale;
}
