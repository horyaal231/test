package game.board.tile;

import game.resource.WheatResource;

/**
 * FieldTile class - Represents the field tile.
 */
public class FieldTile extends BuildableTile {

    /**
     * Constructs a Field Tile.
     * 
     */
    public FieldTile() {
        super();
        this.generatedResource = new WheatResource();
    }

    /**
     * The description can be placed in a function that can be called later or
     * whenever the player wants.
     * 
     * @return the string of the special Tile
     */
    public String toString() {
        return "F" + super.toString();
    }

}