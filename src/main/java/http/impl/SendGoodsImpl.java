package http.impl;

import com.google.gson.Gson;
import http.SendGoods;
import http.entity.Good;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.apache.log4j.Logger;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import java.io.IOException;
import java.util.*;

public class SendGoodsImpl {
    private static Logger logger = Logger.getLogger(SendGoodsImpl.class);

    public int send(List<Good> list, String storeUuid, String token) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://umorili.herokuapp.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Gson gson = new Gson();
        String body = gson.toJson(list);
        SendGoods sendGoods = retrofit.create(SendGoods.class);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("text/plain"), body);
        Response<ResponseBody> responce = sendGoods.sendData(storeUuid
                , token
                , "application/json"
                , requestBody).execute();
        logger.info(responce.message());
       return responce.code();
    }

}
