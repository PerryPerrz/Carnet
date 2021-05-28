package carnet.exceptions;

/**
 * La classe PageInexistanteException
 */
public class PageInexistanteException extends CarnetException {

    /**
     * Constructeur de l'exception se déclanchant lorsque l'on n'arrive pas à trouver la page recherchée
     *
     * @param message le message
     */
    public PageInexistanteException(String message) {
        super(message);
    }
}
