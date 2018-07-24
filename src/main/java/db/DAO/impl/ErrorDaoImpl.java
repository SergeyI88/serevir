package db.DAO.impl;

import db.DAO.ErrorDao;
import db.connection.ConnectionPostgres;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
@Repository
public class ErrorDaoImpl implements ErrorDao {
    @Override
    public void write(String message) {
        PreparedStatement statement;
        try (Connection connection = ConnectionPostgres.getConnection()) {
            statement = connection.prepareStatement("INSERT INTO client VALUES(DEFAULT, ?, ?)");
            statement.setObject(1, LocalDateTime.now());
            statement.setString(2, message);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
