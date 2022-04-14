package utils.mapper;

import bankApi.models.MononankResponse;
import bankApi.models.NBUResponseItemDTO;
import bankApi.models.PrivatBankResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonMapper {
    Gson gson = new Gson();

    public List<PrivatBankResponse> mapJsonToListPrivatBankResponse(String json){
        Type userListType = new TypeToken<ArrayList<PrivatBankResponse>>(){}.getType();
        return gson.fromJson(json, userListType);
    }

    public List<NBUResponseItemDTO> mapJsonToListNBUResponseItemDTO(String json) {
        Type typeToken = new TypeToken<ArrayList<NBUResponseItemDTO>>() {}.getType();

        return gson.fromJson(json, typeToken);
    }

    public List<MononankResponse> mapJsonToListMonobankResponse(String json){
        Type userList = new TypeToken<ArrayList<MononankResponse>>(){}.getType();

        return gson.fromJson(json,userList);
    }
}
