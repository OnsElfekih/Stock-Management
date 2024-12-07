import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseChecker {
    private static final String URL = "jdbc:mysql://localhost:3306"; // URL sans nom de base de données
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Pas de mot de passe

    public static boolean checkDatabaseExists(String dbName) {
        boolean exists = false;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            // Exécutez une requête pour vérifier si la base de données existe
            String query = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '" + dbName + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                exists = true; // La base de données existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }

    public static void main(String[] args) {
        String dbName = "miniprojetjava"; // Nom de la base de données à vérifier

        if (checkDatabaseExists(dbName)) {
            System.out.println("La base de données '" + dbName + "' existe.");
        } else {
            System.out.println("La base de données '" + dbName + "' n'existe pas.");
        }
    }
}
