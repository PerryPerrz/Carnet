package carnet.sauvegarde;

import carnet.exceptions.FichierDeSauvegardeException;
import carnet.model.CarnetDeVoyage;
import carnet.model.PageDuCarnet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

/**
 * La classe SauvegardeDuCarnet
 */
public class SauvegardeDuCarnet {

    private SauvegardeDuCarnet() {
    }

    private static final SauvegardeDuCarnet instance = new SauvegardeDuCarnet();

    /**
     * Retourne l'instance de SauvegardeDuCarnet
     *
     * @return l'instance de SauvegardeDuCarnet
     */
    public static SauvegardeDuCarnet getInstance() {
        return instance;
    }

    /**
     * Procédure qui sauvegarde le carnet dans un fichier de sauvegarde
     *
     * @param carnet un carnet à sauvegarder
     */
    public void sauvegardeDuCarnet(CarnetDeVoyage carnet, File dossierDeSauvegarde, String nomDeLaSauvegarde) {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        String sauvegarde = gson.toJson(carnet, CarnetDeVoyage.class);

        //On sauvegarde les informations dans un fichier json.
        try {
            FileWriter flot = new FileWriter(dossierDeSauvegarde.getPath() + "/" + nomDeLaSauvegarde + ".json");
            PrintWriter flotFiltre = new PrintWriter(flot);
            flotFiltre.println(sauvegarde);
            flotFiltre.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fonction qui retranscrit les données du fichier de sauvegarde dans un carnet
     *
     * @return Un carnet de voyage
     * @throws FichierDeSauvegardeException un FichierDeSauvegardeException
     */
    public CarnetDeVoyage retranscriptionDuCarnet(File fichierDeSauvegarde) throws FichierDeSauvegardeException {
        try {
            FileReader flot = new FileReader(fichierDeSauvegarde);
            BufferedReader buff = new BufferedReader(flot);
            String ligneLue = buff.readLine();
            StringBuilder str = new StringBuilder(90);

            while (ligneLue != null) {
                str.append(ligneLue);
                ligneLue = buff.readLine();
            }
            Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
            return gson.fromJson(str.toString(), CarnetDeVoyage.class);
        } catch (IOException e) {
            throw new FichierDeSauvegardeException("Impossible de trouve rle fichier de sauvegarde");
        }
    }

    /**
     * Procédure qui sauvegarde une page du carnet
     *
     * @param page la page
     */
    public void sauvegardeDUnePage(PageDuCarnet page) {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        String sauvegarde = gson.toJson(page, PageDuCarnet.class);

        //On sauvegarde les informations dans un fichier json.
        try {
            FileWriter flot = new FileWriter("/tmp/sauvegardePage.json");
            PrintWriter flotFiltre = new PrintWriter(flot);
            flotFiltre.println(sauvegarde);
            flotFiltre.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fonction qui retranscrit les informations d'une page du carnet
     *
     * @return La page retranscrite
     * @throws FichierDeSauvegardeException un FichierDeSauvegardeException
     */
    public PageDuCarnet retranscriptionDUnePageDuCarnet() throws FichierDeSauvegardeException {
        try {
            FileReader flot = new FileReader("/tmp/sauvegardePage.json");
            BufferedReader buff = new BufferedReader(flot);
            String ligneLue = buff.readLine();
            StringBuilder str = new StringBuilder(90);

            while (ligneLue != null) {
                str.append(ligneLue);
                ligneLue = buff.readLine();
            }
            Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
            return gson.fromJson(str.toString(), PageDuCarnet.class); //JSon vers objet
        } catch (IOException e) {
            throw new FichierDeSauvegardeException("Impossible de trouve rle fichier de sauvegarde");
        }
    }
}
