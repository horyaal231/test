package game.board.tile;

/**
 * Exception thrown to indicate that a tile cannot be upgraded.
 */
public class TileNotUpgradableException extends Exception {
    
    /**
     * Constructs a new TileNotUpgradableException with no detail message.
     */
    public TileNotUpgradableException() {
        super();
    }

    /**
     * Constructs a new TileNotUpgradableException with the specified detail
     * message.
     * 
     * @param arg0 the detail message
     */
    public TileNotUpgradableException(String arg0) {
        super(arg0);
    }
}
