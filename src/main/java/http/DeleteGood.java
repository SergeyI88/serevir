package http;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DeleteGood {

    @POST("https://api.evotor.ru/api/v1/inventories/stores/{storeUuid}/products/delete")
    Call<ResponseBody> delete(@Path("storeUuid") String storeUuid
            , @Header("X-Authorization") String authorization
            , @Header("Content-Type") String content_type
            , @Body RequestBody requestBody);
}
