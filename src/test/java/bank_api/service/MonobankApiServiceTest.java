package bank_api.service;

import bank_api.models.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class MonobankApiServiceTest {
    MonobankApiService monobankApiService = new MonobankApiService();


    @Test
    public void getBankCurrency(){
        List<MononankResponse> monobankBankResponseList = monobankApiService.getBankCurrency();
        System.out.println("monobankBankResponseList = " + monobankBankResponseList);
        assertFalse(monobankBankResponseList.isEmpty());
        assertEquals(840, monobankBankResponseList.get(0).getCurrencyCodeA());
        monobankBankResponseList.forEach(System.out::println);
    }

    @Test
    public void getCurrentCurrency(){
        CashCurrency cashCurrency = monobankApiService.getCurrentCurrency(Currency. EUR);
        assertNotNull(cashCurrency);
        assertNotEquals(0.0, cashCurrency.getValueBuy());
        assertNotEquals(0.0, cashCurrency.getValueSale());
        assertEquals(Currency.EUR, cashCurrency.getCurrency());
        assertEquals(BankName.MONO, cashCurrency.getBankName());
        assertEquals(LocalDate.now(), cashCurrency.getDate());
        System.out.println("cashCurrency = " + cashCurrency);
    }
}
