package bank.service.api;

import java.rmi.UnexpectedException;

import bank.models.BankResponseInterface;
import keyboard.comands.CommandBank;

public class BankApiFactory {
    public BankApiInterface<? extends BankResponseInterface> createBankApi(CommandBank bank) throws UnexpectedException {
        switch (bank) {
            case MONO:
                return new MonobankApiService();
            case PRIVAT:
                return new PrivatApiService();
            case NBU:
                return new NBUApiService();
            default:
                throw new UnexpectedException("Current Bank is missed");
        }
    }
}
