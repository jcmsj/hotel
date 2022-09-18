package auth;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.Connector;

/**
 * {@summary Handles database management for users}
 * @see Verifier
 */
public class Registrar extends Connector {
    public static final String TABLE_NAME = "users";
    private static Registrar registrar;
    public Registrar() throws SQLException {
        executeSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id int NOT NULL AUTO_INCREMENT, email varchar(255) NOT NULL UNIQUE, name varchar(255) NOT NULL, phrase varchar(255) NOT NULL, address varchar(255) NOT NULL, contact varchar(11) NOT NULL, PRIMARY KEY (id))");
        registrar = this;
    }

    /**
     * Gets the registrar singleton
     */
    public static Registrar getRegistrar() throws SQLException {
        if (registrar == null) {
            return new Registrar();
        }
        
        return registrar;
    }
    /**
     * 
     * @return new record's id or failure if 0.
     */
    public int register(UserInfo u) {
        Connection conn = null;
        int new_id = 0;
        try {
            conn = connect();
            var p = conn.prepareStatement("INSERT INTO " + TABLE_NAME + "(name, phrase, email, address, contact) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            p.setString(1, u.getName());
            p.setString(2, u.getPhrase());
            p.setString(3, u.getEmail());
            p.setString(4, u.getAddress());
            p.setString(5, u.getContact());
            p.executeUpdate();
            var rs = p.getGeneratedKeys();
            if (rs.next()) {
                new_id = rs.getInt(1);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return new_id;
    }

    public boolean delete(User u) {
        Connection conn;
        int status = 0;
        try {
            conn = connect();
            var p  = conn.prepareStatement("DELETE FROM " + TABLE_NAME + " where email = ? AND phrase = ?;");
            p.setString(1, u.email);
            p.setString(2, u.phrase);
            status = p.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return status == 1;
    }
}