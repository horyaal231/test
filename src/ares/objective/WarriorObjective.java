package ares.objective;

import java.util.List;

import ares.board.tile.build.WarriorsContainingBuild;
import game.board.Board;
import game.board.tile.BuildableTile;
import game.objective.Objective;
import game.player.Player;

public class WarriorObjective extends Objective {
    private static final int NB_WARRIORS = 100;
    int nbWarriors = 0;

    public boolean checkObjective(Board board, Player player) {
        List<BuildableTile> tiles = board.getPlayerTiles(player);
        int nbWarriors = 0;
        for (BuildableTile tile : tiles) {
            if (tile.getBuild() instanceof WarriorsContainingBuild) {
                nbWarriors += ((WarriorsContainingBuild) tile.getBuild()).getNbWarriors();
            }
        }
        return nbWarriors >= NB_WARRIORS;
    }

    @Override
    public String getName() {
        return "Warrior Objective";
    }
}
