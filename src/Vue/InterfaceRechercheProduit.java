package Vue;

import Controller.ProduitDAO;
import Model.Produit;
import Vue.InterfaceProduits;
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
public class InterfaceRechercheProduit extends Application{
    private TextField txtRechercheRef;
    private TextField txtRechercheNom;
    private Button btnRechercher;
    private Button btnAnnuler;
    private TableView<Produit> tableView;
    private ObservableList<Produit> produitObservable;

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Recherche Produit");
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        Label labelTitre = new Label("Rechercher un produit");
        labelTitre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10 0 20 0;");
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.TOP_CENTER);

        txtRechercheRef = new TextField();
        txtRechercheRef.setPromptText("Entrez la référence");
        txtRechercheRef.setPrefWidth(350);

        txtRechercheNom = new TextField();
        txtRechercheNom.setPromptText("Entrez le Nom du produit");
        txtRechercheNom.setPrefWidth(350);

        vbox.getChildren().addAll(labelTitre, txtRechercheNom, txtRechercheRef);

        HBox hboxButtons = new HBox(20);
        hboxButtons.setAlignment(Pos.CENTER);
        hboxButtons.setPadding(new Insets(20, 0, 20, 0));

        btnRechercher = new Button("Trouver");
        btnRechercher.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");

        btnAnnuler = new Button("Retourner");
        btnAnnuler.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px;");

        hboxButtons.getChildren().addAll(btnRechercher, btnAnnuler);

        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Produit, String> colRef = new TableColumn<>("Référence");
        colRef.setCellValueFactory(cellData -> cellData.getValue().refProperty());
        colRef.setPrefWidth(150);

        TableColumn<Produit, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        colNom.setPrefWidth(200);

        TableColumn<Produit, Integer> colQte = new TableColumn<>("Quantité");
        colQte.setCellValueFactory(cellData -> cellData.getValue().qteProperty().asObject()); // Utilisation de asObject() pour Integer
        colQte.setPrefWidth(150);

        TableColumn<Produit, Float> colPrix = new TableColumn<>("Prix");
        colPrix.setCellValueFactory(cellData -> cellData.getValue().prixProperty().asObject()); // Utilisation de asObject() pour Integer
        colPrix.setPrefWidth(150);

        tableView.getColumns().addAll(colRef,colNom,colQte,colPrix);
        tableView.setPlaceholder(new Label("Aucun résultat trouvé."));

        btnRechercher.setOnAction(event -> {
            String ref = txtRechercheRef.getText().trim();
            String nom = txtRechercheNom.getText().trim();

            List<Produit> produits = trouverProduit(ref,nom);

            if (produits == null || produits.isEmpty()) {
                showAlert("Aucun produit trouvé.");
            } else {
                mettreAJourTableView(produits);
            }
            clearFields();
        });

        btnAnnuler.setOnAction(event -> {
            clearFields(); // Clear fields when "Annuler" is clicked
            returnToInterfaceProduit(primaryStage); // Switch to the main screen
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
    private void mettreAJourTableView(List<Produit> produits) {
        produitObservable = FXCollections.observableArrayList(produits);
        tableView.setItems(produitObservable);
    }
    private List<Produit> trouverProduit(String ref, String nom) {
        ProduitDAO produitDAO = new ProduitDAO();
        return produitDAO.trouverProduit(ref, nom);
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Résultat Recherche");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void clearFields() {
        txtRechercheRef.clear();
        txtRechercheNom.clear();
    }
    private void returnToInterfaceProduit(Stage primaryStage) {
        InterfaceProduits interfaceProduit = new InterfaceProduits();
        try {
            interfaceProduit.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
