package game.board.tile;

import game.resource.OreResource;


/**
 * MountainTile class - Represents the mountain tile.
 */
public class MountainTile extends BuildableTile {

    /**
     * Constructs a Moutain Tile.
     * 
     */
    public MountainTile() {
        super();
        this.generatedResource = new OreResource();
    }

    /**
     * The description can be placed in a function that can be called later or
     * whenever the player wants.
     * 
     * @return the string of the special Tile
     */
    public String toString() {
        return "M" + super.toString();
    }

}
