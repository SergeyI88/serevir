package db.connection;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPostgres {

    public static Connection getConnection() {
        URI dbUri;
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            dbUri = new URI("postgres://gmshneygtfislj:c2dc30ac57b0434b0b56e7ee79f9a6872ee6ae053129727a296fa6822b50260d@ec2-54-235-70-253.compute-1.amazonaws.com:5432/dctl2lg60na9pe");

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
            System.out.println(dbUrl);
            connection = DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException | URISyntaxException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
