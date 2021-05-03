package carnet.designPattern;

import java.util.ArrayList;

/**
 * La classe SujetObserve
 */
public class SujetObserve {
    private final ArrayList<Observateur> observateurs;

    /**
     * Constructeur de SujetObserve, initialisation de la collection d'observateurs.
     */
    public SujetObserve() {
        observateurs = new ArrayList<>(10);
    }

    /**
     * Procédure qui ajoute un observateur à l'attribut qui correspond à la collection d'observateurs.
     *
     * @param obs l'observateur
     */
    public void ajouterObservateur(Observateur obs) {
        observateurs.add(obs);
    }

    /**
     * Procédure qui notifie les observateurs, met à jour les vues.
     */
    public void notifierObservateurs() {
        for (int i = 0; i < this.observateurs.size(); ++i) {
            observateurs.get(i).reagir();
        }
    }
}
