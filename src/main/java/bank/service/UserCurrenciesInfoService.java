package bank.service;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bank.models.BankResponseInterface;
import bank.models.CurrencyInfoDTO;
import bank.service.api.BankApiFactory;
import bank.service.api.BankApiInterface;
import bank.service.information_message.BankInformationMessageBuilder;
import keyboard.Commands;
import keyboard.comands.CommandBank;
import keyboard.comands.CommandCurrency;
import lombok.extern.slf4j.Slf4j;
import user.User;

@Slf4j
public class UserCurrenciesInfoService {
    private final BankApiFactory bankApiFactory = new BankApiFactory();
    private final User user;

    public UserCurrenciesInfoService(User user)
    {
        this.user = user;
    }

    public String getInformation() {
        log.info("Getting information");
        Map<CommandBank, List<CurrencyInfoDTO>> usersCurrenciesMap = getUsersCurrenciesMapByBanks();

        return buildInformationMessage(usersCurrenciesMap);
    }

    private Map<CommandBank, List<CurrencyInfoDTO>> getUsersCurrenciesMapByBanks() {
        Map<CommandBank, List<CurrencyInfoDTO>> currenciesMapByBank = new HashMap<>();

        for (Commands userBank : user.getBank()) {
            try {
                BankApiInterface<? extends BankResponseInterface> bankApi = bankApiFactory.createBankApi((CommandBank) userBank);

                currenciesMapByBank.put(
                        (CommandBank) userBank,
                        getUsersCurrenciesInfoListFrom(bankApi)
                );
            } catch (UnexpectedException e) {
                throw new RuntimeException(e);
            }
        }

        return currenciesMapByBank;
    }

    private List<CurrencyInfoDTO> getUsersCurrenciesInfoListFrom(
            BankApiInterface<? extends BankResponseInterface> bankApi
    ) {
        List<CurrencyInfoDTO> result = new ArrayList<>();

        for (Commands currency : user.getCurrency()) {
            CurrencyInfoDTO currencyInfo = bankApi.getCurrentCurrency((CommandCurrency) currency);
            result.add(currencyInfo);
        }

        return result;
    }

    private String buildInformationMessage(Map<CommandBank, List<CurrencyInfoDTO>> usersCurrenciesMap) {
        return new BankInformationMessageBuilder(usersCurrenciesMap, user).build();
    }
}
