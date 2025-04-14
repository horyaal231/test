package game.objective;

import game.board.Board;
import game.player.Player;

/**
 * TileObjective class is a class that represents the objective of having a certain number of tiles.
 */
public class TileObjective extends Objective {
    private static final int NB_TILES = 10;

    /**
     * Constructs a TileObjective.
     */
    public TileObjective() {
        super();
    }
    /**
     * Constructs a TileObjective.
     */
    public boolean checkObjective(Board board, Player player) {
        return board.getPlayerTiles(player).size() >= NB_TILES;
    }

    /**
     * Gets the name of the objective.
     *
     * @return the name of the objective
     */
    public String getName() {
        return "Tile Objective";
    }
}
