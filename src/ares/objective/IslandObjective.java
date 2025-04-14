package ares.objective;

import game.board.Board;
import game.objective.Objective;
import game.player.Player;


public class IslandObjective extends Objective {
    private static final int NB_ISLANDS = 3;
    int nbIslands = 0;

    public boolean checkObjective(Board board, Player player) {
        int nbPlayerIsland = board.getNbPlayerIsland(player);
        return nbPlayerIsland >= NB_ISLANDS;

    }

    @Override
    public String getName() {
        return "Island Objective";
    }

}
