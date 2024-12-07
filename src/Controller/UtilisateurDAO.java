package Controller;
import Model.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/miniprojetjava";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void creerTableUtilisateurs() {
        String tableName = "Utilisateurs";
        String sqlCreate = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nom VARCHAR(100) NOT NULL, " +
                "email VARCHAR(100) UNIQUE NOT NULL, " +
                "motDePasse VARCHAR(100) NOT NULL, " +
                "typeUtilisateur VARCHAR(50) NOT NULL)";  // Added field for user type

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.execute(sqlCreate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean verifierTableUtilisateurs() {
        String sql = "SHOW TABLES LIKE 'Utilisateurs'";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            return resultSet.next(); // Retourne true si la table existe, sinon false
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean validerEmail(String email) {
        // Utilisation d'une expression régulière pour valider l'email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }





    public static boolean utilisateurExiste(String email) {
        String sqlCheck = "SELECT COUNT(*) FROM Utilisateurs WHERE email = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlCheck)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void ajouterUtilisateur(Utilisateur utilisateur) {
        if (!validerEmail(utilisateur.getEmail())) {
            System.out.println("L'email n'est pas valide."); // Message de console pour email invalide
            return;
        }

        if (utilisateurExiste(utilisateur.getEmail())) {
            System.out.println("Cet email est déjà utilisé pour un autre utilisateur."); // Message de console
            return;
        }

        String sqlInsert = "INSERT INTO Utilisateurs (nom, email, motDePasse, typeUtilisateur) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getEmail());
            preparedStatement.setString(3, utilisateur.getMotDePasse());
            preparedStatement.setString(4, utilisateur.getTypeUtilisateur());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public static List<Utilisateur> listerUtilisateurs() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sqlSelect = "SELECT * FROM Utilisateurs";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlSelect)) {
            while (resultSet.next()) {
                Utilisateur utilisateur = new Utilisateur(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("email"),
                        resultSet.getString("motDePasse"),
                        resultSet.getString("typeUtilisateur")  // Récupère le type d'utilisateur
                );
                utilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }


    public static void modifierUtilisateur(Utilisateur utilisateur) {
        String sqlUpdate = "UPDATE Utilisateurs SET nom = ?, email = ?, motDePasse = ?, typeUtilisateur = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)) {
            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getEmail());
            preparedStatement.setString(3, utilisateur.getMotDePasse());
            preparedStatement.setString(4, utilisateur.getTypeUtilisateur());
            preparedStatement.setInt(5, utilisateur.getId());  // Assumez que l'utilisateur a un ID unique

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public static void supprimerUtilisateur(Utilisateur utilisateur) {
        String sqlDelete = "DELETE FROM Utilisateurs WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete)) {
            preparedStatement.setInt(1, utilisateur.getId()); // Use the ID from the Utilisateur object
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Utilisateur> rechercherParNomEmailType(String nom, String email, String typeUtilisateur) {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM Utilisateurs WHERE 1=1");

        // Ajoutez les conditions dynamiquement en fonction des paramètres non vides
        if (nom != null && !nom.isEmpty()) {
            sqlBuilder.append(" AND nom LIKE ?");
        }
        if (email != null && !email.isEmpty()) {
            sqlBuilder.append(" AND email LIKE ?");
        }
        if (typeUtilisateur != null && !typeUtilisateur.isEmpty()) {
            sqlBuilder.append(" AND typeUtilisateur LIKE ?");
        }

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString())) {

            int parameterIndex = 1;

            // Ajoutez les paramètres dynamiquement
            if (nom != null && !nom.isEmpty()) {
                preparedStatement.setString(parameterIndex++, "%" + nom + "%");
            }
            if (email != null && !email.isEmpty()) {
                preparedStatement.setString(parameterIndex++, "%" + email + "%");
            }
            if (typeUtilisateur != null && !typeUtilisateur.isEmpty()) {
                preparedStatement.setString(parameterIndex++, "%" + typeUtilisateur + "%");
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Utilisateur utilisateur = new Utilisateur(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("email"),
                        resultSet.getString("motDePasse"),
                        resultSet.getString("typeUtilisateur")
                );
                utilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }

    public static boolean verifierConnexion(String email, String motDePasse) {
        String sql = "SELECT * FROM Utilisateurs WHERE email = ? AND motDePasse = ? AND typeUtilisateur = 'Gestionnaire'";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, motDePasse);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Retourne true si l'utilisateur est trouvé et est un Gestionnaire
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean verifierConnexionEmploye(String email, String motDePasse) {
        String sql = "SELECT * FROM Utilisateurs WHERE email = ? AND motDePasse = ? AND typeUtilisateur = 'Employe'";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, motDePasse);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Retourne true si l'utilisateur est trouvé et est un employe
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

