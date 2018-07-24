package service;

import db.DAO.ErrorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceError {
    @Autowired
    ErrorDao errorDao;
    public void writeError(String string, Object token) {
        errorDao.write(string, (String) token);
    }
}
