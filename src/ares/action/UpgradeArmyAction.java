package ares.action;

import java.util.List;

import ares.board.tile.build.ArmyBuild;
import ares.board.tile.build.CampsBuild;
import ares.resource.WarriorResource;
import game.action.Action;
import game.action.NotProcessableActionException;
import game.action.ChoseOneTileAction;
import game.board.Board;
import game.board.tile.BuildableTile;
import game.board.tile.TileNotBuildableException;
import game.board.tile.TileNotOwnableException;
import game.player.Player;
import game.player.inventory.Inventory;
import game.resource.NonExistentResourceException;

public class UpgradeArmyAction extends Action {

    private BuildableTile tile;
    private Player player;
    private Board board;

    

    /**
     * Constructor for the UpgradeArmyAction class
     * 
     * @param board  game board
     * @param player player who wants to upgrade the army
     */
    public UpgradeArmyAction(Board board, Player player) {
        this.player = player;
        this.board = board;
    }

    public void process() throws NonExistentResourceException, TileNotBuildableException, TileNotOwnableException, NotProcessableActionException {
        List<BuildableTile> tiles = board.getPlayerTiles(player, ArmyBuild.class);
        ChoseOneTileAction selectedTile = new ChoseOneTileAction(tiles, "Choose a tile to upgrade the army");
        selectedTile.process();
        this.tile = selectedTile.getselectedTile();
        
        CampsBuild camps = new CampsBuild();
        this.player.getInventory().deleteResources(camps.getRequiredResource());
        this.tile.resetTile();
        this.tile.setBuild(camps, this.player);
        System.out.println("An army has been upgraded to a camps for : " + this.player.getName());
    }

    public String toString() {
        return "Upgrade Army";
    }

    /**
     * Checks if the player has the necessary resources
     * 
     * @param player the player who wants to upgrade the army
     * @return true if the player has the necessary resources, false otherwise
     */
    public static boolean isProcessable(Player player, Board board) {
        CampsBuild camps = new CampsBuild();
        Inventory inv = camps.getRequiredResource();
        inv.addResource(new WarriorResource());

        Inventory inventory = player.getInventory();
        if (!inventory.hasResources(inv)) {
            return false;
        }

        List<BuildableTile> tiles = board.getPlayerTiles(player, ArmyBuild.class);
        
        return !tiles.isEmpty();

    }
}
