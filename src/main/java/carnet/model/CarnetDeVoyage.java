package carnet.model;

import carnet.designPattern.SujetObserve;
import carnet.exceptions.CarnetException;
import carnet.exceptions.FichierDeSauvegardeException;
import carnet.exceptions.PageInexistanteException;
import carnet.exceptions.SupprimerPageDePresentationException;
import carnet.sauvegarde.SauvegardeDuCarnet;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * La classe CarnetDeVoyage
 */
public class CarnetDeVoyage extends SujetObserve {
    private final ArrayList<String> participants;
    private int pageActuelle;
    private final PageDePresentation pageDePresentation;
    private final ArrayList<PageDuCarnet> pagesDuCarnet;
    private transient boolean toTheLeft; //Booléen qui regarde si l'utilisateur appuie sur le bouton précedent ou non

    /**
     * Constructeur de la classe CarnetDeVoyage
     */
    public CarnetDeVoyage() {
        this.participants = new ArrayList<>(4);
        this.pageActuelle = -1;
        this.pageDePresentation = new PageDePresentation(1);
        this.pagesDuCarnet = new ArrayList<>(10);
    }

    /**
     * Procédure qui sauvegarde le carnet
     *
     * @throws FichierDeSauvegardeException
     */
    public void enregistrerCarnet() throws FichierDeSauvegardeException {
        SauvegardeDuCarnet sauvDuCar = SauvegardeDuCarnet.getInstance();
        sauvDuCar.sauvegardeDuCarnet(this);
    }

    /**
     * Procédure qui renomme le carnet
     *
     * @param nouveauNom le nouveau nom du carnet
     */
    public void renommerCarnet(String nouveauNom) {
        this.pageDePresentation.setTitre(nouveauNom);
        notifierObservateurs();
    }

    /**
     * Procédure qui renomme une page du carnet
     *
     * @param nouveauNom le nouveau nom du carnet
     */
    public void renommerPage(String nouveauNom) {
        this.pagesDuCarnet.get(this.pageActuelle).setTitre(nouveauNom);
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
        this.notifierObservateurs();
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
        Page page;
        if (this.pageActuelle == -1)
            page = this.pageDePresentation;
        else if (this.pageActuelle == 0) {
            pageActuelle--;
            page = this.pageDePresentation;
        } else {
            pageActuelle--;
            page = this.pagesDuCarnet.get(pageActuelle);
        }
        this.toTheLeft = true;
        notifierObservateurs();
        return page;
    }

    /**
     * Fonction qui retourne la page suivante du carnet
     *
     * @return la page suivante
     */
    public Page pageSuivante() {
        Page page;
        //Si l'utilisateur arrive à la dernière page et veux arriver à la page suivante, il créer une nouvelle page
        if (this.pageActuelle == this.pagesDuCarnet.size() - 1) {
            this.ajouterPage();
            page = this.pagesDuCarnet.get(pageActuelle);
        } else {
            pageActuelle++;
            page = this.pagesDuCarnet.get(pageActuelle);
        }
        this.toTheLeft = false;
        notifierObservateurs();
        return page;
    }

    /**
     * Fonction qui retourne la page de présentation du carnet
     *
     * @return la page de présentation du carnet
     */
    public PageDePresentation getPageDePresentation() {
        return pageDePresentation;
    }

    /**
     * Fonction qui retourne la page actuelle du carnet
     *
     * @return la page actuelle du carnet
     */
    public PageDuCarnet getPageDuCarnet() {
        return this.pagesDuCarnet.get(this.pageActuelle);
    }

    /**
     * Fonction qui retourne la page du carnet avec un numéro de page
     *
     * @param numeroDePage le numero de page
     * @return la page du carnet avec un numero
     * @throws PageInexistanteException CarnetException
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
     * Iterator participants du carnet
     *
     * @return the iterator
     */
    public Iterator<String> iteratorParticipantsDuCarnet() {
        return this.participants.iterator();
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

    /**
     * Fonction qui retourne vrai si la page actuelle est une page de présentation
     *
     * @return un booléen
     */
    public boolean siLaPageActuelleEstLaPageDePresentation() {
        return this.getPageActuelle() == this.getPageDePresentation().getNumeroPage();
    }

    public String getParticipants() {
        StringBuilder str = new StringBuilder(20);
        for (String s : this.participants) {
            str.append(s).append("-");
        }
        if (this.participants.size() > 0)
            str.deleteCharAt(str.lastIndexOf("-")); //On supprimer le dernier "-"
        return str.toString();
    }

    public boolean isToTheLeft() {
        return toTheLeft;
    }

    public void nettoyerLesParticipants() {
        this.participants.clear();
    }

    /**
     * Procédure qui remet l'indice de la page sur la première page, soit la page de présentation
     */
    public void backToPageDePresentation() {
        this.pageActuelle = -1;
        notifierObservateurs();
    }

    /**
     * Procédure qui remet l'indice de la page sur la première page, soit la page de présentation
     */
    public void goToLastPage() {
        this.pageActuelle = this.pagesDuCarnet.size() - 1;
        notifierObservateurs();
    }
}
