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
        this.pageDePresentation = new PageDePresentation(1);
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
        notifierObservateurs();
    }

    /**
     * Procédure qui ajoute une nouvelle page du carnet
     */
    public void ajouterPage() {
        PageDuCarnet pdc = new PageDuCarnet(); //+2 car la page de présentation a comme indice 1 mais lorsuqu'on la consulte, page actuelle est à -1.
        this.pageActuelle++;
        this.pagesDuCarnet.add(pageActuelle, pdc);
        this.raffraichirIndices();
    }

    /**
     * Procédure qui supprime une page du carnet
     *
     * @param numPageASupprimer numéro de la page à supprimer
     * @throws CarnetException, throw SupprimerPageDePresentationException & PageInexistanteException
     */
    public void supprimerPage(int numPageASupprimer) throws CarnetException {
        PageDuCarnet pageASupprimer;
        boolean aEteSupprimer = false;
        if (numPageASupprimer == 1)
            throw new SupprimerPageDePresentationException("Vous ne pouvez pas supprimer la page de présentation !");
        else {
            for (Iterator<PageDuCarnet> iter = this.iteratorPageDuCarnet(); iter.hasNext(); ) {
                pageASupprimer = iter.next();
                if (pageASupprimer.getNumeroPage() == numPageASupprimer) {
                    iter.remove();
                    this.pagesDuCarnet.remove(pageASupprimer);
                    this.pageActuelle--;
                    aEteSupprimer = true;
                }
            }
        }
        if (!aEteSupprimer)
            throw new PageInexistanteException("La page que vous voulez supprimer n'existe pas");
        this.raffraichirIndices();
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
        if (this.pageActuelle == 0) {
            pageActuelle--;
            return this.pageDePresentation;
        } else {
            pageActuelle--;
            return this.pagesDuCarnet.get(pageActuelle);
        }
    }

    /**
     * Fonction qui retourne la page suivante du carnet
     *
     * @return la page suivante
     */
    public Page pageSuivante() {
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
     * Gets page de presentation.
     *
     * @return the page de presentation
     */
    public PageDuCarnet getPageDuCarnet() {
        return this.pagesDuCarnet.get(this.pageActuelle);
    }

    /**
     * Gets page du carnet avec un numero.
     *
     * @param numeroDePage the numero de page
     * @return the page du carnet avec un numero
     * @throws PageInexistanteException the carnet exception
     */
    public Page getPageDuCarnetAvecUnNumero(int numeroDePage) throws PageInexistanteException {
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

    /**
     * Procédure qui permet de raffraichir les indices des pages du carnet après une action
     */
    public void raffraichirIndices() {
        PageDuCarnet pageARaffraichir;
        int cpt = 2;
        for (Iterator<PageDuCarnet> iter = this.iteratorPageDuCarnet(); iter.hasNext(); ) {
            pageARaffraichir = iter.next();
            pageARaffraichir.setIndicePage(cpt);
            cpt++;
        }
    }

    /**
     * Fonction qui retourne le nombre de page(s) du carnet
     *
     * @return l'entier qui correspond au nombre de page(s) du carnet
     */
    public int getNbPagesDuCarnet() {
        return this.pagesDuCarnet.size() + 1;
    }

    /**
     * Fonction qui retourne le numéro correspondant à la page actuelle
     *
     * @return l'entier qui correspond au numéro de la page actuelle
     */
    public int getPageActuelle() {
        if (this.pageActuelle == -1) { //Si la page actuelle correspond à la page de présentation
            return this.pageDePresentation.getNumeroPage();
        } else {
            return this.pagesDuCarnet.get(this.pageActuelle).getNumeroPage();
        }
    }

    public String getNomDuCarnet() {
        return nomDuCarnet;
    }
}
