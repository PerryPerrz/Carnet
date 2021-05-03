package carnet.model;

import carnet.designPattern.SujetObserve;
import carnet.exceptions.CarnetException;
import carnet.exceptions.PageInexistanteException;
import carnet.exceptions.SupprimerPageDePresentationException;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * La classe CarnetDeVoyage
 */
public class CarnetDeVoyage extends SujetObserve {
    private String nomDuCarnet;
    private final ArrayList<String> participants;
    private int pageActuelle;
    private final PageDePresentation pageDePresentation;
    private final ArrayList<PageDuCarnet> pagesDuCarnet;

    /**
     * Constructeur de la classe CarnetDeVoyage
     */
    public CarnetDeVoyage() {
        this.nomDuCarnet = "Carnet de Monaco";
        this.participants = new ArrayList<>(4);
        this.pageActuelle = -1;
        this.pageDePresentation = new PageDePresentation();
        this.pagesDuCarnet = new ArrayList<>(10);
    }

    /**
     * Procédure qui sauvegarde le carnet
     */
    public void enregistrerCarnet() {

    }

    /**
     * Procédure qui renomme le carnet
     *
     * @param nouveauNom le nouveau nom du carnet
     */
    public void renommerCarnet(String nouveauNom) {
        this.nomDuCarnet = nouveauNom;
    }

    /**
     * Procédure qui ajoute une nouvelle page du carnet
     */
    public void ajouterPage() {
        PageDuCarnet pdc = new PageDuCarnet();
        this.pageActuelle++;
        this.pagesDuCarnet.add(pageActuelle, pdc);
    }

    /**
     * Procédure qui supprime une page du carnet
     *
     * @param numPageASupprimer numéro de la page à supprimer
     * @throws CarnetException the carnet exception
     */
    public void supprimerPage(int numPageASupprimer) throws CarnetException {
        PageDuCarnet pageASupprimer;
        if (numPageASupprimer == 0)
            throw new SupprimerPageDePresentationException("Vous ne pouvez pas supprimer la page de présentation !");
        else {
            for (Iterator<PageDuCarnet> iter = this.iteratorPageDuCarnet(); iter.hasNext(); ) {
                pageASupprimer = iter.next();
                if (pageASupprimer.getNumeroPage() == numPageASupprimer)
                    iter.remove();
                this.pagesDuCarnet.remove(numPageASupprimer);
                this.pageActuelle--;
            }
        }
    }

    /**
     * Procédure qui ajoute le(s) participant(s) au carnet
     *
     * @param participant nom de(s) participant(s)
     */
    public void ajouterParticipants(String participant) {
        this.participants.add(participant);
    }

    /**
     * Fonction qui retourne la page précedente du carnet
     *
     * @return la page précedente
     */
    public Page pagePrecedente() {
        if (this.pageActuelle == -1)
            return this.pageDePresentation;
        pageActuelle--;
        if (this.pageActuelle == 0)
            return this.pageDePresentation;
        else
            return this.pagesDuCarnet.get(pageActuelle);
    }

    /**
     * Fonction qui retourne la page suivante du carnet
     *
     * @return la page suivante
     * @throws CarnetException the carnet exception
     */
    public Page pageSuivante() throws CarnetException {
        //Si l'utilisateur arrive à la dernière page et veux arriver à la page suivante, il créer une nouvelle page
        if (this.pageActuelle == this.pagesDuCarnet.size() - 1) {
            this.ajouterPage();
            return this.pagesDuCarnet.get(pageActuelle);
        }
        pageActuelle++;
        return this.pagesDuCarnet.get(pageActuelle);
    }

    /**
     * Gets page de presentation.
     *
     * @return the page de presentation
     */
    public PageDePresentation getPageDePresentation() {
        return pageDePresentation;
    }

    /**
     * Gets page du carnet avec un numero.
     *
     * @param numeroDePage the numero de page
     * @return the page du carnet avec un numero
     * @throws CarnetException the carnet exception
     */
    public Page getPageDuCarnetAvecUnNumero(int numeroDePage) throws CarnetException {
        Page pageRecherchee = null;
        for (Page p : this.pagesDuCarnet) {
            if (p.numeroPage == numeroDePage)
                pageRecherchee = p;
        }
        if (pageRecherchee == null)
            throw new PageInexistanteException("Impossible de trouver la page recherchée");
        return pageRecherchee;
    }

    /**
     * Iterator page du carnet iterator.
     *
     * @return the iterator
     */
    public Iterator<PageDuCarnet> iteratorPageDuCarnet() {
        return this.pagesDuCarnet.iterator();
    }
}
