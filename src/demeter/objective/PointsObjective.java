package demeter.objective;

import demeter.board.tile.build.ExploitationBuild;
import demeter.board.tile.build.FarmBuild;
import game.board.Board;
import game.objective.Objective;
import game.player.Player;

public class PointsObjective extends Objective{
    private static final int POINTS_TO_WIN = 12;

    /**
     * Checks if the player has reached the objective.
     * 
     */
    public boolean checkObjective(Board board, Player player) {

        int points = 0;
        points += board.getPlayerTiles(player, FarmBuild.class).size();
        points += board.getPlayerTiles(player, ExploitationBuild.class).size()*2;
        int nbIsland = board.getNbPlayerIsland(player);
        points += ((nbIsland > 2)? 2 : (nbIsland) >= 2 ? 1 : 0); 

        return points >= POINTS_TO_WIN;
    }

    public String getName() {
        return "Points Objective";
    }
}
