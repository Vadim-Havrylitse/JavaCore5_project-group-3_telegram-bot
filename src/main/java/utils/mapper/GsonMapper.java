package utils.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import bank.models.MonobankResponse;
import bank.models.NBUResponseItemDTO;
import bank.models.PrivatBankResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GsonMapper {
    Gson gson = new Gson();

    public PrivatBankResponse mapJsonToListPrivatBankResponse(String json) {
        log.info("Creating PrivatBankResponse from json");
        return gson.fromJson(json, PrivatBankResponse.class);
    }

    public List<NBUResponseItemDTO> mapJsonToListNBUResponseItemDTO(String json) {
        log.info("Creating List NBUResponseItemDTO from json");
        Type typeToken = new TypeToken<ArrayList<NBUResponseItemDTO>>() {
        }.getType();

        return gson.fromJson(json, typeToken);
    }

    public List<MonobankResponse> mapJsonToListMonobankResponse(String json) {
        log.info("Creating List MonobankResponse from json");
        Type userList = new TypeToken<ArrayList<MonobankResponse>>() {
        }.getType();

        return gson.fromJson(json, userList);
    }
}
