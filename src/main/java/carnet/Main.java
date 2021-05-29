package carnet;

import animatefx.animation.FadeIn;
import carnet.model.CarnetDeVoyage;
import carnet.outil.TailleComposants;
import carnet.panneauDeControle.PanneauDeControleMenu;
import carnet.panneauDeControle.PanneauDeControlePageDePresentation;
import carnet.panneauDeControle.PanneauDeControlePageDuCarnet;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * La classe Main
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root;
        CarnetDeVoyage carnet = new CarnetDeVoyage();
        TailleComposants tc = TailleComposants.getInstance();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/vues/VueMenu.fxml"));
        PanneauDeControleMenu pdcM = new PanneauDeControleMenu(carnet);
        PanneauDeControlePageDePresentation pdcP = new PanneauDeControlePageDePresentation(carnet);
        PanneauDeControlePageDuCarnet pdcC = new PanneauDeControlePageDuCarnet(carnet);

        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/images/carnet.png"))));

        loader.setControllerFactory(ic -> {
            if (ic.equals(carnet.panneauDeControle.PanneauDeControleMenu.class)) return pdcM;
            else if (ic.equals((carnet.panneauDeControle.PanneauDeControlePageDePresentation.class))) return pdcP;
            else if (ic.equals(carnet.panneauDeControle.PanneauDeControlePageDuCarnet.class)) return pdcC;
            else // par défaut...
                return null;
        });
        root = loader.load();

        primaryStage.setTitle("Carnet | Hugo Iopeti");
        primaryStage.setScene(new Scene(root, tc.getWindowX(), tc.getWindowY()));
        primaryStage.show();

        //Animation
        new FadeIn(root).play();

        carnet.notifierObservateurs();
    }

    /**
     * Le point d'entrée de l'application
     *
     * @param args les arguments d'entrée
     */
    public static void main(String[] args) {
        launch(args);
    }
}
