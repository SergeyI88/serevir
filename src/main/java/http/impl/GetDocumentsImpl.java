package http.impl;

import controllers.json.Document;
import http.GetDocuments;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

public class GetDocumentsImpl {
    public List<Document> get(String storeUuid, String auth){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://umorili.herokuapp.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetDocuments getDocuments = retrofit.create(GetDocuments.class);
        List<Document> list = null;
        try {
            list = getDocuments.get(storeUuid, auth).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        GetDocumentsImpl getDocuments = new GetDocumentsImpl();
        getDocuments.get("20180620-B2F2-40AA-806C-5013E03BA9B8","4874893f-df06-46be-a0f0-68e32cbf4110").forEach(System.out::println);

    }

    public List<Document> get(String storeUuid, String token, String to, String from) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://umorili.herokuapp.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetDocuments getDocuments = retrofit.create(GetDocuments.class);
        List<Document> list = null;
        try {
            list = getDocuments.get(storeUuid, token, to, from).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
