package carnet.model;

import carnet.exceptions.FormatCoordonneesException;

/**
 * La classe PageDuCarnet
 */
public class PageDuCarnet extends Page {
    private String texte;
    private Double latitude;
    private Double longitude;

    /**
     * Constructeur de la classe PageDuCarnet
     */
    public PageDuCarnet() {
        super();
        this.texte = "";
        this.latitude = 48.66514303712896;
        this.longitude = 6.161092511379647;
    }

    /**
     * Fonction qui retourne le texte d'une page du carnet
     *
     * @return un string, le texte
     */
    public String getTexte() {
        return texte;
    }

    /**
     * Procédure qui définit le texte d'une page du carnet
     *
     * @param texte le texte
     */
    public void setTexte(String texte) {
        this.texte = texte;
    }

    /**
     * Fonction qui retourne la latitude de la MapView d'une page
     *
     * @return un entier, la latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Procédure qui définit la latitude d'une page
     *
     * @param latitude la latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Fonction qui retourne la longitude de la MapView d'une page
     *
     * @return un entier, la longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Procédure qui définit la longitude d'une page
     *
     * @param longitude la longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * Procédure qui récupère un string, et découpe celui-ci pour séparer la lattitude et la longitude
     *
     * @param coord les coordonnées
     * @throws FormatCoordonneesException FormatCoordonneesException
     */
    public void changerLesCoordonneesDuCurseur(String coord) throws FormatCoordonneesException {
        if (!coord.contains(","))
            throw new FormatCoordonneesException("Attention, la latitude et la longitude sont séparés par une \",\" !");
        final String separateur = ",";
        String[] coordonnees = coord.split(separateur);
        try {
            this.setLatitude(Double.parseDouble(coordonnees[0]));
            this.setLongitude(Double.parseDouble(coordonnees[1]));
        } catch (NumberFormatException nfe) {
            throw new FormatCoordonneesException("Attention, la latitude et la longitude sont des nombres réels!");
        }
    }
}
