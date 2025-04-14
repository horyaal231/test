package game.board.tile;

/**
 * TileNotOwnableException class - Represents an exception thrown when a tile is not ownable.
 */
public class TileNotOwnableException extends Exception {
    /**
     * Constructor for the TileNotOwnableException class.
     */
    public TileNotOwnableException() {
        super();
    }

    /**
     * Constructor for the TileNotOwnableException class.
     * @param arg0 the detail message
     */
    public TileNotOwnableException(String arg0) {
        super(arg0);
    }
}
