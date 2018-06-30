package service;


import db.DAO.ShopDao;
import db.entity.Shop;
import http.Shops;
import http.entity.ShopHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopService {

    @Autowired
    ShopDao shopDao;
    @Autowired
    ClientService clientService;

    public List<Shop> getShops(String userUuid, String token) {
        List<Shop> list = shopDao.getAllShopFromClientByUuidClient(userUuid);
        if (list.isEmpty()) {
            List<ShopHttp> list1 = null;
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://umorili.herokuapp.com") //Базовая часть адреса
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            try {
                list1 = retrofit.create(Shops.class).getData(token).execute().body();
                list = list1.stream().map(s -> {
                    Shop shop = new Shop();
                    shop.setUuid(s.getUuid());
                    shop.setName(s.getName());
                    return shop;
                }).collect(Collectors.toList());
                clientService.createClient(userUuid, token, "");
                shopDao.downLoadShops(userUuid, list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public Shop getShop(String storeUuid) {
        return shopDao.getShopByUuidStore(storeUuid);
    }

    public String getTokenByStoreUuid(String shop) {
        return shopDao.getTokenByStoreUuid(shop);
    }
}
