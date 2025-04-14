package game.board.tile;

/**
 * TileNotBuildableException class - Represents an exception thrown when a tile is not buildable.
 */
public class TileNotBuildableException extends Exception{
    /**
     * Constructor for the TileNotBuildableException class.
     */
    public TileNotBuildableException() {
        super("This tile is not buildable");
    }

    /**
     * Constructor for the TileNotBuildableException class.
     * @param arg0 the detail message
     */
    public TileNotBuildableException(String arg0) {
        super(arg0);
    }
}

