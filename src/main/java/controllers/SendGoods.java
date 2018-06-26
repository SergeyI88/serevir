package controllers;

import com.google.gson.Gson;
import consts.Const;
import controllers.Good;
import controllers.GoodsPost;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import java.io.IOException;
import java.util.*;

public class SendGoods {

    public void send(List<Good> list, Const con) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://umorili.herokuapp.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Gson gson = new Gson();
        String body = gson.toJson(list);
        GoodsPost goodsPost = retrofit.create(GoodsPost.class);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("text/plain"), body);
        goodsPost.sendData(con.name
                , "2fabfbb4-5163-477a-b903-b0f389569a87"
                , "application/json"
                , requestBody).execute();
    }

}
