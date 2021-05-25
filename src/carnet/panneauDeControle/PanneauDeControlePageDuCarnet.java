package carnet.panneauDeControle;

import carnet.designPattern.Observateur;
import carnet.exceptions.CancelImageException;
import carnet.exceptions.ImageNotLoadedException;
import carnet.exceptions.PageInexistanteException;
import carnet.model.CarnetDeVoyage;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

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
    @FXML
    private ImageView imagePageDuCarnet;

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
        this.carnet.getPageDuCarnet().setTitre(titre);
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

    public void onDragDropped(DragEvent event) {
        boolean success = true;
        Dragboard dragboard = event.getDragboard();
        List<File> listeDeFichier = dragboard.getFiles();
        try {
            Image image = new Image(new FileInputStream(listeDeFichier.get(0))); //On récupère l'image sous forme de fichier, pour la passer en image, j'utilise un fileInputStream
            if (image.getProgress() == 0) //Si l'image ne s'est pas chargée
                throw new ImageNotLoadedException("Impossible de charger l'image");
            this.imagePageDuCarnet.setImage(image);
        } catch (FileNotFoundException fnfe) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("FileNotFoundException");
            dialog.setHeaderText("Impossible de trouver l'image");
            dialog.setContentText("Erreur : un problème à été rencontré lors de la recherche de l'image\n" + "Veuillez rééssayer ! ");
            dialog.show();
            //Le chronomètre
            PauseTransition pt = new PauseTransition(Duration.seconds(5));
            pt.setOnFinished(Event -> dialog.close());
            pt.play();
            success = false;
        } catch (ImageNotLoadedException inle) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("ImageNotLoadedException");
            dialog.setHeaderText("Impossible de charger l'image");
            dialog.setContentText("Erreur : un problème à été rencontré lors du chargement de l'image\n" + "Veuillez rééssayer ! ");
            dialog.show();
            //Le chronomètre
            PauseTransition pt = new PauseTransition(Duration.seconds(5));
            pt.setOnFinished(Event -> dialog.close());
            pt.play();
            success = false;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    public void onDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
        event.consume();
    }

    public void onMouseClicked() {
        Window fenetre = imagePageDuCarnet.getScene().getWindow();
        FileChooser file = new FileChooser();
        file.setTitle("Choisissez votre image !");
        file.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Tous les fichiers", "*."),
                new FileChooser.ExtensionFilter("JPG,JPEG,PNG", "*.jpg", "*.jpeg", "*.png"),//On ajoute un filtre dans la fenêtre pour faciliter la recherche d'images
                new FileChooser.ExtensionFilter("GIFS", "*.gif"));
        File fileSelected = file.showOpenDialog(fenetre);
        try {
            if (fileSelected == null) { //Si l'utilisateur annule l'ajout de l'image
                throw new CancelImageException("L'ajout de l'image a bien été annulé");
            } else {
                Image image = new Image(new FileInputStream(fileSelected)); //On récupère l'image sous forme de fichier, pour la passer en image, j'utilise un fileInputStream
                if (image.getProgress() == 0) //Si l'image ne s'est pas chargée
                    throw new ImageNotLoadedException("Impossible de charger l'image");
                this.imagePageDuCarnet.setImage(image);
            }
        } catch (FileNotFoundException fnfe) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("FileNotFoundException");
            dialog.setHeaderText("Impossible de trouver l'image");
            dialog.setContentText("Erreur : un problème à été rencontré lors de la recherche de l'image\n" + "Veuillez rééssayer ! ");
            dialog.show();
            //Le chronomètre
            PauseTransition pt = new PauseTransition(Duration.seconds(5));
            pt.setOnFinished(Event -> dialog.close());
            pt.play();
        } catch (ImageNotLoadedException inle) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("ImageNotLoadedException");
            dialog.setHeaderText("Impossible de charger l'image");
            dialog.setContentText("Erreur : un problème à été rencontré lors du chargement de l'image\n" + "Veuillez rééssayer ! ");
            dialog.show();
            //Le chronomètre
            PauseTransition pt = new PauseTransition(Duration.seconds(5));
            pt.setOnFinished(Event -> dialog.close());
            pt.play();
        } catch (CancelImageException cie) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("CancelImageException");
            dialog.setHeaderText("Impossible d'ajouter l'image");
            dialog.setContentText("Erreur : l'image n'as pas été choisie\n" + "Veuillez rééssayer ! ");
            dialog.show();
            //Le chronomètre
            PauseTransition pt = new PauseTransition(Duration.seconds(5));
            pt.setOnFinished(Event -> dialog.close());
            pt.play();
        }
    }

    @Override
    public void reagir() {
        if (!carnet.siLaPageActuelleEstLaPageDePresentation()) { //On à pas toujours une page du carnet.
            this.titre.setText(this.carnet.getPageDuCarnet().getTitre());
            this.zoneDeTexte.setText(this.carnet.getPageDuCarnet().getTexte());

            try {
                titre.setText(this.carnet.getPageDuCarnetAvecUnNumero(this.carnet.getPageActuelle()).getTitre());
            } catch (PageInexistanteException e) {
                e.printStackTrace();
            }
        }
    }
}
