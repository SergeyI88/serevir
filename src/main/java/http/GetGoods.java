package http;

import java.util.List;

import http.entity.Good;
import retrofit2.Call;
import retrofit2.http.*;

public interface GetGoods {

    @GET("https://api.evotor.ru/api/v1/inventories/stores/{storeUuid}/products")
    Call<List<Good>> getData(@Path("storeUuid") String storeUuid, @Header("X-Authorization") String authorization);

}
