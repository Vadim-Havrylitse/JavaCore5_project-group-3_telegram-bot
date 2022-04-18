package bank_api.service;

import java.net.http.HttpClient;
import java.util.List;

import bank_api.models.CashCurrency;
import keyboard.comands.CommandCurrency;
import utils.mapper.GsonMapper;

public interface BaseBankApiInterface<T> {

    HttpClient httpClient = HttpClient.newBuilder().build();

    GsonMapper gsonMapper = new GsonMapper();

    List<T> getBankCurrency();

    CashCurrency getCurrentCurrency(CommandCurrency currency);
}
