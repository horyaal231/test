package game.action;

import java.util.ArrayList;
import java.util.List;

import game.board.Board;
import game.board.position.Position;
import game.board.tile.BuildableTile;
import game.board.tile.SeaTile;
import game.board.tile.Tile;
import game.board.tile.TileNotBuildableException;
import game.board.tile.TileNotOwnableException;
import game.board.tile.build.PortBuild;
import game.player.Player;
import game.player.inventory.Inventory;
import game.resource.NonExistentResourceException;

/**
 * BuildPortAction class - Represents the action of building a port.
 */
public class BuildPortAction extends Action {
    private Tile tile;
    private Player player;
    private Board board;

    /**
     * Constructor for the BuildPortAction class.
     * 
     * @param board   the board to build the port on
     * @param player  the player who wants to build the port
     */
    public BuildPortAction(Board board, Player player) {
        this.player = player;
        this.board = board;    
    }


    /**
     * Processes the action of building a port.
     * 
     * @throws TileNotBuildableException if the tile is not buildable
     * @throws NonExistentResourceException if the player does not have the required resources
     * @throws TileNotOwnableException if the tile is not ownable
     * @throws NotProcessableActionException if the action cannot be processed
     */
    public void process()
            throws TileNotBuildableException, NonExistentResourceException, TileNotOwnableException, NotProcessableActionException {
        List<BuildableTile> tiles = new ArrayList<>();
        // if (!isProcessable(this.player, this.board)) {
        //     throw new NotProcessableActionException("Cannot build port - you need to have at least one port on another island, and 2 buildings on each island");
        // }
        if (!player.hasPort()) {
            for (Tile tile : board.getPlayerFreeTiles(this.player, 2)) {
                if (verifySeaNeighboor(tile, board)) {
                    tiles.add((BuildableTile) tile);
                }
            }
        } else {
            for (Tile tile : board.getFreeTiles()) {
                if (verifySeaNeighboor(tile, board)) {
                    tiles.add((BuildableTile) tile);
                }
            }
        }
        if (tiles.size() == 0) {
            throw new NotProcessableActionException("No tiles to build the port on");
        }
        ChoseOneTileAction choseOneTileAction = new ChoseOneTileAction(tiles, "Choose a tile to build the port");
        choseOneTileAction.process();
        this.tile = choseOneTileAction.getselectedTile();
        
        PortBuild port = new PortBuild();
        Inventory inventory = player.getInventory();
        inventory.deleteResources(port.getRequiredResource());
        ((BuildableTile) tile).setBuild(port, player);
        player.givePort();
        System.out.println("Un port a été construit pour le joueur : " + player.getName());
    }

    
    /**
     * Checks if the tile has a sea neighbor.
     * 
     * @param tile the tile to check
     * @param board the board to check
     * @return true if the tile has a sea neighbor, false otherwise
     */
    private static Boolean verifySeaNeighboor(Tile tile, Board board) {
        List<Position> neighborPos = tile.getPosition().getPosNeighbours(board.getCols(), board.getRows());
        Boolean hasSeaNeighboor = false;
        for (Position pos : neighborPos) {
            Tile neighTile = board.getTile(pos);
            if (neighTile instanceof SeaTile) {
                hasSeaNeighboor = true;
            }
        }
        if (!hasSeaNeighboor) return false;
        return true;

    }

    public String toString() {
        return "Build Port";
    }

    /**
     * Checks if the player has the necessary resources and if there is a buildable
     * tile with a sea neighbor
     * 
     * @param player the player who wants to build the port
     * @param board  the board to build the port on
     * @return true if the player has the necessary resources and there is a buildable
     * tile with a sea neighbor, false otherwise
     */
    public static boolean isProcessable(Player player, Board board) {
        List<BuildableTile> tiles = new ArrayList<>();
        Inventory inventory = player.getInventory();
        PortBuild port = new PortBuild();
        if (!inventory.hasResources(port.getRequiredResource())) {
            return false;
        }

        if (!player.hasPort()) {
            for (Tile tile : board.getPlayerFreeTiles(player, 2)) {
                if (verifySeaNeighboor(tile, board)) {
                    tiles.add((BuildableTile) tile);
                }
            }
        } else {
            for (Tile tile : board.getFreeTiles()) {
                if (verifySeaNeighboor(tile, board)) {
                    tiles.add((BuildableTile) tile);
                }
            }
        }
        return tiles.size() != 0;
    }
}
