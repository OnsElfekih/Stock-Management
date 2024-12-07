package Vue;

import Controller.UtilisateurDAO;
import Model.Utilisateur;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;

public class InterfaceRechercheUtilisateur extends Application {

    private TextField txtRechercheNom;
    private TextField txtRechercheEmail;
    private ComboBox<String> comboBoxTypeUtilisateur;
    private Button btnRechercher;
    private Button btnAnnuler;
    private TableView<Utilisateur> tableView;
    private ObservableList<Utilisateur> utilisateurObservable;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Recherche Utilisateur");

        // Layout principal
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));

        // Titre de la section recherche
        Label labelTitre = new Label("Rechercher un utilisateur");
        labelTitre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10 0 20 0;");

        // Créer les champs de recherche dans un VBox
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.TOP_CENTER);

        txtRechercheNom = new TextField();
        txtRechercheNom.setPromptText("Entrez le Nom et le Prénom");
        txtRechercheNom.setPrefWidth(350);

        txtRechercheEmail = new TextField();
        txtRechercheEmail.setPromptText("Entrez l'email");
        txtRechercheEmail.setPrefWidth(350);

        comboBoxTypeUtilisateur = new ComboBox<>();
        comboBoxTypeUtilisateur.getItems().addAll("Gestionnaire", "Employé");
        comboBoxTypeUtilisateur.setPromptText("Sélectionnez le rôle de l'utilisateur.");
        comboBoxTypeUtilisateur.setPrefWidth(750);

        vbox.getChildren().addAll(labelTitre, txtRechercheNom, txtRechercheEmail, comboBoxTypeUtilisateur);

        // Boutons de recherche et annulation
        HBox hboxButtons = new HBox(20);
        hboxButtons.setAlignment(Pos.CENTER);
        hboxButtons.setPadding(new Insets(20, 0, 20, 0));

        btnRechercher = new Button("Trouver");
        btnRechercher.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");

        btnAnnuler = new Button("Retourner");
        btnAnnuler.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px;");

        hboxButtons.getChildren().addAll(btnRechercher, btnAnnuler);

// TableView pour afficher les résultats
        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<Utilisateur, String> colNom = new TableColumn<>("Nom et Prénom");
        colNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        colNom.setPrefWidth(150);

        TableColumn<Utilisateur, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        colEmail.setPrefWidth(200);

        TableColumn<Utilisateur, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(cellData -> cellData.getValue().typeUtilisateurProperty());
        colType.setPrefWidth(150);

// Nouvelle colonne pour afficher le mot de passe
        TableColumn<Utilisateur, String> colMotDePasse = new TableColumn<>("Mot de Passe");
        colMotDePasse.setCellValueFactory(cellData -> cellData.getValue().motDePasseProperty());
        colMotDePasse.setPrefWidth(200);

        tableView.getColumns().addAll(colNom, colEmail, colType, colMotDePasse);
        tableView.setPlaceholder(new Label("Aucun résultat trouvé."));


        // Gestionnaire d'événements pour les boutons
        btnRechercher.setOnAction(event -> {
            String nom = txtRechercheNom.getText().trim();
            String email = txtRechercheEmail.getText().trim();
            String typeUtilisateur = comboBoxTypeUtilisateur.getValue();

            List<Utilisateur> utilisateurs = rechercherUtilisateurs(nom, email, typeUtilisateur);

            if (utilisateurs == null || utilisateurs.isEmpty()) {
                showAlert("Aucun utilisateur trouvé.");
            } else {
                mettreAJourTableView(utilisateurs);
            }
            clearFields(); // Clear all fields after search
        });

        btnAnnuler.setOnAction(event -> {
            clearFields(); // Clear fields when "Annuler" is clicked
            returnToInterfaceUtilisateur(primaryStage); // Switch to the main screen
        });

        // Ajout des composants au BorderPane
        VBox centerLayout = new VBox(15, vbox, hboxButtons);
        centerLayout.setAlignment(Pos.CENTER);

        borderPane.setTop(centerLayout);
        borderPane.setCenter(tableView);

        // Création de la scène
        Scene scene = new Scene(borderPane, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void mettreAJourTableView(List<Utilisateur> utilisateurs) {
        utilisateurObservable = FXCollections.observableArrayList(utilisateurs);
        tableView.setItems(utilisateurObservable);
    }

    private List<Utilisateur> rechercherUtilisateurs(String nom, String email, String typeUtilisateur) {
        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        return utilisateurDAO.rechercherParNomEmailType(nom, email, typeUtilisateur);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Résultat Recherche");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        txtRechercheNom.clear();
        txtRechercheEmail.clear();
        comboBoxTypeUtilisateur.setValue(null);
    }

    private void returnToInterfaceUtilisateur(Stage primaryStage) {
        // Here you should create the scene for InterfaceUtilisateur (main interface)
        // For example, if InterfaceUtilisateur is another class that extends Application:
        InterfaceUtilisateurs interfaceUtilisateur = new InterfaceUtilisateurs();
        try {
            interfaceUtilisateur.start(primaryStage);  // Switch back to the main screen
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
