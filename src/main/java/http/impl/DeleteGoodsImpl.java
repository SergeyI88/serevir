package http.impl;

import http.DeleteGood;
import http.entity.Good;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.apache.log4j.Logger;
import retrofit2.*;

import java.util.List;


public class DeleteGoodsImpl {
    Logger logger = Logger.getLogger(DeleteGoodsImpl.class);

    public void execute(String storeUuid, String auth, List<Good> goods) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://umorili.herokuapp.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        StringBuilder body = new StringBuilder("[");
        if (goods != null) {
            for (int i = 0; i < goods.size() - 1; i++) {
                body.append(" { \"uuid\": " + "\"").append(goods.get(i).getUuid()).append("\"").append(" }, ");
            }
            body.append(" { \"uuid\": " + "\"").append(goods.get(goods.size() - 1).getUuid()).append("\"").append(" }] ");
        } else {
            body.append("]");
        }

        RequestBody requestBody =
                RequestBody.create(MediaType.parse("text/plain"), body.toString());
        DeleteGood deleteGood = retrofit.create(DeleteGood.class);
        deleteGood.delete(storeUuid, auth, "application/json", requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                logger.info(response.code() + " removing");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }
}
