package Model ;
import javafx.beans.property.*;

public class Produit {
    private final StringProperty ref;
    private final StringProperty nom;
    private final IntegerProperty qte;
    private final FloatProperty prix;

    // Constructeur
    public Produit(String ref, String nom, int qte, float prix) {
        this.ref = new SimpleStringProperty(ref);
        this.nom = new SimpleStringProperty(nom);
        this.qte = new SimpleIntegerProperty(qte);
        this.prix = new SimpleFloatProperty(prix);
    }

    // Getters et Setters
    public String getRef() {
        return ref.get();
    }

    public void setRef(String ref) {
        this.ref.set(ref);
    }

    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public int getQte() {
        return qte.get();
    }

    public void setQte(int qte) {
        this.qte.set(qte);
    }

    public float getPrix() {
        return prix.get();
    }

    public void setPrix(float prix) {
        this.prix.set(prix);
    }

    // Propriétés pour JavaFX
    public StringProperty nomProperty() {
        return nom;
    }

    public StringProperty refProperty() {
        return ref;
    }

    public IntegerProperty qteProperty() {
        return qte;
    }

    public FloatProperty prixProperty() {
        return prix;
    }
}

