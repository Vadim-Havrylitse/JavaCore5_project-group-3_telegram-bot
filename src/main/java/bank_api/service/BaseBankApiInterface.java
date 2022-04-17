package bank_api.service;

import bank_api.models.CashCurrency;
import bank_api.models.Currency;
import utils.mapper.GsonMapper;

import java.net.http.HttpClient;
import java.util.List;

public interface BaseBankApiInterface<T> {

    HttpClient httpClient = HttpClient.newBuilder().build();

    GsonMapper gsonMapper = new GsonMapper();

    List<T> getBankCurrency();

    CashCurrency getCurrentCurrency(Currency currency);
}
