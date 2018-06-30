package http;

import com.google.gson.Gson;
import http.entity.Good;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import java.io.IOException;
import java.util.*;

public class SendGoods {

    public int send(List<Good> list, String con, String token) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://umorili.herokuapp.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Gson gson = new Gson();
        System.out.println();
        System.out.println();
        System.out.println(token);
        System.out.println(con);
        System.out.println();
        System.out.println();
        String body = gson.toJson(list);
        SendGoodsI sendGoodsI = retrofit.create(SendGoodsI.class);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("text/plain"), body);
        Response<ResponseBody> responce = sendGoodsI.sendData(con
                , token
                , "application/json"
                , requestBody).execute();
       return responce.code();
    }

}
