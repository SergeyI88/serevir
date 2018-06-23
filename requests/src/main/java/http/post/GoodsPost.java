package http.post;

import model.evotor.Good;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Created by admin on 23.06.2018.
 */
public interface GoodsPost {
    @POST("https://api.evotor.ru/api/v1/inventories/stores/{storeUuid}/products")
    Call<ResponseBody> sendData(@Path("storeUuid") String storeUuid
            , @Header("X-Authorization") String authorization
            , @Header("Content-Type") String content_type
            , @Body RequestBody body);

}
