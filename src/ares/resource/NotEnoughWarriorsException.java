package ares.resource;

/**
 * NotEnoughWarriorsException class - Represents the exception thrown when there are not enough warriors to perform an action.
 */
public class NotEnoughWarriorsException extends Exception{
    /**
     * Constructor for the NotEnoughWarriorsException class.
     * @param message The message to be displayed.
     */
    public NotEnoughWarriorsException(String message){
        super(message);
    }
}
