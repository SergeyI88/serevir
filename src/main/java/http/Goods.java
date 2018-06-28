package http;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import http.entity.Good;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.*;

public interface Goods {

    @GET("https://api.evotor.ru/api/v1/inventories/stores/{storeUuid}/products")
    Call<List<Good>> getData(@Path("storeUuid") String storeUuid, @Header("X-Authorization") String authorization);

    public static void main(String[] args) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://umorili.herokuapp.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Goods goods = retrofit.create(Goods.class);
        try {
            Response response = goods.getData("20180620-B2F2-40AA-806C-5013E03BA9B8"
                    , "f7bbb6d8-60a7-475a-9c8e-2c40797820dd").execute();
            List<Good> list = (List<Good>) response.body();
            list.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
