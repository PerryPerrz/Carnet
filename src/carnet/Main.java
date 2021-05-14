package carnet;

import carnet.model.CarnetDeVoyage;
import carnet.panneauDeControle.PanneauDeControleMenu;
import carnet.panneauDeControle.PanneauDeControlePageDePresentation;
import carnet.panneauDeControle.PanneauDeControlePageDuCarnet;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root;
        CarnetDeVoyage carnet = new CarnetDeVoyage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("vues/VueMenu.fxml"));
        PanneauDeControleMenu pdcM = new PanneauDeControleMenu(carnet);
        PanneauDeControlePageDePresentation pdcP = new PanneauDeControlePageDePresentation(carnet);
        PanneauDeControlePageDuCarnet pdcC = new PanneauDeControlePageDuCarnet(carnet);

        loader.setControllerFactory(ic -> {
            if (ic.equals(carnet.panneauDeControle.PanneauDeControleMenu.class)) return pdcM;
            else if (ic.equals((carnet.panneauDeControle.PanneauDeControlePageDePresentation.class)))return pdcP;
            else if (ic.equals(carnet.panneauDeControle.PanneauDeControlePageDuCarnet.class)) return pdcC;
        else // par défaut...
            return null ;
        });
        root = loader.load();

        primaryStage.setTitle("Carnet | Hugo Ipt");
        primaryStage.setScene(new Scene(root,800,550));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
