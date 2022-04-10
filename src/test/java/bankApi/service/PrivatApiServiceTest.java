package bankApi.service;


import bankApi.models.BankName;
import bankApi.models.CashCurrency;
import bankApi.models.Currency;
import bankApi.models.PrivatBankResponse;
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
        assertEquals("UAH", privatBankResponseList.get(0).getBase_ccy());
        privatBankResponseList.forEach(System.out::println);
    }
    
    @Test
    public void getCurrentCurrency(){
        CashCurrency cashCurrency = privatApiService.getCurrentCurrency(Currency.EUR);
        assertNotNull(cashCurrency);
        assertNotEquals(0.0, cashCurrency.getValueBuy());
        assertNotEquals(0.0, cashCurrency.getValueSale());
        assertEquals(Currency.EUR, cashCurrency.getCurrency());
        assertEquals(BankName.PRIVAT, cashCurrency.getBankName());
        assertEquals(LocalDate.now(), cashCurrency.getDate());
        System.out.println("cashCurrency = " + cashCurrency);
    }
}
