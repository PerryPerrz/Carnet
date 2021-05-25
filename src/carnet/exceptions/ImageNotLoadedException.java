package carnet.exceptions;

/**
 * La classe ImageNotLoadedException
 */
public class ImageNotLoadedException extends CarnetException {

    /**
     * Constructeur de l'exception se déclanchant lorsque l'on n'arrive pas à charger une image
     *
     * @param message le message
     */
    public ImageNotLoadedException(String message) {
        super(message);
    }
}
