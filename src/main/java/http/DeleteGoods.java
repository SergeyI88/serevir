package http;

import com.google.gson.Gson;
import http.entity.Good;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.*;

public class DeleteGoods {

    public void execute(String storeUuid, String auth, Good temp) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://umorili.herokuapp.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Gson gson = new Gson();
        String body = "[ { \"uuid\": " + "\"" + temp.getUuid() + "\"" + " } ]";
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("text/plain"), body);
        DeleteGood deleteGood = retrofit.create(DeleteGood.class);
        deleteGood.delete(storeUuid, auth, "application/json", requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println("==================================================");
                System.out.println(response.isSuccessful());
                System.out.println(response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
            }
        });
    }
}
