package demeter.action;

import java.util.List;

import demeter.board.tile.build.FarmBuild;
import game.action.Action;
import game.action.NotProcessableActionException;
import game.action.ChoseOneTileAction;
import game.board.Board;
import game.board.tile.BuildableTile;
import game.board.tile.TileNotBuildableException;
import game.board.tile.TileNotOwnableException;
import game.board.tile.build.Build;
import game.player.Player;
import game.player.inventory.Inventory;
import game.resource.NonExistentResourceException;

public class BuildFarmAction extends Action {
    private Player player;
    private Board board;

    public BuildFarmAction(Board board, Player player) {
        this.player = player;
        this.board = board;
    }

    public void process() throws NonExistentResourceException, TileNotBuildableException, TileNotOwnableException, NotProcessableActionException {
        List<BuildableTile> BuildableTiles = board.getFreeTiles();

        if (BuildableTiles.isEmpty()) {
            throw new TileNotBuildableException("No buildable tile");
        }
        ChoseOneTileAction choseOneTileAction = new ChoseOneTileAction(BuildableTiles, "Choose a tile to build the farm");
        choseOneTileAction.process();
        BuildableTile tile = choseOneTileAction.getselectedTile();

        Build farm = new FarmBuild();
        Inventory inventory = this.player.getInventory();

        inventory.deleteResources(farm.getRequiredResource());

        ((BuildableTile) tile).setBuild(farm, this.player);
        System.out.println("Une ferme a été construite pour le joueur : " + this.player.getName());
    }

    public String toString() {
        return "Build Farm";
    }

    public static boolean isProcessable(Player player, Board board) {
        Inventory inventory = player.getInventory();
        Build farm = new FarmBuild();
        if (inventory.hasResources(farm.getRequiredResource())) {
            return false;
        }
        List<BuildableTile> playerTile = board.getPlayerTiles(player);
        for (BuildableTile tile : playerTile) {
            if (tile.getBuild() == null) {
                return true;
            }
        }
        return false;
    }
}
