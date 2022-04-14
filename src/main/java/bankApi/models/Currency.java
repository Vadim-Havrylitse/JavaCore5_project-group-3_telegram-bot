package bankApi.models;



public enum Currency{

    USD(840),
    EUR(978),
    GBP(826);
    public final int codeISOL;
    Currency(int codeISOL){
        this.codeISOL = codeISOL;
    }



}
