package http.get;


import java.util.List;

import model.evotor.Good;
import retrofit2.Call;
import retrofit2.http.*;

public interface Goods {

    @GET("https://api.evotor.ru/api/v1/inventories/stores/{storeUuid}/products")
    Call<List<Good>> getData(@Path("storeUuid") String storeUuid, @Header("X-Authorization") String authorization);

}
