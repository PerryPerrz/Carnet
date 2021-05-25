package carnet.panneauDeControle;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import carnet.designPattern.Observateur;
import carnet.exceptions.CarnetException;
import carnet.exceptions.FichierDeSauvegardeException;
import carnet.exceptions.SupprimerPageDePresentationException;
import carnet.model.CarnetDeVoyage;
import carnet.sauvegarde.SauvegardeDuCarnet;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;

/**
 * La classe PanneauDeControleMenu
 */
public class PanneauDeControleMenu implements Observateur {
    private CarnetDeVoyage carnet;

    @FXML
    BorderPane menu;  //On récupère la VueMenu

    @FXML
    BorderPane pageDePresentation; //On récupère la VuePageDePresentation

    @FXML
    BorderPane pageDuCarnet; //On récupère la VuePageDuCarnet

    /**
     * Constructeur de la classe PanneauDeControleMenu
     *
     * @param car2voy le carnet
     */
    public PanneauDeControleMenu(CarnetDeVoyage car2voy) {
        this.carnet = car2voy;
        this.carnet.ajouterObservateur(this);
    }

    /**
     * Procédure enregistrer
     */
    public void enregistrer() {
        try {
            carnet.enregistrerCarnet();
        } catch (FichierDeSauvegardeException fDSe) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("FichierDeSauvegardeException");
            dialog.setHeaderText("Impossible de créer ou charger le fichier de sauvegarde");
            dialog.setContentText("Erreur : un problème à été rencontré lors de la sauvegarde\n" + "Veuillez rééssayer ! ");
            dialog.show();
            //Le chronomètre
            PauseTransition pt = new PauseTransition(Duration.seconds(5));
            pt.setOnFinished(Event -> dialog.close());
            pt.play();
        }
    }

    /**
     * Procédure nouveau
     */
    public void nouveau() {
        BorderPane root;
        CarnetDeVoyage carnet = new CarnetDeVoyage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../vues/VueMenu.fxml"));
        PanneauDeControleMenu pdcM = new PanneauDeControleMenu(carnet);
        PanneauDeControlePageDePresentation pdcP = new PanneauDeControlePageDePresentation(carnet);
        PanneauDeControlePageDuCarnet pdcC = new PanneauDeControlePageDuCarnet(carnet);

        loader.setControllerFactory(ic -> {
            if (ic.equals(carnet.panneauDeControle.PanneauDeControleMenu.class)) return pdcM;
            else if (ic.equals((carnet.panneauDeControle.PanneauDeControlePageDePresentation.class))) return pdcP;
            else if (ic.equals(carnet.panneauDeControle.PanneauDeControlePageDuCarnet.class)) return pdcC;
            else // par défaut...
                return null;
        });

        try {
            root = loader.load();
            Stage primaryStage = (Stage) this.menu.getScene().getWindow();

            primaryStage.setTitle("Carnet | Hugo Iopeti");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();

            //Animation
            new FadeIn(root).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.carnet.notifierObservateurs();
    }

    /**
     * Procédure renommer
     */
    public void renommer() {
        if(carnet.siLaPageActuelleEstLaPageDePresentation()) { //Si on veut renommer le titre de la page actuelle
            TextInputDialog dialog = new TextInputDialog("Renommer le carnet");
            dialog.setTitle("Renommer le carnet");
            dialog.setHeaderText("Entrez le nouveau nom :");
            dialog.setContentText("Nom :");

            Optional<String> res = dialog.showAndWait();
            res.ifPresent(this.carnet::renommerCarnet);
        }
        else {
            TextInputDialog dialog = new TextInputDialog("Renommer la page");
            dialog.setTitle("Renommer la page");
            dialog.setHeaderText("Entrez le nouveau nom :");
            dialog.setContentText("Nom :");

            Optional<String> res = dialog.showAndWait();
            res.ifPresent(this.carnet::renommerPage);
        }
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

    public void quitter() {
        Platform.exit();
    }

    /**
     * Procédure qui affiche la page regardée actuellement
     */
    public void affichagePage() {
        if (this.carnet.siLaPageActuelleEstLaPageDePresentation()) {
            if (menu.getChildren().contains(this.pageDuCarnet)) {
                this.menu.getChildren().remove(this.pageDuCarnet);
                this.menu.setCenter(this.pageDePresentation);
                //Animation
                new SlideInLeft(this.pageDePresentation).play();
            }
        } else {
            if (menu.getChildren().contains(this.pageDePresentation)) {
                this.menu.getChildren().remove(this.pageDePresentation);
                this.menu.setCenter(this.pageDuCarnet);
            }
            //Animation
            if (this.carnet.isToTheLeft()) {
                new SlideInLeft(this.pageDuCarnet).play();
            } else {
                new SlideInRight(this.pageDuCarnet).play();
            }
        }
    }

    /**
     * Procédure qui ouvre un carnet depuis un fichier de sauvegarde
     */
    public void ouvrirUnCarnet() {
        BorderPane root;
        SauvegardeDuCarnet sauvegarde = SauvegardeDuCarnet.getInstance();
        CarnetDeVoyage carnet = null;
        try {
            carnet = sauvegarde.retranscriptionDuCarnet();
        } catch (FichierDeSauvegardeException e) {
            e.printStackTrace();
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../vues/VueMenu.fxml"));
        PanneauDeControleMenu pdcM = new PanneauDeControleMenu(carnet);
        PanneauDeControlePageDePresentation pdcP = new PanneauDeControlePageDePresentation(carnet);
        PanneauDeControlePageDuCarnet pdcC = new PanneauDeControlePageDuCarnet(carnet);

        loader.setControllerFactory(ic -> {
            if (ic.equals(carnet.panneauDeControle.PanneauDeControleMenu.class)) return pdcM;
            else if (ic.equals((carnet.panneauDeControle.PanneauDeControlePageDePresentation.class))) return pdcP;
            else if (ic.equals(carnet.panneauDeControle.PanneauDeControlePageDuCarnet.class)) return pdcC;
            else // par défaut...
                return null;
        });

        try {
            root = loader.load();
            Stage primaryStage = (Stage) this.menu.getScene().getWindow();

            primaryStage.setTitle("Carnet | Hugo Iopeti");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();

            //Animation
            new FadeIn(root).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
        carnet.notifierObservateurs();
    }

    @Override
    public void reagir() {
        this.affichagePage();
    }
}
