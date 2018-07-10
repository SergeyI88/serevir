package http.impl;

import http.GetTerminals;
import http.entity.Terminal;
import http.impl.GetGoodsImpl;
import org.apache.log4j.Logger;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;

public class GetTerminalsImpl {

    private static Logger logger = Logger.getLogger(GetGoodsImpl.class);

    public List<Terminal> get(String token) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://umorili.herokuapp.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetTerminals getTerminals = retrofit.create(GetTerminals.class);
        return getTerminals.get(token).execute().body();
    }
}

