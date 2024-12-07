package Controller;

import Model.Produit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/miniprojetjava";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Méthode pour créer la table Produit
    public static void creerTableProduits() {
        String tableName = "Produits";
        String sqlCreate = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "ref VARCHAR(100) PRIMARY KEY, " +
                "nom VARCHAR(100) NOT NULL, " +
                "qte INT NOT NULL, " +
                "prix FLOAT NOT NULL)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.execute(sqlCreate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean verifierTableProduits() {
        String sql = "SHOW TABLES LIKE 'Produits'";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            return resultSet.next(); // Retourne true si la table existe, sinon false
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean ProduitExiste(String ref) {
        String sqlCheck = "SELECT COUNT(*) FROM Produits WHERE ref = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlCheck)) {
            preparedStatement.setString(1, ref);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour ajouter un Produit
    public static void ajouterProduit(Produit produit) {
        if (ProduitExiste(produit.getRef())) {
            System.out.println("Cette reférence  est déjà pour un produit existe"); // Message de console
            return;
        }
        String sqlInsert = "INSERT INTO Produits (ref, nom, qte, prix) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
            preparedStatement.setString(1, produit.getRef());
            preparedStatement.setString(2, produit.getNom());
            preparedStatement.setInt(3, produit.getQte());
            preparedStatement.setFloat(4, produit.getPrix());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour lister tous les produits
    public static List<Produit> listerProduits() {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * FROM produits";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String ref = resultSet.getString("ref");
                String nom = resultSet.getString("nom");
                int qte = resultSet.getInt("qte");
                float prix = resultSet.getFloat("prix");

                Produit produit = new Produit(ref, nom, qte, prix);
                produits.add(produit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produits;
    }


    public static List<Produit> trouverProduit(String ref, String nom) {
        List<Produit> produits = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Produits WHERE 1=1");  // "1=1" pour éviter de manipuler des conditions vides

        // On ajoute des conditions uniquement si elles sont non nulles ou non vides
        if (ref != null && !ref.isEmpty()) {
            sql.append(" AND ref LIKE ?");
        }
        if (nom != null && !nom.isEmpty()) {
            sql.append(" AND nom LIKE ?");
        }

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

            // Définir les paramètres des conditions
            int paramIndex = 1;

            if (ref != null && !ref.isEmpty()) {
                preparedStatement.setString(paramIndex++, "%" + ref + "%");
            }
            if (nom != null && !nom.isEmpty()) {
                preparedStatement.setString(paramIndex++, "%" + nom + "%");
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Produit produit = new Produit(
                            resultSet.getString("ref"),
                            resultSet.getString("nom"),
                            resultSet.getInt("qte"),
                            resultSet.getFloat("prix")
                    );
                    produits.add(produit);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produits;
    }


    // Méthode pour modifier un produit
    public static void modifierProduit(Produit produit) {
        String sqlUpdate = "UPDATE Produits SET nom = ?, qte = ?, prix = ? WHERE ref = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)) {
            preparedStatement.setString(1, produit.getNom());
            preparedStatement.setInt(2, produit.getQte());
            preparedStatement.setFloat(3, produit.getPrix());
            preparedStatement.setString(4, produit.getRef());  // Assumez que l'utilisateur a un ID unique

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour supprimer un produit
    public static void supprimerProduit(Produit produit) {
        String sqlDelete = "DELETE FROM Produits WHERE ref = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete)) {
            preparedStatement.setString(1, produit.getRef());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
