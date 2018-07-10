package http;

import controllers.json.Document;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.LinkedHashMap;
import java.util.List;

public interface GetDocuments {

    @GET("https://api.evotor.ru/api/v1/inventories/stores/{storeUuid}/documents")
    Call<List<Document>> get(@Path("storeUuid") String storeUuid, @Header("X-Authorization") String token);
    @GET("https://api.evotor.ru/api/v1/inventories/stores/{storeUuid}/documents")
    Call<List<Document>> get(@Path("storeUuid") String storeUuid, @Header("X-Authorization") String token, @Query("ltCloseDate")String to, @Query("gtCloseDate")String from);
}
