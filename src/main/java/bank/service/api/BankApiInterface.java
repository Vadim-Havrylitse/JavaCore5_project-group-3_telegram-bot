package bank.service.api;

import java.net.http.HttpClient;
import java.util.List;

import bank.models.CurrencyInfoDTO;
import keyboard.comands.CommandCurrency;
import utils.mapper.GsonMapper;

public interface BankApiInterface<T> {

    HttpClient httpClient = HttpClient.newBuilder().build();

    GsonMapper gsonMapper = new GsonMapper();

    List<T> getBankCurrency();

    CurrencyInfoDTO getCurrentCurrency(CommandCurrency currency);
}
