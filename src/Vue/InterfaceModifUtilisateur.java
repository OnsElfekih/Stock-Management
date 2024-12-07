package Vue;

import Controller.UtilisateurDAO;
import Model.Utilisateur;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.collections.ObservableList;

public class InterfaceModifUtilisateur {

    private Utilisateur utilisateurSelectionne;
    private TextField txtNom;
    private TextField txtEmail;
    private TextField txtMotDePasse;
    private ComboBox<String> comboTypeUtilisateur;
    private Button btnEnregistrer;
    private Button btnAnnuler;
    private ObservableList<Utilisateur> utilisateursObservable;

    public InterfaceModifUtilisateur(Utilisateur utilisateur, ObservableList<Utilisateur> utilisateursObservable) {
        this.utilisateurSelectionne = utilisateur;
        this.utilisateursObservable = utilisateursObservable;
        Stage stage = new Stage();
        stage.setTitle("Modifier l'Utilisateur");

        // Layout pour la modification
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));  // Ajouter de l'espace autour du contenu
        grid.setVgap(15);
        grid.setHgap(10);

        // Champs de texte pour l'utilisateur avec des Tooltips pour plus d'explications
        txtNom = new TextField(utilisateur.getNom());
        txtNom.setTooltip(new Tooltip("Entrez le nom et le prénom de l'utilisateur"));

        txtEmail = new TextField(utilisateur.getEmail());
        txtEmail.setTooltip(new Tooltip("Entrez l'email de l'utilisateur"));

        txtMotDePasse = new TextField(utilisateur.getMotDePasse());
        txtMotDePasse.setTooltip(new Tooltip("Entrez le mot de passe de l'utilisateur"));

        comboTypeUtilisateur = new ComboBox<>();
        comboTypeUtilisateur.getItems().addAll("Employé", "Gestionnaire");
        comboTypeUtilisateur.setValue(utilisateur.getTypeUtilisateur());
        comboTypeUtilisateur.setTooltip(new Tooltip("Sélectionnez le type d'utilisateur"));

        // Bouton "Enregistrer"
        btnEnregistrer = new Button("Enregistrer");
        btnEnregistrer.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");  // Style du bouton
        btnEnregistrer.setOnAction(e -> enregistrerChangements(stage));

        // Bouton "Annuler"
        btnAnnuler = new Button("Annuler");
        btnAnnuler.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");  // Style du bouton
        btnAnnuler.setOnAction(e -> stage.close());

        // Disposition des champs dans le GridPane
        grid.add(new Label("Nom et Prénom :"), 0, 0);
        grid.add(txtNom, 1, 0);
        grid.add(new Label("Email :"), 0, 1);
        grid.add(txtEmail, 1, 1);
        grid.add(new Label("Mot de passe :"), 0, 2);
        grid.add(txtMotDePasse, 1, 2);
        grid.add(new Label("Type d'utilisateur :"), 0, 3);
        grid.add(comboTypeUtilisateur, 1, 3);

        // Ajouter les boutons dans une HBox pour les aligner horizontalement
        HBox hBoxButtons = new HBox(20, btnEnregistrer, btnAnnuler);
        hBoxButtons.setPadding(new Insets(10, 0, 0, 0));  // Espacement au-dessus des boutons
        grid.add(hBoxButtons, 1, 4);  // Ajouter la HBox à la grille

        // Ajouter un espacement supplémentaire autour de la fenêtre
        Scene scene = new Scene(grid, 450, 350);
        stage.setScene(scene);
        stage.show();
    }

    private void enregistrerChangements(Stage stage) {
        // Valider les champs avant de modifier l'utilisateur
        if (txtNom.getText().isEmpty() || txtEmail.getText().isEmpty() || txtMotDePasse.getText().isEmpty() || comboTypeUtilisateur.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Tous les champs doivent être remplis.");
            alert.showAndWait();
            return;
        }

        // Vérification de la validité de l'email
        String email = txtEmail.getText();
        if (!UtilisateurDAO.validerEmail(email)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Email invalide");
            alert.setHeaderText(null);
            alert.setContentText("L'email fourni n'est pas valide. L'email valide doit contenir un '@' suivi d'un domaine, comme exemple@domaine.com.");
            alert.showAndWait();
            return;  // Sortir de la méthode si l'email est invalide
        }

        // Appliquer les changements à l'utilisateur sélectionné
        utilisateurSelectionne.setNom(txtNom.getText());
        utilisateurSelectionne.setEmail(email);  // Mise à jour de l'email
        utilisateurSelectionne.setMotDePasse(txtMotDePasse.getText());
        utilisateurSelectionne.setTypeUtilisateur(comboTypeUtilisateur.getValue());

        // Enregistrer les changements dans la base de données
        UtilisateurDAO.modifierUtilisateur(utilisateurSelectionne);

        // Mettre à jour l'ObservableList
        int index = utilisateursObservable.indexOf(utilisateurSelectionne);
        if (index >= 0) {
            utilisateursObservable.set(index, utilisateurSelectionne);
        }

        // Afficher une alerte de confirmation
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modification réussie");
        alert.setHeaderText(null);
        alert.setContentText("Les informations de l'utilisateur ont été modifiées avec succès.");
        alert.showAndWait();

        // Fermer la fenêtre de modification
        stage.close();
    }
}
