package carnet.outil;

/**
 * La classe TailleComposants.
 */
public class TailleComposants {
    private static final TailleComposants instance = new TailleComposants();
    private final int tailleBouton;
    private final int windowX;
    private final int windowY;

    /**
     * Constructeur de la classe TailleComposants, il permet d'initialiser toutes les tailles utilisées dans l'application Carnet
     */
    private TailleComposants() {
        this.tailleBouton = 35;
        this.windowX = 800;
        this.windowY = 600;
    }

    /**
     * Fonction permettant de réaliser une instance du singleton TailleComposants.
     *
     * @return instance
     */
    public static TailleComposants getInstance() {
        return instance;
    }

    /**
     * Fonction qui retourne la taille du bouton permettant d'ajouter des activités.
     *
     * @return taille bouton
     */
    public int getTailleBouton() {
        return tailleBouton;
    }

    /**
     * Fonction qui retourne la largeur de la fenêtre de l'application.
     *
     * @return window x
     */
    public int getWindowX() {
        return windowX;
    }

    /**
     * Fonction qui retourne la hauteur de la fenêtre de l'application.
     *
     * @return window y
     */
    public int getWindowY() {
        return windowY;
    }
}