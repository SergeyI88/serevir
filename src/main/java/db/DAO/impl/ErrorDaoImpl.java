package db.DAO.impl;

import db.DAO.ErrorDao;
import db.connection.ConnectionPostgres;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Repository
public class ErrorDaoImpl implements ErrorDao {
    Logger logger = Logger.getLogger(ErrorDaoImpl.class);
    @Override
    public void write(String message, String token) {
        PreparedStatement statement;
        try (Connection connection = ConnectionPostgres.getConnection()) {
            statement = connection.prepareStatement("INSERT INTO errors VALUES(DEFAULT, ?, ?, ?)");
            statement.setObject(1, LocalDateTime.now());
            statement.setString(2, message);
            statement.setString(3, token);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }
}
