package dk.michaelbui.ezbud_server.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionProvider.class);

    private String userName;
    private String password;
    private String url;

    public ConnectionProvider( String url, String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.url = url;
    }

    public Connection getConnection() throws InfrastructureException{
        try {
             return DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            LOG.error("Failed to connect to database", e);
            throw new InfrastructureException("Failed to connect to database", e);
        }
    }
}
