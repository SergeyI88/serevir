package http;

import http.entity.Terminal;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface GetTerminals {

    @GET("https://api.evotor.ru/api/v1/inventories/devices/search")
    Call<List<Terminal>> get(String token);
}
