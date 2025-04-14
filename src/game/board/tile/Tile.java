package game.board.tile;

import game.board.position.Position;

/**
 * Represents a Tile on the game board that may yield resources.
 */
public abstract class Tile {
    /**
     * The position of the tile on the board.
     */
    public Position position;

    /**
     * Constructs a Tile with no specified position and no resource generation.
     * 
     */
    public Tile() {
        this.position = null;
    }

    /**
     * Sets the position of the tile.
     * 
     * @param position the position of this tile
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Gets the position of the tile on the board.
     * 
     * @return the position of the tile
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * String the owner of the tile if the tile had a owner and the build if the
     * tile had build
     * 
     * @return the string of the tile
     */
    public String toString() {
        return "  ";

    }
}
