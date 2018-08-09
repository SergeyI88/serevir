package db.DAO;

import db.entity.Client;

import java.sql.Connection;

public interface ClientDao {

    Client getClientById(long id);

    Client getClientByUuid(String uuid);

    boolean createClient(String token, String company_name, String uuid, Connection connection);

    void removeClient(String userUuid);

    void setIsEnable(String uuid, Boolean isEnabled);

    boolean getIsEnable(String token);
}
