package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Helper class for communicating with the assigned DB
 */
public class Connector {
    protected static final String DB_HOST = System.getenv("DB_HOST");
    protected static final String DB_USER = System.getenv("DB_USER");
    protected static final String DB_PASS = System.getenv("DB_PASS");
    protected static final String DB_NAME = System.getenv("DB_NAME");
    protected static final String URL = "jdbc:mysql://" + DB_HOST + "/" + DB_NAME;
    /** 
     * {@summary Creates a connection using the Connector's credentials} 
     */
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(
            URL,
            DB_USER, 
            DB_PASS
        );
    }
}