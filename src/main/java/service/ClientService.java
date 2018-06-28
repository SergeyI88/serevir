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

}
