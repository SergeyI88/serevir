package db.DAO.impl;

import db.DAO.ShopDao;
import db.connection.ConnectionPostgres;
import db.entity.Shop;
import org.springframework.stereotype.Repository;

import java.sql.*;
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
            shop.setName(set.getString("name"));
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
            shop.setName(set.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shop;
    }

    @Override
    public boolean createShop(String shop_uuid, long client_id, String name) {
        PreparedStatement statement = null;
        int result = 0;
        try (Connection connection = ConnectionPostgres.getConnection();) {
            statement = connection.prepareStatement("INSERT INTO shop VALUES(DEFAULT, ?, ?, ?)");
            statement.setString(1, shop_uuid);
            statement.setLong(2, client_id);
            statement.setString(3, name);
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
            while (set.next()) {
                Shop shop = new Shop();
                shop.setId(set.getLong("id"));
                shop.setClientId(set.getLong("client_id"));
                shop.setUuid(set.getString("uuid"));
                shop.setName(set.getString("name"));
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
        List<Shop> shopList = new ArrayList<>();
        try (Connection connection = ConnectionPostgres.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * from shop as s inner join client as c on s.client_id = c.client_id where c.uuid = ?");
            statement.setString(1, uuid);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Shop shop = new Shop();
                shop.setId(set.getLong("id"));
                shop.setClientId(set.getLong("client_id"));
                shop.setUuid(set.getString("uuid"));
                shop.setName(set.getString("name"));
                shopList.add(shop);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shopList;
    }

    @Override
    public boolean downLoadShops(String userUuid, List<Shop> list) {
        int[] result = null;
        try (Connection connection = ConnectionPostgres.getConnection()) {
            Statement statement = connection.createStatement();
            for (Shop s : list) {
                statement.addBatch("INSERT INTO shop VALUES(DEFAULT, " +
                        "'" + s.getUuid() + "'" + ", (SELECT client_id FROM client WHERE client.uuid = "+ "'" + userUuid + "'" +"), " + "'" + s.getName() + "'" + ")");
            }
            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result != null;
    }
}
