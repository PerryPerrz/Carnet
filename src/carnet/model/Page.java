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
     * Fonction qui retourne le numéro de page d'une page
     *
     * @return un entier, le numéro de page
     */
    public int getNumeroPage() {
        return numeroPage;
    }
}
