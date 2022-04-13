package bankApi.service;

import bankApi.models.CashCurrency;
import bankApi.models.Currency;
import bankApi.models.NBUResponseItemDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        CashCurrency usdCurrency = nbuApiService.getCurrentCurrency(Currency.USD);

        assertEquals(Currency.USD, usdCurrency.getCurrency());
        assertInstanceOf(Double.class, usdCurrency.getValueBuy());
    }
}