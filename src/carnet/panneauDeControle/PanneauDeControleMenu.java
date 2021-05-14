package carnet.panneauDeControle;

import carnet.designPattern.Observateur;
import carnet.exceptions.CarnetException;
import carnet.exceptions.SupprimerPageDePresentationException;
import carnet.model.CarnetDeVoyage;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.util.Duration;

import java.util.Optional;

/**
 * La classe PanneauDeControleMenu
 */
public class PanneauDeControleMenu implements Observateur {
    private CarnetDeVoyage carnet;

    /**
     * Constructeur de la classe PanneauDeControleMenu
     *
     * @param car2voy le carnet
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
     */
    public void renommer() {
        TextInputDialog dialog = new TextInputDialog("Renommer le carnet");
        dialog.setTitle("Renommer le carnet");
        dialog.setHeaderText("Entrez le nouveau nom :");
        dialog.setContentText("Nom :");

        Optional<String> res = dialog.showAndWait();
        res.ifPresent(this.carnet::renommerCarnet);

    }

    /**
     * Procédure supprimer page
     */
    public void supprimer() {
        try {
            this.carnet.supprimerPage(this.carnet.getPageActuelle());
        } catch (SupprimerPageDePresentationException e) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("SupprimerPageDePresentationException");
            dialog.setHeaderText("Impossible de supprimer la page de présentation");
            dialog.setContentText("Erreur : la page de présentation ne peut pas être supprimé \n" + "Veuillez rééssayer ! ");
            dialog.show();
            //Le chronomètre
            PauseTransition pt = new PauseTransition(Duration.seconds(5));
            pt.setOnFinished(Event -> dialog.close());
            pt.play();
        } catch (CarnetException e) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("PageInexistanteException");
            dialog.setHeaderText("Impossible de trouver la page à supprimer");
            dialog.setContentText("Erreur : la page à supprimer est introuvable \n" + "Veuillez rééssayer ! ");
            dialog.show();
            //Le chronomètre
            PauseTransition pt = new PauseTransition(Duration.seconds(5));
            pt.setOnFinished(Event -> dialog.close());
            pt.play();
        }
    }

    public void quitter(){
        Platform.exit();
    }
    @Override
    public void reagir() {

    }
}
