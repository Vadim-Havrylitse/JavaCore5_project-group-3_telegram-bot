package bankApi.service;

import bankApi.models.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class MonobankApiServiceTest {
    MonobankApiService monobankApiService = new MonobankApiService();

    @Test
    public void getBankCurrency(){
        List<MononankResponse> privatBankResponseList = monobankApiService.getBankCurrency();
        assertFalse(privatBankResponseList.isEmpty());
        assertEquals("UAH", privatBankResponseList.get(0).getBase_ccy());
        privatBankResponseList.forEach(System.out::println);
    }

    @Test
    public void getCurrentCurrency(){
        CashCurrency cashCurrency = monobankApiService.getCurrentCurrency(Currency.EUR);
        assertNotNull(cashCurrency);
        assertNotEquals(0.0, cashCurrency.getValueBuy());
        assertNotEquals(0.0, cashCurrency.getValueSale());
        assertEquals(Currency.EUR, cashCurrency.getCurrency());
        assertEquals(BankName.PRIVAT, cashCurrency.getBankName());
        assertEquals(LocalDate.now(), cashCurrency.getDate());
        System.out.println("cashCurrency = " + cashCurrency);
    }
}
