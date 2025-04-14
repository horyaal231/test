package ares.action;

import java.util.List;
import java.util.ArrayList;

import ares.board.tile.build.ArmyBuild;
import ares.resource.WarriorResource;
import game.action.Action;
import game.action.NotProcessableActionException;
import game.action.ChoseOneTileAction;
import game.board.Board;
import game.board.tile.BuildableTile;
import game.board.tile.Tile;
import game.board.tile.TileNotBuildableException;
import game.board.tile.TileNotOwnableException;
import game.board.tile.build.Build;
import game.player.Player;
import game.player.inventory.Inventory;
import game.resource.NonExistentResourceException;

public class BuildArmyAction extends Action {
    private Player player;
    private Board board;
    
    /**
     * Constructor for the BuildArmyAction class
     * @param board the board to build the army on
     * @param player the player who wants to build the army
     */
    public BuildArmyAction(Board board, Player player) {
        this.player = player;
        this.board = board;
    }

    public void process()
            throws NonExistentResourceException, TileNotBuildableException, NotProcessableActionException,
            TileNotOwnableException {
        List<BuildableTile> tiles = new ArrayList<>();

        if (!player.hasPort()) {
            for (Tile tile : board.getPlayerFreeTiles(this.player, 2)) {
                tiles.add((BuildableTile) tile);
            }
        } else {
            for (Tile tile : board.getFreeTiles()) {
                tiles.add((BuildableTile) tile);
            }
        }
        if (tiles.size() == 0) {
            throw new NotProcessableActionException("No tiles to build the army on");
        }
        ChoseOneTileAction choseOneTileAction = new ChoseOneTileAction(tiles, "Choose a tile to build the army");
        choseOneTileAction.process();
        BuildableTile tile = choseOneTileAction.getselectedTile();


        Build army = new ArmyBuild();
        Inventory inventory = player.getInventory();
        inventory.deleteResources(army.getRequiredResource());
        System.out.println(tile.toString());
        ((BuildableTile) tile).setBuild(army, this.player);
        System.out.println("Une armée a été construit pour le joueur : " + this.player.getName());
    }

    public void process(boolean start)
            throws NonExistentResourceException, TileNotBuildableException, NotProcessableActionException,
            TileNotOwnableException {
        List<BuildableTile> tiles = board.getFreeTiles();
        ChoseOneTileAction choseOneTileAction = new ChoseOneTileAction(tiles, "Choose a tile to build the army");
        choseOneTileAction.process();
        BuildableTile tile = choseOneTileAction.getselectedTile();

        Build army = new ArmyBuild();
        Inventory inventory = player.getInventory();
        inventory.deleteResources(army.getRequiredResource());
        System.out.println(tile.toString());
        ((BuildableTile) tile).setBuild(army, this.player);
        System.out.println("Une armée a été construit pour le joueur : " + this.player.getName());
    }
    public String toString() {
        return "Build Army";
    }

    public static boolean isProcessable(Player player, Board board) {
        List<BuildableTile> tiles = new ArrayList<>();
        Inventory inventory = player.getInventory();
        Build army = new ArmyBuild();
        Inventory inv = army.getRequiredResource();
        inv.addResource(new WarriorResource());
        if (!inventory.hasResources(inv)) {
            return false;
        }

        if (!player.hasPort()) {
            for (Tile tile : board.getPlayerFreeTiles(player, 2)) {
                tiles.add((BuildableTile) tile);
            }
        } else {
            for (Tile tile : board.getFreeTiles()) {
                tiles.add((BuildableTile) tile);
            }
        }
        return tiles.size() != 0;
    }

}

