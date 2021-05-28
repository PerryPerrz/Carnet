package carnet.panneauDeControle;

import carnet.designPattern.Observateur;
import carnet.exceptions.CancelImageException;
import carnet.exceptions.FormatCoordonneesException;
import carnet.exceptions.ImageNotLoadedException;
import carnet.exceptions.PageInexistanteException;
import carnet.model.CarnetDeVoyage;
import carnet.outil.TailleComposants;
import com.sothawo.mapjfx.Coordinate;
import com.sothawo.mapjfx.MapView;
import com.sothawo.mapjfx.Marker;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

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
    @FXML
    private Button boutonPrec;
    @FXML
    private Button boutonSuiv;
    @FXML
    private Button boutonSauvegarde;
    @FXML
    private Button home;
    @FXML
    private VBox Vbox;

    private Marker mark;
    private MapView mapView;

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
            this.carnet.getPageDuCarnet().setPathImagePage(listeDeFichier.get(0).getPath());//On stock l'image dans le model
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
                this.imagePageDuCarnet.setImage(image);
                this.carnet.getPageDuCarnet().setPathImagePage(fileSelected.getPath());
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
    public void goHome() {
        this.carnet.backToPageDePresentation();
    }

    @FXML
    void initialize() {
        Coordinate coord = new Coordinate(48.66514303712896, 6.161092511379647);

        this.mark = Marker.createProvided(Marker.Provided.RED);
        mark.setPosition(coord).setVisible(true);

        HBox Hbox = new HBox();

        TailleComposants tc = TailleComposants.getInstance();
        Button boutonMarker = new Button();
        boutonMarker.setOnAction(e->mark.setVisible(!mark.getVisible()));
        boutonMarker.setPrefWidth(tc.getTailleBouton());
        boutonMarker.setPrefHeight(tc.getTailleBouton());
        boutonMarker.setStyle("-fx-background-color:transparent;");

        Button boutonCoordonnees = new Button();
        boutonCoordonnees.setOnAction(e->this.changerLesCoordonnees());
        boutonMarker.setPrefWidth(tc.getTailleBouton());
        boutonMarker.setPrefHeight(tc.getTailleBouton());
        boutonCoordonnees.setStyle("-fx-background-color:transparent;");

        ImageView image = new ImageView(new Image("images/pointeur.png"));
        image.setFitWidth(tc.getTailleBouton());
        image.setFitWidth(tc.getTailleBouton());
        image.setPreserveRatio(true);
        boutonMarker.setGraphic(image);

        ImageView image2 = new ImageView(new Image("images/boussole.png"));
        image2.setFitWidth(tc.getTailleBouton());
        image2.setFitWidth(tc.getTailleBouton());
        image2.setPreserveRatio(true);
        boutonCoordonnees.setGraphic(image2);

        this.mapView = new MapView();

        mapView.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                mapView.setCenter(coord);
                mapView.addMarker(mark);
            }
        });

        mapView.initialize();
        mapView.setMinSize(319, 147);
        mapView.setMaxSize(319, 147);

        Hbox.getChildren().addAll(boutonMarker,boutonCoordonnees);
        Hbox.setAlignment(Pos.CENTER);
        this.Vbox.getChildren().addAll(mapView,Hbox);
    }

    /**
     * Procédure qui change les coordonnées du pointeur
     */
    public void changerLesCoordonnees(){
        TextInputDialog dialog = new TextInputDialog("Changer les coordonnées");
        dialog.setTitle("Changer les coordonnées GPS");
        dialog.setHeaderText("Entrez les coordonnées GPS : ");
        dialog.setContentText("latitude,longitude :");

        Optional<String> res = dialog.showAndWait();
        res.ifPresent(coord -> {
            try {
                this.carnet.getPageDuCarnet().changerLesCoordonneesDuCurseur(coord);
            } catch (FormatCoordonneesException e) {
                Alert dialog2 = new Alert(Alert.AlertType.ERROR);
                dialog2.setTitle("FormatCoordonneesException");
                dialog2.setHeaderText("Impossible d'utiliser les coordonnées saisies");
                dialog2.setContentText("Erreur : un problème à été rencontré lors de la recherche des coordonnées GPS\n" + "Veuillez rééssayer ! ");
                dialog2.show();
                //Le chronomètre
                PauseTransition pt = new PauseTransition(Duration.seconds(5));
                pt.setOnFinished(Event -> dialog2.close());
                pt.play();
            }
        });
        this.carnet.notifierObservateurs();
    }

    @Override
    public void reagir() {
        TailleComposants tc = TailleComposants.getInstance();
        if (!carnet.siLaPageActuelleEstLaPageDePresentation()) { //On à pas toujours une page du carnet.
            this.titre.setText(this.carnet.getPageDuCarnet().getTitre());
            this.zoneDeTexte.setText(this.carnet.getPageDuCarnet().getTexte());
            //Je m'occupe de raffraichir l'affichage de la mapView et du marqueur
            Coordinate coord = new Coordinate(this.carnet.getPageDuCarnet().getLatitude(), this.carnet.getPageDuCarnet().getLongitude());
            this.mark.setPosition(coord);
            this.mapView.setCenter(coord);
            try {
                titre.setText(this.carnet.getPageDuCarnetAvecUnNumero(this.carnet.getPageActuelle()).getTitre());
                if (this.carnet.getPageDuCarnet().getPathImagePage().equals("")) {
                    Image image = new Image("images/image2.png");
                    this.carnet.getPageDuCarnet().setPathImagePage(image.getUrl().replace("file:", "")); //J'utilise l'image pour récuperer le chemin absolu
                }
                this.imagePageDuCarnet.setImage(new Image(new FileInputStream(this.carnet.getPageDuCarnet().getPathImagePage())));
            } catch (FileNotFoundException | PageInexistanteException e) {
                e.printStackTrace();
            }
        }

        ImageView image = new ImageView(new Image("images/left.png"));
        image.setFitWidth(tc.getTailleBouton());
        image.setFitWidth(tc.getTailleBouton());
        image.setPreserveRatio(true);
        this.boutonPrec.setGraphic(image);

        ImageView image2 = new ImageView(new Image("images/right.png"));
        image2.setFitWidth(tc.getTailleBouton());
        image2.setFitWidth(tc.getTailleBouton());
        image2.setPreserveRatio(true);
        this.boutonSuiv.setGraphic(image2);

        ImageView image3 = new ImageView(new Image("images/file.png"));
        image3.setFitWidth(tc.getTailleBouton());
        image3.setFitWidth(tc.getTailleBouton());
        image3.setPreserveRatio(true);
        this.boutonSauvegarde.setGraphic(image3);

        ImageView image4 = new ImageView(new Image("images/home.png"));
        image4.setFitWidth(tc.getTailleBouton());
        image4.setFitWidth(tc.getTailleBouton());
        image4.setPreserveRatio(true);
        this.home.setGraphic(image4);
    }
}
