package carnet.outil;

/**
 * La classe FabriqueNumeroPage.
 */
public class FabriqueNumeroPage {
    private int numPage;

    private FabriqueNumeroPage() {
        this.numPage = 1;
    }

    private static final FabriqueNumeroPage instance = new FabriqueNumeroPage();

    /**
     * Retourne l'instance de FabriqueNumeroPage.
     *
     * @return FabriqueNumeroPage instance
     */
    public static FabriqueNumeroPage getInstance() {
        return instance;
    }

    /**
     * Retourne un numéro de page unique.
     *
     * @return le numéro de page
     */
    public int getNumeroPage() {
        numPage++;
        return this.numPage - 1;
    }

    /**
     * Réinitialise les numéros de page.
     */
    public void reset() {
        this.numPage = 1;
    }
}
