package carnet.model;

import java.util.Date;

/**
 * La classe PageDePresentation
 */
public class PageDePresentation extends Page {
    private String auteur;
    private Date dateDebut;
    private Date dateFin;

    /**
     * Constructeur de la classe PageDePresentation
     */
    public PageDePresentation() {
        super();
        this.auteur = "Hugo Ipt";
        this.dateDebut = new Date();
        this.dateFin = new Date();
    }

    /**
     * Constructeur de la classe PageDePresentation
     *
     * @param indice entier qui correspond à l'emplacement de la page dans le carnet
     */
    public PageDePresentation(int indice) {
        super();
        this.indicePage = indice;
        this.auteur = "Hugo Ipt";
        this.dateDebut = new Date();
        this.dateFin = new Date();
    }

    /**
     * Procédure qui définit l'auteur / le nouvel auteur du carnet
     *
     * @param nouvelAuteur le nouvel auteur
     */
    public void setAuteur(String nouvelAuteur) {
        this.auteur = nouvelAuteur;
    }

    /**
     * Procédure qui définit la date du début de la création du carnet
     *
     * @param dateDeb la date de début
     */
    public void setDateDebut(Date dateDeb) {
        this.dateDebut = dateDeb;
    }

    /**
     * Procédure qui définit la date de fin de la création du carnet
     *
     * @param dateFin la date de fin
     */
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    /**
     * Fonction qui retourne l'auteur
     *
     * @return un string, l'auteur
     */
    public String getAuteur() {
        return auteur;
    }

    /**
     * Fonction qui retourne la date de début
     *
     * @return la date de début
     */
    public Date getDateDebut() {
        return dateDebut;
    }

    /**
     * Fonction qui retourne la date de fin
     *
     * @return la date de fin
     */
    public Date getDateFin() {
        return dateFin;
    }
}
