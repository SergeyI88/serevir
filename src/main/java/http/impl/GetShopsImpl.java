package http.impl;

import db.entity.Shop;
import http.GetShops;
import http.entity.ShopHttp;
import http.entity.Terminal;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class GetShopsImpl {

    public List<Shop> get(String token) {
        List<Shop> shopsFromAPI = null;
        List<Terminal> terminalList = null;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://umorili.herokuapp.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        try {
            List<ShopHttp> list1 = retrofit.create(GetShops.class).getData(token).execute().body();
            shopsFromAPI = list1.stream().map(s -> {
                Shop shop = new Shop();
                shop.setUuid(s.getUuid());
                shop.setName(s.getName());
                return shop;
            }).collect(Collectors.toList());
            terminalList = new GetTerminalsImpl().get(token);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return union(shopsFromAPI, terminalList);
    }

    private List<Shop> union(List<Shop> shopsFromAPI, List<Terminal> terminalList) {
        return shopsFromAPI.stream().peek(s -> s.setDeviceUuid(terminalList
                .stream()
                .filter(t -> s.getUuid().equals(t.getStoreUuid()))
                .findFirst()
                .orElseGet(Terminal::new)
                .getUuid())).collect(Collectors.toList());
    }
}
