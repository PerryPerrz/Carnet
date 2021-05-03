package carnet.exceptions;

/**
 * La classe SupprimerPageDePresentationException
 */
public class SupprimerPageDePresentationException extends CarnetException {

    /**
     * Constructeur de l'exception se déclanchant lorsque l'on essaie de supprimer la page de présentation
     *
     * @param message le message
     */
    public SupprimerPageDePresentationException(String message) {
        super(message);
    }
}
