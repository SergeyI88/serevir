package db.DAO;

import db.entity.Shop;

import java.util.*;

public interface ShopDao {

    Shop getShopById(long id);

    Shop getShopClientByUuid(String uuid);

    boolean createShop(String shop_uuid, long client_id);

    List<Shop> getAllShopFromClientByIdClient(long id);

    List<Shop> getAllShopFromClientByUuidClient(String uuid);

}
