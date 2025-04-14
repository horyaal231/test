package game.board.tile;

import game.resource.SheepResource;

/**
 * PastureTile class - Represents a pasture tile.
 */
public class PastureTile extends BuildableTile {

    /**
     * Constructs a Pasture Tile.
     * 
     */
    public PastureTile() {
        super();
        this.generatedResource = new SheepResource();
    }

    /**
     * The description can be placed in a function that can be called later or
     * whenever the player wants.
     * 
     * @return the string of the special Tile
     */
    public String toString() {
        return "P" + super.toString();
    }

}