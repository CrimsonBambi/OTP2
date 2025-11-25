import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/translation_test?serverTimezone=UTC&useSSL=false";
    private static final String USER = "root";
    private static final String PASS = ""; // no password in XAMPP default

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

