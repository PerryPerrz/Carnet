package carnet.panneauDeControle;

import carnet.designPattern.Observateur;
import carnet.exceptions.CarnetException;
import carnet.exceptions.PageInexistanteException;
import carnet.model.CarnetDeVoyage;

/**
 * La classe PanneauDeControleMenu
 */
public class PanneauDeControleMenu implements Observateur {
    private CarnetDeVoyage carnet;

    /**
     * Constructeur de la classe PanneauDeControleMenu
     *
     * @param car2voy the car 2 voy
     */
    public PanneauDeControleMenu(CarnetDeVoyage car2voy) {
        this.carnet = car2voy;
    }

    /**
     * Procédure enregistrer
     */
    public void enregistrer() {
        carnet.enregistrerCarnet();
    }

    /**
     * Procédure nouveau
     */
    public void nouveau() {
        //Créer un nouveau carnet et load une nouvelle fenêtre (fxml) sur le nouveau carnet
    }

    /**
     * Procédure renommer
     *
     * @param nouveauNom le nouveau nom
     */
    public void renommer(String nouveauNom) {
        this.carnet.renommerCarnet(nouveauNom);
    }

    /**
     * Procédure supprimer page
     *
     * @param numDeLaPageASupprimer entier correspondant au numéro de la page à supprimer du carnet
     * @throws PageInexistanteException La page inexistante exception
     */
    public void supprimer(int numDeLaPageASupprimer) throws PageInexistanteException{
        try {
            this.carnet.supprimerPage(numDeLaPageASupprimer);
        } catch (CarnetException e) {
            throw new PageInexistanteException("La page que vous voulez supprimer n'existe pas");
        }
    }

    @Override
    public void reagir() {

    }
}
