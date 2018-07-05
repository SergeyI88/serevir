package service;

import db.DAO.ClientDao;
import db.DAO.ShopDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    ClientDao clientDao;
    @Autowired
    ShopDao shopDao;


    public boolean createClient(String userUuid, String token, String company_name) {
        if (clientDao.getClientByUuid(userUuid).getId() == 0) {
           return clientDao.createClient(token, "", userUuid);
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
}
