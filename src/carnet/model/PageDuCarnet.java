package carnet.model;

import java.util.Date;

/**
 * La classe PageDuCarnet
 */
public class PageDuCarnet extends Page {
    private String texte;
    private final Date date;

    /**
     * Constructeur de la classe PageDuCarnet
     */
    public PageDuCarnet() {
        super();
        this.texte = "Racontez votre voyage...";
        this.date = new Date();
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
}
