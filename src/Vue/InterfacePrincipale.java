package Vue;

import Vue.InterfaceLoginAdmin;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class InterfacePrincipale extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Master Stock Pro");

        // Création des boutons pour naviguer entre les deux interfaces
        Button btnUtilisateurs = createStyledButton("Gestion des Utilisateurs", "file:D:/onsd/ITBS/Java/test/src/images/users.png");
        Button btnProduits = createStyledButton("Gestion des Produits", "file:D:/onsd/ITBS/Java/test/src/images/product.png");
        Button btnExit = createStyledButton("Terminer", "file:D:/onsd/ITBS/Java/test/src/images/quitter.png");

        // Actions des boutons
        btnUtilisateurs.setOnAction(e -> ouvrirGestionUtilisateurs(primaryStage));
        btnProduits.setOnAction(e -> ouvrirGestionProduits(primaryStage));
        btnExit.setOnAction(e -> {
            primaryStage.close();
            InterfaceLoginAdmin interfaceLogin = new InterfaceLoginAdmin();
            try {
                interfaceLogin.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Disposition des boutons dans un VBox (vertical)
        VBox vBox = new VBox(20, btnUtilisateurs, btnProduits, btnExit);
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-padding: 20; -fx-background-color: rgba(255, 255, 255, 0.7); -fx-background-radius: 15px;");

        // Ajouter l'image en fond (avec transparence)
        StackPane stackPane = new StackPane();
        Image imgXmark = new Image("file:D:/onsd/ITBS/Java/test/src/images/img.png");
        ImageView imageViewXmark = new ImageView(imgXmark);
        imageViewXmark.setFitHeight(241.0);
        imageViewXmark.setFitWidth(243.0);
        stackPane.getChildren().addAll(imageViewXmark, vBox);

        // Appliquer un fond avec transparence
        stackPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3);");

        // Créer la scène principale
        Scene scene = new Scene(stackPane, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthode pour créer un bouton stylisé avec texte et image
    private Button createStyledButton(String text, String imagePath) {
        Button button = new Button(text);
        button.setPrefWidth(250);
        button.setFont(Font.font(16));
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 10px; -fx-background-radius: 10px;");

        // Ajouter l'image spécifique à chaque bouton
        Image img = new Image(imagePath);
        ImageView imageView = new ImageView(img);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        button.setGraphic(imageView);
        button.setContentDisplay(javafx.scene.control.ContentDisplay.LEFT);

        // Effet de survol
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 10px; -fx-background-radius: 10px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 10px; -fx-background-radius: 10px;"));

        return button;
    }

    // Méthode pour ouvrir la gestion des utilisateurs
    private void ouvrirGestionUtilisateurs(Stage primaryStage) {
        InterfaceUtilisateurs interfaceUtilisateurs = new InterfaceUtilisateurs();
        interfaceUtilisateurs.start(primaryStage);
    }

    // Méthode pour ouvrir la gestion des produits
    private void ouvrirGestionProduits(Stage primaryStage) {
        InterfaceProduits interfaceProduits = new InterfaceProduits();
        interfaceProduits.start(primaryStage);
    }

}
