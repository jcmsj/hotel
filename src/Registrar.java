import java.sql.Connection;
import java.sql.SQLException;

public class Registrar extends Connector {
    private static final String TABLE_NAME = "users";

    /**
     * 
     * @return whether the registration was successful
     */
    public boolean register(UserInfo u) {
        Connection conn = null;
        int status = 0;
        try {
            conn = connect();
            var p = conn.prepareStatement("INSERT INTO " + TABLE_NAME + "(name, phrase, email) VALUES(?,?,?)");
            p.setString(1, u.name);
            p.setString(2, u.phrase);
            p.setString(3, u.email);
            status = p.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace(); 
            return false;
        }

        return status == 1;
    }

    public boolean delete(User u) {
        Connection conn;
        int status = 0;
        try {
            conn = connect();
            var p  = conn.prepareStatement("DELETE FROM " + TABLE_NAME + " where email = ? AND phrase = ?;");
            p.setString(1, u.email());
            p.setString(2, u.phrase());
            status = p.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return status == 1;
    }
}