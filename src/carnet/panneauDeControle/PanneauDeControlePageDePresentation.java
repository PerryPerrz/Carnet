package carnet.panneauDeControle;

import carnet.designPattern.Observateur;
import carnet.exceptions.CancelImageException;
import carnet.exceptions.ImageNotLoadedException;
import carnet.model.CarnetDeVoyage;
import carnet.outil.TailleComposants;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * La classe PanneauDeControlePageDePresentation
 */
public class PanneauDeControlePageDePresentation implements Observateur {
    private final CarnetDeVoyage carnet;
    @FXML
    private Label titre;
    @FXML
    private DatePicker dateDebut;
    @FXML
    private DatePicker dateFin;
    @FXML
    private TextArea participants;
    @FXML
    private TextField auteur;
    @FXML
    private ImageView imagePageDePresentation;
    @FXML
    private Button boutonSuiv;
    @FXML
    private Button boutonSauvegarde;
    @FXML
    private Button lastPage;

    /**
     * Constructeur de la classe PanneauDeControlePageDePresentation
     */
    public PanneauDeControlePageDePresentation(CarnetDeVoyage car2voy) {
        this.carnet = car2voy;
        this.carnet.ajouterObservateur(this);
    }

    /**
     * Procédure ajouterAuteur
     */
    private void ajouterAuteur() {
        //Je récupère l'auteur que l'utilisateur saisit
        String auteur = this.auteur.getText();
        this.carnet.getPageDePresentation().setAuteur(auteur);
    }

    /**
     * Procédure ajouterDateDebut
     */
    private void ajouterDateDebut() {
        try {
            //Je récupère la date que l'utilisateur saisit
            Date dateDeb = new SimpleDateFormat("dd/MM/yyyy").parse(this.dateDebut.getEditor().getText());
            this.carnet.getPageDePresentation().setDateDebut(dateDeb);
        } catch (ParseException e) {
            //Je reset la date de début
            this.dateDebut.getEditor().setText("00/00/0000");
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("DateInvalideException");
            dialog.setHeaderText("Erreur de saisie : la date est invalide");
            dialog.setContentText("Erreur : Le format de la date est incorrect ! \n" + "Format accepté : JJ/MM/AAAA, veuillez rééssayer ! ");
            dialog.show();
            //Le chronomètre
            PauseTransition pt = new PauseTransition(Duration.seconds(5));
            pt.setOnFinished(Event -> dialog.close());
            pt.play();
        }
    }

    /**
     * Procédure ajouterDateFin
     */
    private void ajouterDateFin() {
        try {
            Date dateFin = new SimpleDateFormat("dd/MM/yyyy").parse(this.dateFin.getEditor().getText());
            this.carnet.getPageDePresentation().setDateFin(dateFin);
        } catch (ParseException e) {
            //Je reset la date de fin
            this.dateFin.getEditor().setText("00/00/0000");
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("DateInvalideException");
            dialog.setHeaderText("Erreur de saisie : la date est invalide");
            dialog.setContentText("Erreur : Le format de la date est incorrect ! \n" + "Format accepté : JJ/MM/AAAA, veuillez rééssayer ! ");
            dialog.show();
            //Le chronomètre
            PauseTransition pt = new PauseTransition(Duration.seconds(5));
            pt.setOnFinished(Event -> dialog.close());
            pt.play();
        }
    }

    /**
     * Procédure ajouterParticipants
     */
    private void ajouterParticipants() {
        /*
        Je récupère le(s) participant(s)
        Je découpe le string que l'utilisateur saisit
        Je compte le nombre de '-'
        */

        int pos = this.participants.getText().indexOf('-');
        //Si il n'y a pas de '-'
        if (pos == -1) {
            Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setTitle("FormetParticipantsException");
            dialog.setHeaderText("Erreur de saisie : les participants doivent être séparés par un \"-\"");
            dialog.setContentText("Erreur : Le format des participants est incorrect ! \n" + "Format accepté : Participant1-Participant2, veuillez rééssayer ! ");
            dialog.show();
            //Le chronomètre
            PauseTransition pt = new PauseTransition(Duration.seconds(5));
            pt.setOnFinished(Event -> dialog.close());
            pt.play();
        }
        final String separateur = "-";
        this.carnet.nettoyerLesParticipants();
        String[] participants = this.participants.getText().split(separateur);
        for (String participant : participants) {
            this.carnet.ajouterParticipants(participant);
        }
    }

    /**
     * Procédure nouvellePage
     */
    public void nouvellePage() {
        this.carnet.ajouterPage();
    }

