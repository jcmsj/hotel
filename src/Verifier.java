import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

/**
 * {@summary For user authentication}
 * @see Registrar
 */
public class Verifier extends Connector implements AnAuthenticator<User> {
    protected String TABLE_NAME = Registrar.TABLE_NAME;
    protected Optional<User> user = Optional.ofNullable(null);
    @Override
    public boolean login(User u) {
        Connection conn = null;
        try {
            conn = connect();
            PreparedStatement s = conn.prepareStatement("SELECT * FROM " + TABLE_NAME + " where email = ? AND phrase = ?;");
            s.setString(1, u.email());
            s.setString(2, u.phrase());
            var r = s.executeQuery();
            return r.next();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    @Override
    public void logout() {
        user = Optional.ofNullable(null);
    }

    @Override
    public Optional<User> getUser() {
        return user;
    }

    public boolean isLoggedIn() {
        return user.isPresent();
    }
}