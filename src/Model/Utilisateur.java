package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Utilisateur {
    private final IntegerProperty id;
    private final StringProperty nom;
    private final StringProperty email;
    private final StringProperty motDePasse;
    private final StringProperty typeUtilisateur;

    // Constructor with ID
    public Utilisateur(int id, String nom, String email, String motDePasse, String typeUtilisateur) {
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.email = new SimpleStringProperty(email);
        this.motDePasse = new SimpleStringProperty(motDePasse);
        this.typeUtilisateur = new SimpleStringProperty(typeUtilisateur);
    }

    // Constructor without ID (optional for creation)
    public Utilisateur(String nom, String email, String motDePasse, String typeUtilisateur) {
        this.id = new SimpleIntegerProperty(0); // Default ID
        this.nom = new SimpleStringProperty(nom);
        this.email = new SimpleStringProperty(email);
        this.motDePasse = new SimpleStringProperty(motDePasse);
        this.typeUtilisateur = new SimpleStringProperty(typeUtilisateur);
    }

    // Getters and setters for ID
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    // Other getters and setters
    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse.get();
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse.set(motDePasse);
    }
    public StringProperty motDePasseProperty() {
        return motDePasse;
    }
    public String getTypeUtilisateur() {
        return typeUtilisateur.get();
    }

    public void setTypeUtilisateur(String typeUtilisateur) {
        this.typeUtilisateur.set(typeUtilisateur);
    }

    public StringProperty typeUtilisateurProperty() {
        return typeUtilisateur;
    }
}

