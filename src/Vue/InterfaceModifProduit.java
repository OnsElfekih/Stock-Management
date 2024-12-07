package Vue;

import Controller.ProduitDAO;
import Model.Produit;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.collections.ObservableList;



public class InterfaceModifProduit {
    private Produit produitSelectionne;
    private TextField txtNom;
    private TextField txtQte;
    private TextField txtPrix;
    private TextField txtRef;
    private Button btnEnregistrer;
    private Button btnAnnuler;
    private ObservableList<Produit> produitsObservable;


    public InterfaceModifProduit(Produit produit, ObservableList<Produit> produitsObservable) {
        this.produitSelectionne = produit;
        this.produitsObservable = produitsObservable;
        Stage stage = new Stage();
        stage.setTitle("Modifier un Produit");

        // Layout pour la modification
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(15);
        grid.setHgap(10);

        // Champs de texte pour le produit
        txtRef = new TextField(produit.getRef());
        txtRef.setTooltip(new Tooltip("Entrez la référence du produit"));

        txtNom = new TextField(produit.getNom());
        txtNom.setTooltip(new Tooltip("Entrez le nom et le prénom du produit"));

        txtQte = new TextField(String.valueOf(produit.getQte()));
        txtQte.setTooltip(new Tooltip("Entrez la quantité du produit"));

        txtPrix = new TextField(String.valueOf(produit.getPrix()));
        txtPrix.setTooltip(new Tooltip("Entrez le prix du produit"));


        // Bouton "Enregistrer"
        btnEnregistrer = new Button("Enregistrer");
        btnEnregistrer.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");  // Style du bouton
        btnEnregistrer.setOnAction(e -> enregistrerChangements(stage));

        // Bouton "Annuler"
        btnAnnuler = new Button("Annuler");
        btnAnnuler.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");  // Style du bouton
        btnAnnuler.setOnAction(e -> stage.close());

        // Disposition des champs
        grid.add(new Label("Référence:"), 0, 0);
        grid.add(txtRef, 1, 0);
        grid.add(new Label("Nom:"), 0, 1);
        grid.add(txtNom, 1, 1);
        grid.add(new Label("Quantité:"), 0, 2);
        grid.add(txtQte, 1, 2);
        grid.add(new Label("Prix:"), 0, 3);
        grid.add(txtPrix, 1, 3);

        // Boutons
        HBox hBoxButtons = new HBox(20, btnEnregistrer, btnAnnuler);
        hBoxButtons.setPadding(new Insets(10, 0, 0, 0));  // Espacement au-dessus des boutons
        grid.add(hBoxButtons, 1, 4);  // Ajouter la HBox à la grille

        // Ajouter un espacement supplémentaire autour de la fenêtre
        Scene scene = new Scene(grid, 450, 350);
        stage.setScene(scene);
        stage.show();
    }

    private void enregistrerChangements(Stage stage) {
        if (txtNom.getText().isEmpty() || txtQte.getText().isEmpty() || txtPrix.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Tous les champs doivent être remplis.");
            alert.showAndWait();
            return;
        }

        try {
            int qte = Integer.parseInt(txtQte.getText());
            float prix = Float.parseFloat(txtPrix.getText());

            produitSelectionne.setNom(txtNom.getText());
            produitSelectionne.setQte(qte);
            produitSelectionne.setPrix(prix);

            ProduitDAO.modifierProduit(produitSelectionne);

            // Mettre à jour l'ObservableList
            int index = produitsObservable.indexOf(produitSelectionne);
            if (index >= 0) {
                produitsObservable.set(index, produitSelectionne);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification réussie");
            alert.setHeaderText(null);
            alert.setContentText("Les informations du produit ont été modifiées avec succès.");
            alert.showAndWait();

            stage.close();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Format invalide");
            alert.setHeaderText(null);
            alert.setContentText("La quantité et le prix doivent être des valeurs numériques.");
            alert.showAndWait();
        }
    }

}
