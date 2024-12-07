package Vue;

import Vue.InterfaceLoginAdmin;
import Vue.InterfaceLoginEmploye;
import Vue.InterfaceLoginGestionnaire;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;  // Ajouté pour le centrage des éléments
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;  // Ajouté pour le centrage du GridPane
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class InterfaceLoginPrincipale extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Connexion - Master Stock Pro");

        // GridPane pour l'interface
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));  // Espace autour de la grille
        grid.setVgap(15);  // Espacement vertical
        grid.setHgap(10);  // Espacement horizontal
        grid.setStyle("-fx-background-color: #f4f4f9;");

        // Centrage du GridPane dans la scène
        grid.setAlignment(Pos.CENTER);  // Centrer le contenu du GridPane

        // Titre de la page
        Label lblTitre = new Label("Bienvenue sur Master Stock Pro");
        lblTitre.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        lblTitre.setTextFill(Color.DARKGREEN);
        grid.add(lblTitre, 0, 0, 2, 1);
        GridPane.setMargin(lblTitre, new Insets(0, 0, 20, 0)); // Marge en bas

        // ComboBox pour choisir le rôle
        Label lblRole = new Label("Type d'utilisateur:");
        ComboBox<String> comboRole = new ComboBox<>();
        comboRole.getItems().addAll("Administrateur", "Gestionnaire", "Employé");
        comboRole.setPromptText("Sélectionnez votre rôle");
        comboRole.setStyle("-fx-border-radius: 5px; -fx-border-color: #5A5A5A; -fx-padding: 5px;");

        // Bouton de connexion
        Button btnConnexion = new Button("Se connecter");
        btnConnexion.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 10px 20px;");


        // Label pour afficher les messages d'erreur
        Label lblErreur = new Label();
        lblErreur.setTextFill(Color.RED);
        lblErreur.setFont(Font.font("Arial", FontWeight.NORMAL, 12));

        // Action pour le bouton de connexion
        btnConnexion.setOnAction(e -> {
            String role = comboRole.getValue();
            if (role != null) {
                afficherInterface(role, primaryStage);  // Afficher l'interface selon le rôle
            } else {
                lblErreur.setText("Veuillez sélectionner un rôle.");
            }
        });

        // Ajouter les éléments à la grille
        grid.add(lblRole, 0, 3);
        grid.add(comboRole, 1, 3);
        grid.add(btnConnexion, 1, 4);
        grid.add(lblErreur, 1, 5);

        // Créer un StackPane pour centrer le GridPane dans la scène
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(grid);

        // Scène et affichage
        Scene scene = new Scene(stackPane, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void afficherInterface(String role, Stage primaryStage) {
        switch (role) {
            case "Administrateur":
                new InterfaceLoginAdmin().start(primaryStage);  // Affiche l'interface Admin
                break;
            case "Gestionnaire":
                new InterfaceLoginGestionnaire().start(primaryStage);  // Affiche l'interface Gestionnaire
                break;
            case "Employé":
                new InterfaceLoginEmploye().start(primaryStage);  // Affiche l'interface Employé
                break;
            default:
                throw new IllegalArgumentException("Rôle non supporté: " + role);
        }
    }
}
