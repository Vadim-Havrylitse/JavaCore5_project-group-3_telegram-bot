package bankApi.service;

import bankApi.models.BaseBankResponse;
import bankApi.models.CashCurrency;
import bankApi.models.Currency;
import utils.mapper.GsonMapper;

import java.net.http.HttpClient;
import java.util.List;

public interface BaseBankApiInterf<T> {

    HttpClient httpClient = HttpClient.newBuilder().build();

    GsonMapper gsonMapper = new GsonMapper();

    List<T> getBankCurrency();

    CashCurrency getCurrentCurrency(Currency currency);
}
