package Vue;

import Controller.ProduitDAO;
import Model.Produit;
import Vue.InterfaceModifProduit;
import Vue.InterfacePrincipale;
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

public class InterfaceProduitsG extends Application {
    private TextField txtNom;
    private TextField txtQte;
    private TextField txtPrix;
    private TextField txtRef;  // Nouveau champ pour la référence
    private TableView<Produit> tableView;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gestion des Produits");

        // Vérification de la table produits
        if (!ProduitDAO.verifierTableProduits()) {
            ProduitDAO.creerTableProduits();
        }

        // Charger les produits depuis la base de données
        List<Produit> produits = ProduitDAO.listerProduits();
        ObservableList<Produit> produitsObservable = FXCollections.observableArrayList(produits);

        // Layout principal
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Formulaire en haut
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(15);
        grid.setHgap(10);
        grid.setStyle("-fx-background-color: #f4f4f9;");

        txtRef = new TextField();
        txtRef.setPromptText("Référence");
        txtRef.setStyle("-fx-border-radius: 5px; -fx-border-color: #5A5A5A;");
        txtRef.setMinWidth(100);  // Largeur minimale
        txtRef.setMaxWidth(400);  // Largeur maximale



        txtNom = new TextField();
        txtNom.setPromptText("Nom");
        txtNom.setStyle("-fx-border-radius: 5px; -fx-border-color: #5A5A5A;");


        txtQte = new TextField();
        txtQte.setPromptText("Quantité");
        txtQte.setStyle("-fx-border-radius: 5px; -fx-border-color: #5A5A5A;");


        txtPrix = new TextField();
        txtPrix.setPromptText("Prix");
        txtPrix.setStyle("-fx-border-radius: 5px; -fx-border-color: #5A5A5A;");




        grid.add(new Label("Référence:"), 0, 0);
        grid.add(txtRef, 1, 0);
        grid.add(new Label("Nom:"), 0, 1);
        grid.add(txtNom, 1, 1);
        grid.add(new Label("Quantité:"), 0, 2);
        grid.add(txtQte, 1, 2);
        grid.add(new Label("Prix:"), 0, 3);
        grid.add(txtPrix, 1, 3);

        // Boutons d'action
        Button btnAjouter = new Button("Ajouter");
        btnAjouter.setOnAction(e -> ajouterProduit());
        btnAjouter.setPrefWidth(120);
        btnAjouter.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 5px;");
        Button btnRechercher = new Button("Rechercher");
        btnRechercher.setPrefWidth(120);
        btnRechercher.setOnAction(e -> rechercherProduitsG());
        btnRechercher.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-border-radius: 5px;");
        Button btnRetour = new Button("Retourner");
        btnRetour.setPrefWidth(120);
        btnRetour.setOnAction(e -> retournerAInterfacePrincipaleGestonnaire(primaryStage));
        btnRetour.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-border-radius: 5px;");


        HBox buttonBox = new HBox(10, btnAjouter, btnRechercher, btnRetour);
        buttonBox.setPadding(new Insets(10));
        grid.add(buttonBox, 0, 4, 2, 1);

        root.setTop(grid);

        // TableView pour afficher les produits
        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setPrefHeight(250);
        tableView.setStyle("-fx-background-color: #ffffff;");
        tableView.setPlaceholder(new Label("Aucun produit disponible"));

