package game.board.tile;

/**
 * SeaTile class - Represents a sea tile.
 */
public class SeaTile extends Tile {

    /**
     * Constructs a Sea Tile
     * 
     */
    public SeaTile() {
        super();
    }

    
    /**
     * return a description of the SeaTile as a string
     * The description can be placed in a function that can be called later or
     * whenever the player wants.
     * 
     * @return the string of the special Tile
     */
    public String toString() {
        return "~~~";
    }

}
