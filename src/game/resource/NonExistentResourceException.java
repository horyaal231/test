package game.resource;

/**
 * NonExistentResourceException class - Represents an exception that is thrown when a resource does not exist.
 */
public class NonExistentResourceException extends Exception {
    /**
     * Constructor for the NonExistentResourceException class.
     * 
     * @param message The message to be displayed when the exception is thrown.
     */
    public NonExistentResourceException(String message) {
        super(message);
    }
}