package carnet.exceptions;

/**
 * La classe FichierDeSauvegardeException
 */
public class FichierDeSauvegardeException extends CarnetException {

    /**
     * Constructeur de l'exception se déclanchant lorsque l'on n'arrive pas à charger un carnet
     *
     * @param message le message
     */
    public FichierDeSauvegardeException(String message) {
        super(message);
    }
}
