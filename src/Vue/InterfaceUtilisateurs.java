package Vue;

import Controller.UtilisateurDAO;
import Model.Utilisateur;
import Vue.InterfaceModifUtilisateur;
import Vue.InterfacePrincipale;
import Vue.InterfaceRechercheUtilisateur;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InterfaceUtilisateurs extends Application {
    private TextField txtNom;
    private TextField txtEmail;
    private TextField txtMotDePasse;
    private TableView<Utilisateur> tableView;
    private Utilisateur utilisateurSelectionne;
    private ComboBox<String> comboTypeUtilisateur;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gestion des Utilisateurs");

        if (!UtilisateurDAO.verifierTableUtilisateurs()) {
            UtilisateurDAO.creerTableUtilisateurs();
        }
        List<Utilisateur> utilisateurs = UtilisateurDAO.listerUtilisateurs();
        ObservableList<Utilisateur> utilisateurObservable = FXCollections.observableArrayList(utilisateurs);

        // Layout principal
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(15);
        grid.setHgap(10);
        grid.setStyle("-fx-background-color: #f4f4f9;");

        // Champs de texte pour l'utilisateur
        txtNom = new TextField();
        txtNom.setPromptText("Nom et Prénom");
        txtNom.setStyle("-fx-border-radius: 5px; -fx-border-color: #5A5A5A;");

        txtEmail = new TextField();
        txtEmail.setPromptText("Email");
        txtEmail.setStyle("-fx-border-radius: 5px; -fx-border-color: #5A5A5A;");

        // Champ de texte pour le mot de passe
        txtMotDePasse = new PasswordField();  // Remplacer TextField par PasswordField
        txtMotDePasse.setPromptText("Mot de passe");
        txtMotDePasse.setStyle("-fx-border-radius: 5px; -fx-border-color: #5A5A5A;");


        // ComboBox pour le type d'utilisateur
        comboTypeUtilisateur = new ComboBox<>();
        comboTypeUtilisateur.getItems().addAll("Employé", "Gestionnaire");
        comboTypeUtilisateur.setPromptText("Type d'utilisateur");
        comboTypeUtilisateur.setStyle("-fx-border-radius: 5px; -fx-border-color: #5A5A5A;");

        // Boutons d'action
        Button btnAjouter = new Button("Ajouter");
        btnAjouter.setOnAction(e -> ajouterUtilisateur());
        btnAjouter.setPrefWidth(120);
        btnAjouter.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 5px;");

        Button btnRechercher = new Button("Rechercher");
        btnRechercher.setOnAction(e -> rechercherUtilisateur());
        btnRechercher.setPrefWidth(120);
        btnRechercher.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-border-radius: 5px;");

        Button btnRetourner = new Button("Retourner");
        btnRetourner.setOnAction(e -> retournerAInterfacePrincipale(primaryStage));
        btnRetourner.setPrefWidth(120);
        btnRetourner.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-border-radius: 5px;");


        // Disposition des boutons avec HBox pour espacement
        HBox hBoxButtons = new HBox(15, btnAjouter, btnRechercher, btnRetourner);
        hBoxButtons.setPadding(new Insets(10, 0, 10, 0));

        // Ajout des champs et ComboBox dans la grille
        grid.add(new Label("Nom et Prénom:"), 0, 0);
        grid.add(txtNom, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(txtEmail, 1, 1);
        grid.add(new Label("Mot de passe:"), 0, 2);
        grid.add(txtMotDePasse, 1, 2);
        grid.add(new Label("Type d'utilisateur:"), 0, 3);
        grid.add(comboTypeUtilisateur, 1, 3);

        // Ajout de HBox avec les boutons
        grid.add(hBoxButtons, 1, 4);

        // TableView pour afficher les utilisateurs
        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setPrefHeight(250);
        tableView.setStyle("-fx-background-color: #ffffff;");
        Label labelVide = new Label("Aucun utilisateur");
        tableView.setPlaceholder(labelVide);

        TableColumn<Utilisateur, Integer> colIndex = new TableColumn<>("Index");
        colIndex.setCellFactory(column -> new TableCell<Utilisateur, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.valueOf(getIndex() + 1));
                }
            }
        });
        colIndex.setPrefWidth(50);

        TableColumn<Utilisateur, String> colNom = new TableColumn<>("Nom et Prénom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNom.setPrefWidth(150);

        TableColumn<Utilisateur, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setPrefWidth(250);

        TableColumn<Utilisateur, String> colMotDePasse = new TableColumn<>("Mot de passe");
        colMotDePasse.setCellValueFactory(new PropertyValueFactory<>("motDePasse"));
        colMotDePasse.setPrefWidth(150);

        TableColumn<Utilisateur, String> colTypeUtilisateur = new TableColumn<>("Type d'utilisateur");
        colTypeUtilisateur.setCellValueFactory(new PropertyValueFactory<>("typeUtilisateur"));
        colTypeUtilisateur.setPrefWidth(150);

        TableColumn<Utilisateur, Void> colActions = new TableColumn<>("Actions");
        colActions.setPrefWidth(250);
        colActions.setCellFactory(new Callback<TableColumn<Utilisateur, Void>, TableCell<Utilisateur, Void>>() {
            @Override
            public TableCell<Utilisateur, Void> call(TableColumn<Utilisateur, Void> param) {
                return new TableCell<Utilisateur, Void>() {
                    private final Button btnModifier = new Button("Modifier");
                    private final Button btnSupprimer = new Button("Supprimer");

                    {
                        btnModifier.setOnAction(e -> modifierUtilisateur(getTableView().getItems().get(getIndex())));
                        btnSupprimer.setOnAction(e -> supprimerUtilisateur(getTableView().getItems().get(getIndex())));
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hBox = new HBox(10, btnModifier, btnSupprimer);
                            setGraphic(hBox);
                        }
                    }
                };
            }
        });
        tableView.getColumns().addAll(colIndex, colNom, colEmail, colMotDePasse, colTypeUtilisateur, colActions);

        // Ajout de la TableView dans le GridPane
        grid.add(tableView, 0, 5, 2, 1);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(grid);

        Scene scene = new Scene(borderPane, 1000, 900);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Charger les utilisateurs au démarrage
        listerUtilisateurs();
    }

    private void ajouterUtilisateur() {
        String nom = txtNom.getText();
        String email = txtEmail.getText();
        String motDePasse = txtMotDePasse.getText();
        String typeUtilisateur = comboTypeUtilisateur.getValue();

        // Vérification des champs vides
        if (nom.isEmpty() || email.isEmpty() || motDePasse.isEmpty() || typeUtilisateur == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Tous les champs doivent être remplis.");
            alert.showAndWait();
            return;  // Sortir de la méthode si des champs sont vides
        }

        // Vérification de la validité de l'email
        if (!UtilisateurDAO.validerEmail(email)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Email invalide");
            alert.setHeaderText(null);
            alert.setContentText("L'email fourni n'est pas valide. L'email valide doit contenir un '@' suivi d'un domaine, comme exemple@domaine.com.");
            alert.showAndWait();
            return;  // Sortir de la méthode si l'email est invalide
        }

        // Vérifiez si l'utilisateur existe déjà
        if (UtilisateurDAO.utilisateurExiste(email)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Utilisateur existant");
            alert.setHeaderText(null);
            alert.setContentText("Cet email est déjà associé à un utilisateur.");
            alert.showAndWait();
            return;
        }

        // Ajout de l'utilisateur si tout est valide
        Utilisateur utilisateur = new Utilisateur(nom, email, motDePasse, typeUtilisateur);
        UtilisateurDAO.ajouterUtilisateur(utilisateur);
        tableView.getItems().add(utilisateur); // Met à jour la TableView

        // Nettoyer les champs
        txtNom.clear();
        txtEmail.clear();
        txtMotDePasse.clear();
        comboTypeUtilisateur.setValue(null);
    }




    private void listerUtilisateurs() {
        List<Utilisateur> utilisateurs = UtilisateurDAO.listerUtilisateurs();
        tableView.getItems().clear();
        tableView.getItems().addAll(utilisateurs);
    }

    private void rechercherUtilisateur() {
        // Crée une nouvelle fenêtre pour l'interface de recherche
        InterfaceRechercheUtilisateur rechercheInterface = new InterfaceRechercheUtilisateur();
        Stage rechercheStage = new Stage();
        rechercheInterface.start(rechercheStage);
    }
    private void modifierUtilisateur(Utilisateur utilisateur) {
        new InterfaceModifUtilisateur(utilisateur, tableView.getItems());
    }

    private void supprimerUtilisateur(Utilisateur utilisateur) {
        if (utilisateur != null) {
            // Créer une alerte de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Êtes-vous sûr de vouloir supprimer l'utilisateur : " + utilisateur.getNom() + " ?");
            alert.setContentText("Cette action est irréversible.");

            // Ajouter les boutons "Confirmer" et "Annuler"
            ButtonType btnConfirmer = new ButtonType("Confirmer");
            ButtonType btnAnnuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(btnConfirmer, btnAnnuler);

            // Afficher l'alerte et attendre la réponse de l'utilisateur
            alert.showAndWait().ifPresent(response -> {
                if (response == btnConfirmer) {
                    // Si l'utilisateur confirme, supprimer l'utilisateur
                    UtilisateurDAO.supprimerUtilisateur(utilisateur);
                    listerUtilisateurs();  // Mettre à jour la liste des utilisateurs dans la TableView
                }
                // Si l'utilisateur annule, rien ne se passe
            });
        } else {
            showAlert("Erreur", "Veuillez sélectionner un utilisateur.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void retournerAInterfacePrincipale(Stage primaryStage) {
        InterfacePrincipale interfacePrincipale = new InterfacePrincipale();
        try {
            interfacePrincipale.start(primaryStage); // Remarque : vous utilisez primaryStage ici pour garder le même stage.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}