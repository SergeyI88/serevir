package controllers;

import com.google.gson.Gson;
import controllers.Good;
import controllers.GoodsPost;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import java.io.IOException;
import java.util.*;

public class SendGoods {

    public void send(List<Good> list) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://umorili.herokuapp.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Gson gson = new Gson();
        String body = gson.toJson(list);
        GoodsPost goodsPost = retrofit.create(GoodsPost.class);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("text/plain"), body);
        goodsPost.sendData("20180620-B2F2-40AA-806C-5013E03BA9B8"
                , "91888854-ca0a-4944-89ee-7b7e2cba4132"
                , "application/json"
                , requestBody).execute();
    }

}
