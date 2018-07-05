package db.DAO;

import db.entity.Client;

public interface ClientDao {

    Client getClientById(long id);

    Client getClientByUuid(String uuid);

    boolean createClient(String token, String company_name, String uuid);

    void removeClient(String userUuid);

    void setIsEnable(String uuid, Boolean isEnabled);

    boolean getIsEnable(String token);
}
