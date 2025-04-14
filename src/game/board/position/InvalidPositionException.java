package game.board.position;

/**
 * Exception thrown to indicate that a position is invalid.
 */
public class InvalidPositionException extends Exception{
    
    /**
     * Constructs a new InvalidPositionException with no detail message.
     */
    public InvalidPositionException() {
        super();
    }

    /**
     * Constructs a new InvalidPositionException with the specified detail message.
     * 
     * @param arg0 the detail message
     */
    public InvalidPositionException(String arg0) {
        super(arg0);
    }
}