    /**
     * Procédure pageSuivante
     */
    public void pageSuivante() {
        this.carnet.pageSuivante();
    }

    /**
     * Procédure qui sauvegarde les informations rentrées par l'utilisateur dans le modèle
     */
    public void sauvegarderLesModifications() {
        this.ajouterAuteur();
        this.ajouterDateDebut();
        this.ajouterDateFin();
        this.ajouterParticipants();
    }

    public void onDragDropped(DragEvent event) {
        boolean success = true;
        Dragboard dragboard = event.getDragboard();
        List<File> listeDeFichier = dragboard.getFiles();
        try {
            Image image = new Image(new FileInputStream(listeDeFichier.get(0))); //On récupère l'image sous forme de fichier, pour la passer en image, j'utilise un fileInputStream
            if (image.getProgress() == 0) //Si l'image ne s'est pas chargée
                throw new ImageNotLoadedException("Impossible de charger l'image");
            this.imagePageDePresentation.setImage(image);
            this.carnet.getPageDePresentation().setPathImagePage(listeDeFichier.get(0).getPath());//On stock l'image dans le model
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
        Window fenetre = imagePageDePresentation.getScene().getWindow();
        FileChooser file = new FileChooser();
        file.setTitle("Choisissez votre image !");
        //On ajoute un filtre dans la fenêtre pour faciliter la recherche d'images
        file.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG, JPEG, PNG", "*.jpg", "*.jpeg", "*.png"),
                new FileChooser.ExtensionFilter("GIFS", "*.gif"));
        File fileSelected = file.showOpenDialog(fenetre);
        try {
            if (fileSelected == null) { //Si l'utilisateur annule l'ajout de l'image
                throw new CancelImageException("L'ajout de l'image a bien été annulé");
            } else {
                Image image = new Image(new FileInputStream(fileSelected)); //On récupère l'image sous forme de fichier, pour la passer en image, j'utilise un fileInputStream
                if (image.getProgress() == 0) //Si l'image ne s'est pas chargée
                    throw new ImageNotLoadedException("Impossible de charger l'image");
                this.imagePageDePresentation.setImage(image);
                this.carnet.getPageDePresentation().setPathImagePage(fileSelected.getPath());
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

    /**
     * Procédure qui ramène l'utilisateur sur la page de présentation
     */
    public void leaveHome() {
        this.carnet.goToLastPage();
    }

    @Override
    public void reagir() {
        TailleComposants tc = TailleComposants.getInstance();
        if (carnet.siLaPageActuelleEstLaPageDePresentation()) {
            this.titre.setText(this.carnet.getPageDePresentation().getTitre());
            if (carnet.getPageDePresentation().getDateDebut() != null)
                this.dateDebut.getEditor().setText((new SimpleDateFormat("dd/MM/yyyy").format(carnet.getPageDePresentation().getDateDebut())));
            if (carnet.getPageDePresentation().getDateFin() != null)
                this.dateFin.getEditor().setText((new SimpleDateFormat("dd/MM/yyyy").format(carnet.getPageDePresentation().getDateFin())));
            this.participants.setText(this.carnet.getParticipants());
            this.auteur.setText(this.carnet.getPageDePresentation().getAuteur());
            try {
                if(this.carnet.getPageDePresentation().getPathImagePage().equals("")) {
                    Image image = new Image("carnet/ressources/image.png");
                    this.carnet.getPageDePresentation().setPathImagePage(image.getUrl().replace("file:", "")); //J'utilise l'image pour récuperer le chemin absolu
                }
                this.imagePageDePresentation.setImage(new Image(new FileInputStream(this.carnet.getPageDePresentation().getPathImagePage()))); //On a un chemin absolu, new Image requirt un chemin relatif, donc j'utilise un FileInputStream en intermédiaire.
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        ImageView image = new ImageView(new Image("carnet/ressources/right.png"));
        image.setFitWidth(tc.getTailleBouton());
        image.setFitWidth(tc.getTailleBouton());
        image.setPreserveRatio(true);
        this.boutonSuiv.setGraphic(image);

        ImageView image2 = new ImageView(new Image("carnet/ressources/file.png"));
        image2.setFitWidth(tc.getTailleBouton());
        image2.setFitWidth(tc.getTailleBouton());
        image2.setPreserveRatio(true);
        this.boutonSauvegarde.setGraphic(image2);

        ImageView image3 = new ImageView(new Image("carnet/ressources/last.png"));
        image3.setFitWidth(tc.getTailleBouton());
        image3.setFitWidth(tc.getTailleBouton());
        image3.setPreserveRatio(true);
        this.lastPage.setGraphic(image3);
    }
}
