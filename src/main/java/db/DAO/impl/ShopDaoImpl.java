package db.DAO.impl;

import db.DAO.ShopDao;
import db.connection.ConnectionPostgres;
import db.entity.Shop;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@SuppressWarnings("Duplicates")
public class ShopDaoImpl implements ShopDao {
    private Logger logger = Logger.getLogger(ShopDaoImpl.class);
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
        PreparedStatement statement;
        int result = 0;
        try (Connection connection = ConnectionPostgres.getConnection()) {
            statement = connection.prepareStatement("INSERT INTO shop VALUES(DEFAULT, ?, ?, ?, DEFAULT)");
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
                shop.setUuid(set.getString("shop_uuid"));
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
                shop.setUuid(set.getString("shop_uuid"));
                shop.setName(set.getString("name"));
                shopList.add(shop);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shopList;
    }

    @Override
    public boolean downLoadShops(String userUuid, List<Shop> list, Connection connection) {
        int[] result = null;
        try  {
            Statement statement = connection.createStatement();
            for (Shop s : list) {
                statement.addBatch("INSERT INTO shop VALUES(DEFAULT, " +
                        "'" + s.getUuid() + "'" + ", (SELECT client_id FROM client WHERE client.uuid = " + "'" + userUuid + "'" + "), " + "'" + s.getName() + "', DEFAULT, " + "'" +s.getDeviceUuid()+ "'" + ")");
            }
            result = statement.executeBatch();
            logger.info("downloadShop good");
            connection.commit();
        } catch (SQLException e) {
            logger.info("downloadShop");
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result != null;
    }

    @Override
    public Shop getShopByUuidStore(String storeUuid) {
        Shop shop = new Shop();
        try (Connection connection = ConnectionPostgres.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * from shop where shop_uuid = ?");
            statement.setString(1, storeUuid);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                shop.setId(set.getLong("id"));
                shop.setUuid(set.getString("shop_uuid"));
                shop.setClientId(set.getLong("client_id"));
                shop.setName(set.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shop;
    }

    @Override
    public void removeShops(String userUuid) {
        try (Connection connection = ConnectionPostgres.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE  from shop where client_id = (SELECT client_id from client where uuid = ?)");
            statement.setString(1, userUuid);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTokenByStoreUuid(String shop) {
        String res = null;
        try (Connection connection = ConnectionPostgres.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT c.token FROM client as c where c.client_id =" +
                    " (SELECT client_id from shop where shop_uuid = ?)");
            statement.setString(1, shop);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                res = set.getString("token");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public String getNameByStoreUuid(String storeUuid) {
        String res = null;
        try (Connection connection = ConnectionPostgres.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT name FROM shop where shop_uuid = ?");
            statement.setString(1, storeUuid);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                res = set.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void writeSequenceColumns(String string, String storeUuid) {
        try (Connection connection = ConnectionPostgres.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement("UPDATE shop set sequence_columns = ? WHERE shop_uuid = ?");
            statement.setString(1, string);
            statement.setString(2, storeUuid);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getSequance(String storeUuid) {
        String res = null;
        try (Connection connection = ConnectionPostgres.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT sequence_columns FROM shop where shop_uuid = ?");
            statement.setString(1, storeUuid);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                res = set.getString("sequence_columns");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
