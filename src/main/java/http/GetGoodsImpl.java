package http;

import com.google.gson.Gson;
import http.entity.Good;
import org.apache.log4j.Logger;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;


public class GetGoodsImpl {

    private static Logger logger = Logger.getLogger(GetGoodsImpl.class);

    public List<Good> get(String storeUuid, String token) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://umorili.herokuapp.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetGoods getGoods = retrofit.create(GetGoods.class);
        return (List<Good>) getGoods.getData(storeUuid, token);
    }
}
