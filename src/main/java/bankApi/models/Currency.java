package bankApi.models;


import lombok.Getter;

@Getter
public enum Currency{

    USD("840"),
    EUR("978"),
    GBP("826");

    public final String codeISOL;

    Currency(String codeISOL){
        this.codeISOL = codeISOL;
    }



}
