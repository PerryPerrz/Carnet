package carnet.outil;

import carnet.model.CarnetDeVoyage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

public class SauvegardeEtRestauration {
    public static void sauvegardeDesInformations(CarnetDeVoyage carnet) {
        JSONObject obj = new JSONObject();

        obj.put("titre", carnet.getNomDuCarnet());
        obj.put("dateDebut", carnet.getPageDePresentation().getDateDebut());
        obj.put("dateFin", carnet.getPageDePresentation().getDateFin());

        String str;
        int cpt = 1;
        for (Iterator<String> iter = carnet.iteratorParticipantsDuCarnet(); iter.hasNext(); ) {
            str = iter.next();
            obj.put("participant" + cpt, str);
            cpt++;
        }
        obj.put("auteur", carnet.getPageDePresentation().getAuteur());

        StringWriter out = new StringWriter();
        try {
            obj.writeJSONString(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonText = out.toString();

        String cheminDuFichier = "fichierDeSauvegarde.json";
        File file = new File(cheminDuFichier);
        try {
            if (!file.exists())
                file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(jsonText);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Erreur: Impossible de créer le fichier!");
        }
    }

    public static void retranscriptionDesInformations() {
        JSONParser parser = new JSONParser();

        String cheminDuFichier = "fichierDeSauvegarde.json";
        File file = new File(cheminDuFichier);

        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                scan.nextLine();
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

        String str = "[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";

        try {
            Object obj = parser.parse(str);
            JSONArray array = (JSONArray) obj;

            str = "{}";
            obj = parser.parse(str);
            System.out.println(obj);

            str = ",";
            obj = parser.parse(str);
            System.out.println(obj);

        } catch (ParseException pe) {
            System.out.println("position: " + pe.getPosition());
            System.out.println(pe);
        }
    }
}