package Vue;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class InterfaceLoginAdmin extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Connexion Administrateur - Master Stock Pro");

        // Titre de la fenêtre
        Label lblTitle = new Label("Bienvenue cher administrateur!");
        lblTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        lblTitle.setTextFill(Color.DARKBLUE);

        // Création des éléments de l'interface
        Label lblUsername = new Label("Nom d'utilisateur :");
        lblUsername.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Entrez votre nom d'utilisateur");

        Label lblPassword = new Label("Mot de passe :");
        lblPassword.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        // Champ de mot de passe et champ texte
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Entrez votre mot de passe");
        TextField txtPasswordVisible = new TextField();
        txtPasswordVisible.setPromptText("Entrez votre mot de passe");
        txtPasswordVisible.setManaged(false);
        txtPasswordVisible.setVisible(false);

        // Synchroniser le texte entre PasswordField et TextField
        txtPassword.textProperty().bindBidirectional(txtPasswordVisible.textProperty());

        // Bouton pour afficher/masquer le mot de passe
        Button btnTogglePassword = new Button("👁");
        btnTogglePassword.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        btnTogglePassword.setOnAction(e -> {
            if (txtPassword.isVisible()) {
                // Masquer PasswordField et afficher TextField
                txtPassword.setManaged(false);
                txtPassword.setVisible(false);
                txtPasswordVisible.setManaged(true);
                txtPasswordVisible.setVisible(true);
            } else {
                // Masquer TextField et afficher PasswordField
                txtPasswordVisible.setManaged(false);
                txtPasswordVisible.setVisible(false);
                txtPassword.setManaged(true);
                txtPassword.setVisible(true);
            }
        });


        // Conteneur pour mot de passe et icône
        HBox passwordBox = new HBox(txtPassword, txtPasswordVisible, btnTogglePassword);
        passwordBox.setAlignment(Pos.CENTER_LEFT);
        passwordBox.setSpacing(5);

        Button btnLogin = new Button("Se connecter");
        btnLogin.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14; -fx-padding: 8 16;");
        Button btnCancel = new Button("Annuler");
        btnCancel.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14; -fx-padding: 8 16;");

        Label lblMessage = new Label();
        lblMessage.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblMessage.setTextFill(Color.RED);

        // Mise en page avec GridPane
// Mise en page avec GridPane
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));
        grid.setAlignment(Pos.CENTER);

        grid.add(lblUsername, 0, 0);
        grid.add(txtUsername, 1, 0);
        grid.add(lblPassword, 0, 1);
        grid.add(passwordBox, 1, 1);
        grid.add(btnLogin, 0, 2); // Positionner "Se connecter" à gauche
        grid.add(btnCancel, 1, 2); // Positionner "Annuler" à droite
        grid.add(lblMessage, 0, 3, 2, 1); // Étendre le message d'erreur sur 2 colonnes


        // Conteneur principal
        VBox root = new VBox(20, lblTitle, grid);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f4f4f4;");

        // Action de connexion
        btnLogin.setOnAction(e -> {
            String username = txtUsername.getText();
            String password = txtPasswordVisible.isVisible() ? txtPasswordVisible.getText() : txtPassword.getText();

            if (username.isEmpty() || password.isEmpty()) {
                lblMessage.setText("Veuillez remplir tous les champs.");
                return;
            }

            if (!"admin".equals(username)) {
                lblMessage.setText("Nom d'utilisateur incorrect.");
                lblMessage.setTextFill(Color.RED);
            } else if (!"adminadmin".equals(password)) {
                lblMessage.setText("Mot de passe incorrect.");
                lblMessage.setTextFill(Color.RED);
            } else {
                lblMessage.setText("Connexion réussie !");
                lblMessage.setTextFill(Color.GREEN);
                // Passer à l'interface principale
                primaryStage.close(); // Fermer la fenêtre de login
                InterfacePrincipale interfacePrincipale = new InterfacePrincipale();
                interfacePrincipale.start(new Stage()); // Ouvrir l'interface principale
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
