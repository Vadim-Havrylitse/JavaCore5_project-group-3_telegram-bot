package bankApi.service;

import bankApi.models.BaseBankResponse;
import bankApi.models.Currency;

import java.net.http.HttpClient;

public interface BaseBankApiInterf {

    HttpClient httpClient = HttpClient.newBuilder().build();

    BaseBankResponse getCurrency(Currency currency);
}
