package bank_api.models;

import java.time.LocalDate;

import keyboard.comandsWithMark.CommandBank;
import keyboard.comandsWithMark.CommandCurrency;
import lombok.Data;

@Data
public class CashCurrency {
    private CommandCurrency currency;
    private CommandBank bankName;
    private LocalDate date;
    private Double valueBuy;
    private Double valueSale;
}
