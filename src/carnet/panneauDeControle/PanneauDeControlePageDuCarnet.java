package carnet.panneauDeControle;

import carnet.designPattern.Observateur;
import carnet.exceptions.PageInexistanteException;
import carnet.model.CarnetDeVoyage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

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
    private void ajouterTitre() {
        //Je récupère le titre que l'utilisateur saisit
        String titre = this.titre.getText();
        this.carnet.getPageDePresentation().setTitre(titre);
    }

    /**
     * Procédure ajouterTexte
     */
    private void ajouterTexte() {
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

    /**
     * Procédure enregistrer qui sauvegarde les informations rentrées par l'utilisateur
     */
    public void enregistrer() {
        this.ajouterTitre();
        this.ajouterTexte();
    }

    @Override
    public void reagir() {
        if (!carnet.siLaPageActuelleEstLaPageDePresentation()) { //On à pas toujours une page du carnet.
            try {
                titre.setText(this.carnet.getPageDuCarnetAvecUnNumero(this.carnet.getPageActuelle()).getTitre());
            } catch (PageInexistanteException e) {
                e.printStackTrace();
            }
        }
    }
}
