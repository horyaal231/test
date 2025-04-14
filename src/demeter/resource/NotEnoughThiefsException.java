package demeter.resource;

/**
 * NotEnoughThiefsException class - Represents the exception thrown when there are not enough thiefs to rob a player.
 */
public class NotEnoughThiefsException extends Exception{

    /**
     * Constructor for the NotEnoughThiefsException class.
     * @param message The message to be displayed.
     */
    public NotEnoughThiefsException(String message){
        super(message);
    }
}
