package carnet.panneauDeControle;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import carnet.designPattern.Observateur;
import carnet.exceptions.CarnetException;
import carnet.exceptions.FichierDeSauvegardeException;
import carnet.exceptions.SupprimerPageDePresentationException;
import carnet.model.CarnetDeVoyage;
import carnet.model.PageDuCarnet;
import carnet.outil.TailleComposants;
import carnet.sauvegarde.SauvegardeDuCarnet;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * La classe PanneauDeControleMenu
 */
public class PanneauDeControleMenu implements Observateur {
    private final CarnetDeVoyage carnet;

    /**
     * Le menu
     */
    @FXML
    BorderPane menu;  //On récupère la VueMenu

    /**
     * La page de présentation
     */
    @FXML
    BorderPane pageDePresentation; //On récupère la VuePageDePresentation

    /**
     * La page du carnet
     */
    @FXML
    BorderPane pageDuCarnet; //On récupère la VuePageDuCarnet

    /**
     * L'item supprimer
     */
    @FXML
    MenuItem supprimer; //On récupère le menuItem supprimer

    /**
     * l'item copier
     */
    @FXML
    MenuItem copier; //On récupère le menuItem copier

    /**
     * l'item coller
     */
    @FXML
    MenuItem coller; //On récupère le menuItem coller

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
        Window mainStage = this.menu.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choisir le répértoire de sauvegarde");
        File selectedDir = directoryChooser.showDialog(mainStage);

        TextInputDialog dialog = new TextInputDialog("sauvegardeDuCarnet");
        dialog.setTitle("Nom du fichier de sauvegarde");
        dialog.setHeaderText("Entrez le nom du fichier de sauvegarde : ");
        dialog.setContentText("Nom de la sauvegarde : ");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> {
            try {
                this.carnet.enregistrerCarnet(selectedDir, s);
            } catch (FichierDeSauvegardeException e) {
                Alert dialog2 = new Alert(Alert.AlertType.ERROR);
                dialog2.setTitle("FichierDeSauvegardeException");
                dialog2.setHeaderText("Impossible de créer ou charger le fichier de sauvegarde");
                dialog2.setContentText("Erreur : un problème à été rencontré lors de la sauvegarde\n" + "Veuillez rééssayer ! ");
                dialog2.show();
                //Le chronomètre
                PauseTransition pt = new PauseTransition(Duration.seconds(5));
                pt.setOnFinished(Event -> dialog2.close());
                pt.play();
            }
        });
    }

    /**
     * Procédure nouveau
     */
    public void nouveau() {
        BorderPane root;
        CarnetDeVoyage carnet = new CarnetDeVoyage();
        TailleComposants tc = TailleComposants.getInstance();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/vues/VueMenu.fxml"));
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
            primaryStage.setScene(new Scene(root, tc.getWindowX(), tc.getWindowY()));
            primaryStage.show();

            primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/images/carnet.png"))));

            //Animation
            new FadeIn(root).play();
        } catch (IOException e) {
            e.printStackTrace();
        }
        carnet.notifierObservateurs();
    }

    /**
     * Procédure renommer
     */
    public void renommer() {
        if (carnet.siLaPageActuelleEstLaPageDePresentation()) { //Si on veut renommer le titre de la page actuelle
            TextInputDialog dialog = new TextInputDialog("Renommer le carnet");
            dialog.setTitle("Renommer le carnet");
            dialog.setHeaderText("Entrez le nouveau nom :");
            dialog.setContentText("Nom :");

            Optional<String> res = dialog.showAndWait();
            res.ifPresent(this.carnet::renommerCarnet);
        } else {
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

    /**
     * Procédure quitter
     */
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
        TailleComposants tc = TailleComposants.getInstance();
        try {
            Window mainStage = this.menu.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Ouvrir le fichier de sauvegarde");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sauvegarde", "*.json"));
            File selectedFile = fileChooser.showOpenDialog(mainStage);
            if (selectedFile == null)
                throw new FichierDeSauvegardeException("Impossible de charger un carnet");

            CarnetDeVoyage carnet = sauvegarde.retranscriptionDuCarnet(selectedFile);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/vues/VueMenu.fxml"));
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
                primaryStage.setScene(new Scene(root, tc.getWindowX(), tc.getWindowY()));
                primaryStage.show();

                primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/images/carnet.png"))));

                //Animation
                new FadeIn(root).play();
            } catch (IOException e) {
                e.printStackTrace();
            }
            carnet.notifierObservateurs();
        } catch (FichierDeSauvegardeException e) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("FichierDeSauvegardeException");
            dialog.setHeaderText("Impossible de charger un carnet");
            dialog.setContentText("Erreur : Aucune sauvegarde n'a été trouvée ! \n" + "Veuillez rééssayer ! ");
            dialog.show();
            //Le chronomètre
            PauseTransition pt = new PauseTransition(Duration.seconds(5));
            pt.setOnFinished(Event -> dialog.close());
            pt.play();
        }
    }

    /**
     * Procédure qui copie une page du carnet
     */
    public void copier() {
        SauvegardeDuCarnet sauvegarde = SauvegardeDuCarnet.getInstance();
        sauvegarde.sauvegardeDUnePage(this.carnet.getPageDuCarnet());
    }

    /**
     * Procédure qui colle une page du carnet
     */
    public void coller() {
        SauvegardeDuCarnet sauvegarde = SauvegardeDuCarnet.getInstance();
        try {
            PageDuCarnet page = sauvegarde.retranscriptionDUnePageDuCarnet();
            this.carnet.pageSuivante();
            this.carnet.retranscriptionDesInformationsDUnePageDuCarnet(page);
        } catch (FichierDeSauvegardeException e) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("FichierDeSauvegardeException");
            dialog.setHeaderText("Impossible de charger la page du carnet");
            dialog.setContentText("Erreur : Aucune sauvegarde n'a été trouvée ! \n" + "Veuillez rééssayer ! ");
            dialog.show();
            //Le chronomètre
            PauseTransition pt = new PauseTransition(Duration.seconds(5));
            pt.setOnFinished(Event -> dialog.close());
            pt.play();
        }
        this.carnet.notifierObservateurs();
    }

    @Override
    public void reagir() {
        this.supprimer.setDisable(this.carnet.siLaPageActuelleEstLaPageDePresentation());
        this.copier.setDisable(this.carnet.siLaPageActuelleEstLaPageDePresentation());
        this.coller.setDisable(this.carnet.siLaPageActuelleEstLaPageDePresentation());
        this.affichagePage();
    }
}