        TableColumn<Produit, Integer> colIndex = new TableColumn<>("Index");
        colIndex.setCellFactory(column -> new TableCell<Produit, Integer>() {
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

        TableColumn<Produit, String> colRef = new TableColumn<>("Référence");
        colRef.setCellValueFactory(new PropertyValueFactory<>("ref"));
        colRef.setPrefWidth(150);

        TableColumn<Produit, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNom.setPrefWidth(150);

        TableColumn<Produit, Integer> colQte = new TableColumn<>("Quantité");
        colQte.setCellValueFactory(new PropertyValueFactory<>("qte"));
        colQte.setPrefWidth(150);

        TableColumn<Produit, Float> colPrix = new TableColumn<>("Prix");
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colPrix.setPrefWidth(150);

        TableColumn<Produit, Void> colActions = new TableColumn<>("Actions");
        colActions.setPrefWidth(250);
        colActions.setCellFactory(new Callback<TableColumn<Produit, Void>, TableCell<Produit, Void>>() {
            @Override
            public TableCell<Produit, Void> call(TableColumn<Produit, Void> param) {
                return new TableCell<Produit, Void>() {
                    private final Button btnModifier = new Button("Modifier");
                    private final Button btnSupprimer = new Button("Supprimer");

                    {
                        btnModifier.setOnAction(e -> modifierProduit(getTableView().getItems().get(getIndex())));
                        btnSupprimer.setOnAction(e -> supprimerProduit(getTableView().getItems().get(getIndex())));
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbox = new HBox(10, btnModifier, btnSupprimer);
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });

        tableView.getColumns().addAll(colIndex, colRef, colNom, colQte, colPrix, colActions);
        tableView.setItems(produitsObservable);
        root.setCenter(tableView);

        // Créer la scène
        Scene scene = new Scene(root, 1000, 900);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void ajouterProduit() {
        String nom = txtNom.getText();
        String qteText = txtQte.getText();
        String prixText = txtPrix.getText();
        String ref = txtRef.getText();

        if (nom.isEmpty() || qteText.isEmpty() || prixText.isEmpty() || ref == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Tous les champs doivent être remplis.");
            alert.showAndWait();
            return;  // Sortir de la méthode si des champs sont vides
        }

        // Check if the quantity and price are valid numeric values
        if (!isNumeric(qteText)) {
            showAlert("Erreur", "La quantité doit être un nombre.");
            return;
        }

        if (!isNumeric(prixText)) {
            showAlert("Erreur", "Le prix doit être un nombre.");
            return;
        }


        // Convert them to numeric values after validation
        int qte = Integer.parseInt(qteText);
        float prix = Float.parseFloat(prixText);

        if (nom.isEmpty() || qteText.isEmpty() || prixText.isEmpty() || ref.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        Produit produit = new Produit(ref, nom, qte, prix);

        if (ProduitDAO.ProduitExiste(ref)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Produit existant");
            alert.setHeaderText(null);
            alert.setContentText("Cette référence est déjà associée à un produit existant.");
            alert.showAndWait();
            return;
        }

        ProduitDAO.ajouterProduit(produit);
        tableView.getItems().add(produit); // Update the TableView
        txtRef.clear();
        txtNom.clear();
        txtQte.clear();
        txtPrix.clear();
    }


    private void listerProduits() {
        List<Produit> produits = ProduitDAO.listerProduits();
        tableView.getItems().clear();
        tableView.getItems().setAll(produits);
    }

    private void rechercherProduitsG() {
        InterfaceRechercheProduitG rechercheInterface = new InterfaceRechercheProduitG();
        Stage rechercheStage = new Stage();
        rechercheInterface.start(rechercheStage);
    }

    private void modifierProduit(Produit produit) {
        new InterfaceModifProduit(produit, tableView.getItems());
    }
    private void supprimerProduit(Produit produit) {
        if (produit != null) {
            // Créer une alerte de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ?");
            alert.setContentText("Produit: " + produit.getNom() + " (Référence: " + produit.getRef() + ")");

            // Attente de la réponse de l'utilisateur
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Si l'utilisateur clique sur OK, supprimer le produit
                    ProduitDAO.supprimerProduit(produit);
                    listerProduits(); // Met à jour la liste des produits dans la table
                }
            });
        } else {
            showAlert("Erreur", "Veuillez sélectionner un produit.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void retournerAInterfacePrincipaleGestonnaire(Stage primaryStage) {
        // Remplace l'interface Produit par l'interface principale
        InterfacePrincipaleGestionnaire interfacePrincipaleG = new InterfacePrincipaleGestionnaire();
        try {
            interfacePrincipaleG.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);  // Try to convert the string to a number
            return true;
        } catch (NumberFormatException e) {
            return false;  // Return false if it's not a valid number
        }
    }
}
