package db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    // Private constructor to prevent instantiation
    private Database() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    private static final Logger LOGGER = Logger.getLogger(Database.class.getName());

    private static String url;
    private static String user;
    private static String pass;

    static {
        try (InputStream input = Database.class.getResourceAsStream("/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            url = prop.getProperty("db.url");
            user = prop.getProperty("db.user");
            pass = prop.getProperty("db.pass");
        } catch (IOException e) {
            // Proper logging instead of printStackTrace
            LOGGER.log(Level.SEVERE, "Failed to load database configuration", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
}