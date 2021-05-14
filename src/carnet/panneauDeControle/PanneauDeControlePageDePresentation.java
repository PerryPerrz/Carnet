package carnet.panneauDeControle;

import carnet.designPattern.Observateur;
import carnet.model.CarnetDeVoyage;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * La classe PanneauDeControlePageDePresentation
 */
public class PanneauDeControlePageDePresentation implements Observateur {
    private final CarnetDeVoyage carnet;
    @FXML
    private Label titre;
    @FXML
    private TextField dateDebut;
    @FXML
    private TextField dateFin;
    @FXML
    private TextArea participants;
    @FXML
    private TextField auteur;

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
    public void ajouterAuteur() {
        //Je récupère l'auteur que l'utilisateur saisit
        String auteur = this.auteur.getText();
        this.carnet.getPageDePresentation().setTitre(auteur);
    }

    /**
     * Procédure ajouterDateDebut
     */
    public void ajouterDateDebut() {
        try {
            //Je récupère la date que l'utilisateur saisit
            Date dateDeb = new SimpleDateFormat("dd/MM/yyyy").parse(this.dateDebut.getText());
            this.carnet.getPageDePresentation().setDateDebut(dateDeb);
        } catch (ParseException e) {
            //Je reset la date de début
            this.dateDebut.setText("00/00/0000");
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
    public void ajouterDateFin() {
        try {
            Date dateFin = new SimpleDateFormat("dd/MM/yyyy").parse(this.dateFin.getText());
            this.carnet.getPageDePresentation().setDateFin(dateFin);
        } catch (ParseException e) {
            //Je reset la date de fin
            this.dateFin.setText("00/00/0000");
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
    public void ajouterParticipants() {
        //Je récupère le(s) participant(s)
        //Je découpe le string que l'utilisateur saisit
        //Je compte le nombre de '-'

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
     * Procédure setImage
     */
    public void setImage() {

    }

    @Override
    public void reagir() {
        titre.setText(this.carnet.getNomDuCarnet());
    }
}
