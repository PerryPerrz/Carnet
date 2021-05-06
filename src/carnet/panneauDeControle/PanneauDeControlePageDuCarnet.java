package carnet.panneauDeControle;

import carnet.designPattern.Observateur;
import carnet.model.CarnetDeVoyage;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * La classe PanneauDeControlePageDuCarnet
 */
public class PanneauDeControlePageDuCarnet implements Observateur {
    private final CarnetDeVoyage carnet;
    //private MapView carte;
    @FXML
    private TextField titre;
    @FXML
    private TextArea zoneDeTexte;

    /**
     * Constructeur de la classe PanneauDeControlePageDuCarnet
     */
    public PanneauDeControlePageDuCarnet(CarnetDeVoyage car2voy) {
        this.carnet = car2voy;
    }

    /**
     * Ajouter titre.
     */
    public void ajouterTitre() {
        //Je récupère le titre que l'utilisateur saisit
        String titre = this.titre.getText();
        this.carnet.getPageDePresentation().setTitre(titre);
    }

    /**
     * Instantiates a new Ajouter texte.
     */
    public void ajouterTexte() {
        //Je récupère le texte que l'utilisateur saisit
        String texte = this.zoneDeTexte.getText();
        this.carnet.getPageDuCarnet().setTexte(texte);
    }

    /**
     * Nouvelle page.
     */
    public void nouvellePage() {
        this.carnet.ajouterPage();
    }

    /**
     * Page suivante.
     */
    public void pageSuivante() {
        this.carnet.pageSuivante();
    }

    /**
     * Page precedente.
     */
    public void pagePrecedente() {
        this.carnet.pagePrecedente();
    }

    /**
     * Sets image.
     */
    public void setImage() {

    }

    /**
     * Sets map.
     */
    public void setMap() {

    }

    @Override
    public void reagir() {

    }
}
