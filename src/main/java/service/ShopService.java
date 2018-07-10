package service;


import db.DAO.ShopDao;
import db.entity.Shop;
import http.entity.ShopHttp;
import http.impl.GetShopsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopService {

    @Autowired
    ShopDao shopDao;
    @Autowired
    ClientService clientService;

    public List<Shop> getShops(String userUuid) {
        List<Shop> list = shopDao.getAllShopFromClientByUuidClient(userUuid);
        return list;
    }

    public Shop getShop(String storeUuid) {
        return shopDao.getShopByUuidStore(storeUuid);
    }

    public String getTokenByStoreUuid(String shop) {
        return shopDao.getTokenByStoreUuid(shop);
    }

    public String getNameByStoreUuid(String storeUuid) {
        return shopDao.getNameByStoreUuid(storeUuid);
    }

    public String getSequance(String storeUuid) {
        return shopDao.getSequance(storeUuid);
    }

    public void writeSequenceColumns(String sequence, String storeUuid) {
        shopDao.writeSequenceColumns(sequence, storeUuid);
    }

}

