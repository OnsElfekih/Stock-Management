package Vue;

import Controller.UtilisateurDAO;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class InterfaceLoginGestionnaire extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Connexion Gestionnaire- Master Stock Pro");

        // Titre de la fenêtre
        Label lblTitle = new Label("Bienvenue chèr gestionnaire!");
        lblTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        lblTitle.setTextFill(Color.DARKBLUE);

        // Création des éléments de l'interface
        Label lblEmail = new Label("Email:");
        lblEmail.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Entrez votre email");

        Label lblPassword = new Label("Mot de passe :");
        lblPassword.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Entrez votre mot de passe");

        Button btnLogin = new Button("Se connecter");
        btnLogin.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14; -fx-padding: 8 16;");
        Button btnCancel = new Button("Annuler");
        btnCancel.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14; -fx-padding: 8 16;");

        Label lblMessage = new Label();
        lblMessage.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblMessage.setTextFill(Color.RED);

        // Mise en page avec GridPane
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));
        grid.setAlignment(Pos.CENTER);

        grid.add(lblEmail, 0, 0);
        grid.add(txtUsername, 1, 0);
        grid.add(lblPassword, 0, 1);
        grid.add(txtPassword, 1, 1);
        grid.add(btnLogin, 1, 2);
        grid.add(lblMessage, 1, 3);

        // Boutons d'action sous la grille
        VBox vboxButtons = new VBox(10, btnLogin, btnCancel);
        vboxButtons.setAlignment(Pos.CENTER);

        // Conteneur principal
        VBox root = new VBox(20, lblTitle, grid, vboxButtons);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f4f4f4;");

        // Action de connexion
        btnLogin.setOnAction(e -> {
            String username = txtUsername.getText();
            String password = txtPassword.getText();

            if (username.isEmpty() || password.isEmpty()) {
                lblMessage.setText("Veuillez remplir tous les champs.");
                lblMessage.setTextFill(Color.RED);
                return;
            }

            // Vérification de la connexion pour un gestionnaire
            if (UtilisateurDAO.verifierConnexion(username, password)) {
                lblMessage.setText("Connexion réussie !");
                lblMessage.setTextFill(Color.GREEN);

                // Passer à l'interface principale
                primaryStage.close(); // Fermer la fenêtre de login
                InterfacePrincipaleGestionnaire interfacePrincipaleGestionnaire = new InterfacePrincipaleGestionnaire();
                interfacePrincipaleGestionnaire.start(new Stage()); // Ouvrir l'interface principale

            } else {
                lblMessage.setText("Aucun gestionnaire ne correspond à ces coordonnées.");
                lblMessage.setTextFill(Color.RED);
            }
        });



        // Action d'annulation
        btnCancel.setOnAction(e -> {
            primaryStage.close(); // Fermer la fenêtre actuelle
            InterfaceLoginPrincipale interfaceLoginPrincipale = new InterfaceLoginPrincipale();
            interfaceLoginPrincipale.start(new Stage()); // Ouvrir l'interface de connexion principale
        });

        // Créer la scène et l'afficher
        Scene scene = new Scene(root, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
