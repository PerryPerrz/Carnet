package carnet.exceptions;

/**
 * La classe FormatCoordonneesException
 */
public class FormatCoordonneesException extends CarnetException {

    /**
     * Constructeur de l'exception se déclanchant lorsque les coordonnées rentrées par l'utilisateur ne correspondent pas au format requis
     *
     * @param message le message
     */
    public FormatCoordonneesException(String message) {
        super(message);
    }
}
