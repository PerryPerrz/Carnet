package carnet.exceptions;

/**
 * La classe CancelImageException
 */
public class CancelImageException extends CarnetException {

    /**
     * Constructeur de l'exception se déclanchant lorsque l'utilisateur annule l'ajout d'une image
     *
     * @param message le message
     */
    public CancelImageException(String message) {
        super(message);
    }
}
