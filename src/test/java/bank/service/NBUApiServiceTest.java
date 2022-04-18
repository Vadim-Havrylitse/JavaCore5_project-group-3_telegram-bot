package bank.service;

import org.junit.jupiter.api.Test;

import java.util.List;

import bank.models.CurrencyInfoDTO;
import bank.models.NBUResponseItemDTO;
import bank.service.api.NBUApiService;
import keyboard.comands.CommandCurrency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class NBUApiServiceTest {
    NBUApiService nbuApiService = new NBUApiService();

    @Test
    void getBankCurrency() {
        List<NBUResponseItemDTO> bankCurrency = nbuApiService.getBankCurrency();

        assertFalse(bankCurrency.isEmpty());
        assertInstanceOf(String.class, bankCurrency.get(0).getCc());
        assertInstanceOf(Double.class, bankCurrency.get(0).getRate());
        bankCurrency.forEach(System.out::println);
    }

    @Test
    void testGetCurrentCurrencyIsValid() {
        CurrencyInfoDTO usdCurrency = nbuApiService.getCurrentCurrency(CommandCurrency.USD);

        assertEquals(CommandCurrency.USD, usdCurrency.getCurrency());
        assertInstanceOf(Double.class, usdCurrency.getValueBuy());
    }
}