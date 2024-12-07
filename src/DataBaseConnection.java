import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/miniprojetjava"; // Remplacez "mysql" par votre SGBD si nécessaire
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Pas de mot de passe

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("Connexion réussie !");
            } else {
                System.out.println("Échec de connexion !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
