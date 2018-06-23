import com.google.gson.Gson;
import http.get.Documents;
import http.get.Goods;
import http.post.GoodsPost;
import model.evotor.Document;
import model.evotor.Good;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://umorili.herokuapp.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(ScalarsConverterFactory.create())//Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        Goods goods = retrofit.create(Goods.class);
        Documents documents = retrofit.create(Documents.class);
        Response response = goods.getData("20180620-B2F2-40AA-806C-5013E03BA9B8", "91888854-ca0a-4944-89ee-7b7e2cba4132").execute();
        List<Good> list = (List<Good>) response.body();
//        list.forEach(System.out::println);
//        response = documents.getData("20180620-B2F2-40AA-806C-5013E03BA9B8", "91888854-ca0a-4944-89ee-7b7e2cba4132").execute();
//        List<Document> list = (List<Document>) response.body();
        list = list.stream()
                .filter(g -> g.getName().contains("Стюар"))
                .collect(ArrayList::new, (g, l) -> g.add(l), (l1, l2) -> l1.addAll(l2));
        System.out.println(list);
//        list.forEach(l -> l.setQuantity(101));
//        list.forEach(System.out::println);
//        Gson gson = new Gson();
//        String body = gson.toJson(list);
//        System.out.println(body);
//        GoodsPost goodsPost = retrofit.create(GoodsPost.class);
//
//        RequestBody requestBody =
//                RequestBody.create(MediaType.parse("text/plain"), body);
//        Response response1 = goodsPost.sendData("20180620-B2F2-40AA-806C-5013E03BA9B8"
//                , "91888854-ca0a-4944-89ee-7b7e2cba4132"
//                , "application/json"
//                , requestBody).execute();
//        System.out.println(response1.message());

    }
}
