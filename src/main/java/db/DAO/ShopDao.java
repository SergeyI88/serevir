package db.DAO;

import db.entity.Shop;

import java.sql.Connection;
import java.util.*;

public interface ShopDao {

    Shop getShopById(long id);

    Shop getShopClientByUuid(String uuid);

    boolean createShop(String shop_uuid, long client_id, String name);

    List<Shop> getAllShopFromClientByIdClient(long id);

    List<Shop> getAllShopFromClientByUuidClient(String uuid);

    boolean downLoadShops(String userUuid, List<Shop> list, Connection connection);

    Shop getShopByUuidStore(String storeUuid);

    void removeShops(String userUuid);

    String getTokenByStoreUuid(String shop);

    String getNameByStoreUuid(String storeUuid);

    void writeSequenceColumns(String string, String storeUuid);

    String getSequance(String storeUuid);
}
