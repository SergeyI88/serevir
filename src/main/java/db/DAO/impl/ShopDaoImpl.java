package db.DAO.impl;

import db.DAO.ShopDao;
import db.connection.ConnectionPostgres;
import db.entity.Shop;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@SuppressWarnings("Duplicates")
public class ShopDaoImpl implements ShopDao {
    @Override
    public Shop getShopById(long id) {
        Shop shop = new Shop();
        try (Connection connection = ConnectionPostgres.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * from shop where id = ?");
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            shop.setId(set.getLong("id"));
            shop.setUuid(set.getString("shop_uuid"));
            shop.setClientId(set.getLong("client_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shop;
    }

    @Override
    public Shop getShopClientByUuid(String uuid) {
        Shop shop = new Shop();
        try (Connection connection = ConnectionPostgres.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * from shop where uuid = ?");
            statement.setString(1, uuid);
            ResultSet set = statement.executeQuery();
            shop.setId(set.getLong("id"));
            shop.setUuid(set.getString("shop_uuid"));
            shop.setClientId(set.getLong("client_id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shop;
    }

    @Override
    public boolean createShop(String shop_uuid, long client_id) {
        PreparedStatement statement = null;
        int result = 0;
        try (Connection connection = ConnectionPostgres.getConnection();) {
            statement = connection.prepareStatement("INSERT INTO shop VALUES(DEFAULT, ?, ?)");
            statement.setString(2, shop_uuid);
            statement.setLong(3, client_id);
            result = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result == 1;
    }

    @Override
    public List<Shop> getAllShopFromClientByIdClient(long id) {
        List<Shop> shopList = new ArrayList<>();
        try (Connection connection = ConnectionPostgres.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * from shop as s inner join client as c on s.client_id = c.client_id where c.client_id = ?");
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            while (set.isFirst()) {
                Shop shop = new Shop();
                shop.setId(set.getLong("id"));
                shop.setClientId(set.getLong("client_id"));
                shop.setUuid(set.getString("uuid"));
                shopList.add(shop);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shopList;
    }

    @Override
    @Deprecated
    public List<Shop> getAllShopFromClientByUuidClient(String uuid) {
        return null;
    }
}
