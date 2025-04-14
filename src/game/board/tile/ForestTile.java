package game.board.tile;

import game.resource.WoodResource;

/**
 * ForestTile class - Represents the forest tile.
 */
public class ForestTile extends BuildableTile {

    /**
     * Constructs a Forest Tile.
     * 
     */
    public ForestTile() {
        super();
        this.generatedResource = new WoodResource();
    }

    /**
     * The description can be placed in a function that can be called later or
     * whenever the player wants.
     * 
     * @return the string of the special Tile
     */
    public String toString() {
        return "T" + super.toString();
    }

}