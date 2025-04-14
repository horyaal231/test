package game.objective;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import game.board.Board;
import game.player.Player;

/**
 * Represents a Objective on the game board.
 */
public abstract class Objective {

    private static final List<Objective> OBJECTIVES = new ArrayList<>();

     /**
     * Assigns a random objective to a player
     *
     * @return a randomly selected objective from the list of objectives.
     */
    public static Objective assignRandomObjective() {
        Collections.shuffle(OBJECTIVES);
        return OBJECTIVES.get(0);
    }


     /**
     * check if the current objective is okay for a player 
     *
     * @param board  The board of the game.
     * @param player The player whose objective is being checked
     * @return true if the objective is verified
     */
    public abstract boolean checkObjective(Board board, Player player);

    /**
     * Gets the name of the objective.
     *
     * @return the name of the objective
     */
    public abstract String getName();
}


