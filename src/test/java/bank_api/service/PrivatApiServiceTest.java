package bank_api.service;


import bank_api.models.BankName;
import bank_api.models.CashCurrency;
import bank_api.models.Currency;
import bank_api.models.PrivatBankResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PrivatApiServiceTest {

    PrivatApiService privatApiService = new PrivatApiService();

    @Test
    public void getBankCurrency(){
       List<PrivatBankResponse> privatBankResponseList = privatApiService.getBankCurrency();
        assertFalse(privatBankResponseList.isEmpty());
        assertEquals("UAH", privatBankResponseList.get(0).getExchangeRate().get(0).getBaseCurrency());
        privatBankResponseList.forEach(System.out::println);
    }
    
    @Test
    public void getCurrentCurrency(){
        CashCurrency cashCurrency = privatApiService.getCurrentCurrency(Currency.GBP);
        assertNotNull(cashCurrency);
        assertNotEquals(0.0, cashCurrency.getValueBuy());
        assertNotEquals(0.0, cashCurrency.getValueSale());
        assertEquals(Currency.GBP, cashCurrency.getCurrency());
        assertEquals(BankName.PRIVAT, cashCurrency.getBankName());
        assertEquals(LocalDate.now(), cashCurrency.getDate());
        System.out.println("cashCurrency = " + cashCurrency);
    }
}
