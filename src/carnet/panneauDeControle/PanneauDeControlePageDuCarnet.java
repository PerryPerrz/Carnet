package carnet.panneauDeControle;

import carnet.designPattern.Observateur;
import carnet.model.CarnetDeVoyage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * La classe PanneauDeControlePageDuCarnet
 */
public class PanneauDeControlePageDuCarnet implements Observateur {
    private final CarnetDeVoyage carnet;
    //private MapView carte;
    @FXML
    private Label titre;
    @FXML
    private TextArea zoneDeTexte;

    /**
     * Constructeur de la classe PanneauDeControlePageDuCarnet
     */
    public PanneauDeControlePageDuCarnet(CarnetDeVoyage car2voy) {
        this.carnet = car2voy;
        this.carnet.ajouterObservateur(this);
    }

    /**
     * Procédure ajouterTitre
     */
    public void ajouterTitre() {
        //Je récupère le titre que l'utilisateur saisit
        String titre = this.titre.getText();
        this.carnet.getPageDePresentation().setTitre(titre);
    }

    /**
     * Procédure ajouterTexte
     */
    public void ajouterTexte() {
        //Je récupère le texte que l'utilisateur saisit
        String texte = this.zoneDeTexte.getText();
        this.carnet.getPageDuCarnet().setTexte(texte);
    }

    /**
     * Procédure NouvellePage
     */
    public void nouvellePage() {
        this.carnet.ajouterPage();
    }

    /**
     * Procédure PageSuivante
     */
    public void pageSuivante() {
        this.carnet.pageSuivante();
    }

    /**
     * Procédure PagePrecedente
     */
    public void pagePrecedente() {
        this.carnet.pagePrecedente();
    }

    /**
     * Procédure SetImage
     */
    public void setImage() {

    }

    /**
     * Sets map
     */
    public void setMap() {

    }

    @Override
    public void reagir() {

    }
}
