package bank_api.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import bank_api.models.CashCurrency;
import bank_api.models.PrivatBankResponse;
import keyboard.comands.CommandBank;
import keyboard.comands.CommandCurrency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PrivatApiServiceTest {

    PrivatApiService privatApiService = new PrivatApiService();

    @Test
    void getBankCurrency() {
        List<PrivatBankResponse> privatBankResponseList = privatApiService.getBankCurrency();
        assertFalse(privatBankResponseList.isEmpty());
        assertEquals("UAH", privatBankResponseList.get(0).getExchangeRate().get(0).getBaseCurrency());
        privatBankResponseList.forEach(System.out::println);
    }

    @Test
    void getCurrentCurrency() {
        CashCurrency cashCurrency = privatApiService.getCurrentCurrency(CommandCurrency.GBP);
        assertNotNull(cashCurrency);
        assertNotEquals(0.0, cashCurrency.getValueBuy());
        assertNotEquals(0.0, cashCurrency.getValueSale());
        assertEquals(CommandCurrency.GBP, cashCurrency.getCurrency());
        assertEquals(CommandBank.PRIVAT, cashCurrency.getBankName());
        assertEquals(LocalDate.now(), cashCurrency.getDate());
        System.out.println("cashCurrency = " + cashCurrency);
    }
}
