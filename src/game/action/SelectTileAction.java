package game.action;

import java.util.List;
import util.Input;

import game.board.Board;
import game.board.position.Position;
import game.board.tile.BuildableTile;
import game.board.tile.Tile;
import game.player.Player;

/**
 * Represents an action where the player selects a tile on the board.
 */
public class SelectTileAction extends Action {
    private Tile selectedTile;
    private Board board;
    private Class<?> targetClass;
    private Player player;

    /**
     * Constructor for the SelectTileAction class.
     * 
     * @param board       the game board where selection occurs
     * @param targetClass the class type to be processed
     * @param player      the player making the selection
     */
    public SelectTileAction(Board board, Class<?> targetClass, Player player) {
        this.player = player;
        this.board = board;
        this.targetClass = targetClass;
    }

    /**
     * Asks the player for tile coordinates and retrieves the corresponding tile
     * from the board.
     * 
     */
    public void process() {
        List<BuildableTile> playerTiles = board.getPlayerTiles(this.player);
        playerTiles.removeIf(tile -> !targetClass.isInstance(tile));
        if (playerTiles.isEmpty()) {
            System.out.println("You don't own any buildable tiles.");
            return;
        }

        // Display available tiles
        System.out.println("Available tiles:");
        for (BuildableTile tile : playerTiles) {
            System.out.println(" - (" + tile.getPosition().getPosX() + ", " + tile.getPosition().getPosY() + ")");
        }

        try {
            int x, y;
            boolean validChoice = false;

            while (!validChoice) {
                System.out.println(player.getName() + ", enter the X coordinate of the tile: ");
                x = Input.readInt();
                System.out.println(player.getName() + ", enter the Y coordinate of the tile: ");
                y = Input.readInt();

                if (x < 0 || x >= board.getRows() || y < 0 || y >= board.getCols()) {
                    System.out.println("Invalid coordinates! Coordinates must be within the board. Try again.");
                    continue;
                }
                Tile chosenTile = board.getTile(new Position(x, y));

                if (chosenTile == null) {
                    System.out.println("Invalid coordinates! No tile found at (" + x + ", " + y + "). Try again.");
                    continue;
                }

                /* check if is a playerTiles */
                for (BuildableTile tile : playerTiles) {
                    if (tile.getPosition().getPosX() == x && tile.getPosition().getPosY() == y) {
                        selectedTile = tile;
                        validChoice = true;
                        System.out.println("Tile selected at (" + x + ", " + y + ").");
                        break;
                    }
                }

                if (!validChoice) {
                    System.out.println("You do not own a buildable tile at (" + x + ", " + y + "). Try again.");
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid input! Please enter a valid integer.");
        }
    } 

    /**
     * Gets the selected tile.
     * 
     * @return The tile selected by the player.
     */
    public Tile getSelectedTile() {
        return selectedTile;
    }

    public String toString() {
        return "Selection a tile";
    }

}
