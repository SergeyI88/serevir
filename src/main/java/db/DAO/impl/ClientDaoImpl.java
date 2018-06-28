package db.DAO.impl;

import db.DAO.ClientDao;
import db.connection.ConnectionPostgres;
import db.entity.Client;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ClientDaoImpl implements ClientDao {

    @Override
    public Client getClientById(long id) {
        Client client = new Client();
        try(Connection connection = ConnectionPostgres.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * from client where client_id = ?");
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                client.setId(set.getLong("client_id"));
                client.setToken(set.getString("token"));
                client.setCompanyName(set.getString("company_name"));
                client.setUuid(set.getString("uuid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public Client getClientByUuid(String uuid) {
        Client client = new Client();
        try(Connection connection = ConnectionPostgres.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * from client where uuid = ?");
            statement.setString(1, uuid);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                client.setId(set.getLong("client_id"));
                client.setToken(set.getString("token"));
                client.setCompanyName(set.getString("company_name"));
                client.setUuid(set.getString("uuid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public boolean createClient(String token, String company_name, String uuid) {
        PreparedStatement statement = null;
        int result = 0;
        try(Connection connection = ConnectionPostgres.getConnection()) {
            statement = connection.prepareStatement("INSERT INTO client VALUES(DEFAULT, ?, ?, ?)");
            statement.setString(1, token);
            statement.setString(2, company_name);
            statement.setString(3, uuid);
            result = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result == 1;
    }
}
