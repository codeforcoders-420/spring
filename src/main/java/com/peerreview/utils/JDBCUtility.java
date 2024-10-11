import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtility {

    public static Connection getDBConnection() {
        String url = "jdbc:mysql://localhost:3306/mySQL_Learning";
        String username = "root";
        String password = "BeStrong23!";
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
