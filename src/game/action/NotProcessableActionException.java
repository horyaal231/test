package game.action;

/**
 * NotProcessableActionException class - Represents an exception that occurs when an action is not processable.
 */
public class NotProcessableActionException extends Exception{
    /**
     * Constructor for the NotProcessableActionException class.
     * @param message The message to be displayed.
     */
    public NotProcessableActionException(String message){
        super(message);
    }
}
