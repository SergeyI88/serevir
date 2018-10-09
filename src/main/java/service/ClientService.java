package service;

import db.DAO.ClientDao;
import db.DAO.ShopDao;
import db.connection.ConnectionPostgres;
import db.entity.Client;
import http.impl.GetShopsImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Service
public class ClientService {
    @Autowired
    ClientDao clientDao;
    @Autowired
    ShopDao shopDao;
    @Autowired ServiceError serviceError;

    Logger logger = Logger.getLogger(ClientService.class);

    public boolean createClient(String userUuid, String token, String company_name) {
        if (clientDao.getClientByUuid(userUuid).getId() == 0) {
            Connection connection = ConnectionPostgres.getConnection();
            try {
                GetShopsImpl getShops = new GetShopsImpl();
                clientDao.createClient(token, "", userUuid, connection);
                logger.info("create Client");
                shopDao.downLoadShops(userUuid, getShops.get(token), connection);
            } catch (Throwable e) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                throw new RuntimeException("Некорректный токен");
            }
        }
        return false;
    }

    public void removeClient(String userUuid) {
        shopDao.removeShops(userUuid);
        clientDao.removeClient(userUuid);
    }

    public void setSubscription(String userId, boolean b) {
        clientDao.setIsEnable(userId, b);
    }

    public boolean getSubscription(String x_auth) {
        return clientDao.getIsEnable(x_auth);
    }

    public boolean getAlert(String x_auth) {
        return clientDao.getWasAlert(x_auth);
    }

    public void setWasAlert(String x_auth){
        clientDao.setWasAlert(x_auth);
    }

    public Client getClientByTokenAndUpdateWasAlert(String x_auth) {
        return clientDao.getClientByTokenAndUpdateWasAlert(x_auth);
    }
}
