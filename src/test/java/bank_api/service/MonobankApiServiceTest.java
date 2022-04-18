package bank_api.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import bank_api.models.CashCurrency;
import bank_api.models.MonobankResponse;
import keyboard.comandsWithMark.CommandBank;
import keyboard.comandsWithMark.CommandCurrency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MonobankApiServiceTest {
    MonobankApiService monobankApiService = new MonobankApiService();

    @Test
    void getBankCurrency() {
        List<MonobankResponse> monobankBankResponseList = monobankApiService.getBankCurrency();
        System.out.println("monobankBankResponseList = " + monobankBankResponseList);
        assertFalse(monobankBankResponseList.isEmpty());
        assertEquals(840, monobankBankResponseList.get(0).getCurrencyCodeA());
        monobankBankResponseList.forEach(System.out::println);
    }

    @Test
    void getCurrentCurrency() {
        CashCurrency cashCurrency = monobankApiService.getCurrentCurrency(CommandCurrency.EUR);
        assertNotNull(cashCurrency);
        assertNotEquals(0.0, cashCurrency.getValueBuy());
        assertNotEquals(0.0, cashCurrency.getValueSale());
        assertEquals(CommandCurrency.EUR, cashCurrency.getCurrency());
        assertEquals(CommandBank.MONO, cashCurrency.getBankName());
        assertEquals(LocalDate.now(), cashCurrency.getDate());
        System.out.println("cashCurrency = " + cashCurrency);
    }
}
