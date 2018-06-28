package http;

import http.entity.Good;
import http.entity.ShopHttp;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

import java.util.List;

public interface Shops {
    @GET("https://api.evotor.ru/api/v1/inventories/stores/search")
    Call<List<ShopHttp>> getData(@Header("X-Authorization") String authorization);
}