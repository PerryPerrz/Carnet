package carnet.model;

/**
 * La classe Page
 */
public abstract class Page {
    /**
     * Le titre d'une page
     */
    protected String titre;

    /**
     * Le numéro d'une page
     */
    protected int numeroPage;

    /**
     * L'indice de la page
     */
    protected int indicePage;

    /**
     * Constructeur de la classe Page
     */
    public Page() {
        this.titre = "Nouvelle page";
    }

    /**
     * Procédure qui définit un nouveau titre de page
     *
     * @param nouveauTitre le nouveau titre
     */
    public void setTitre(String nouveauTitre) {
        this.titre = nouveauTitre;
    }

    /**
     * Fonction qui retourne le numéro de page d'un carnet
     *
     * @return un entier, le numéro de page
     */
    public int getNumeroPage() {
        return numeroPage;
    }

    /**
     * Fonction qui retourne l'indice d'une page d'un carnet
     *
     * @return un entier, l'indice de la page
     */
    public int getIndicePage() {
        return indicePage;
    }

    /**
     * Procédure qui définit l'indice de la page
     *
     * @param indicePage, l'indice de la page
     */
    public void setIndicePage(int indicePage) {
        this.indicePage = indicePage;
    }
}
