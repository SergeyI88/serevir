package db.DAO;

import db.entity.Client;

import java.sql.Connection;

public interface ClientDao {

    Client getClientById(long id);

    Client getClientByUuid(String uuid);

    Client getClientByTokenAndUpdateWasAlert(String token);

    boolean createClient(String token, String company_name, String uuid, Connection connection);

    void removeClient(String userUuid);

    void setIsEnable(String uuid, Boolean isEnabled);

    boolean getIsEnable(String token);

    boolean getWasAlert(String token);

    void setWasAlert(String token);
}
