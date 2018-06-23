package http.get;

import model.evotor.Document;
import model.evotor.Good;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

import java.util.List;

/**
 * Created by admin on 23.06.2018.
 */
public interface Documents {

    @GET("https://api.evotor.ru/api/v1/inventories/stores/{storeUuid}/documents")
    Call<List<Document>> getData(@Path("storeUuid") String storeUuid, @Header("X-Authorization") String authorization);
}
