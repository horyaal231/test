package livrable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ares.AresGame;
import ares.board.tile.build.ArmyBuild;
import ares.board.tile.build.CampsBuild;
import demeter.board.tile.build.ExploitationBuild;
import demeter.board.tile.build.FarmBuild;
import game.board.Board;
import game.board.position.Position;
import game.board.tile.BuildableTile;
import game.board.tile.Tile;
import game.board.tile.build.Build;
import game.board.tile.build.PortBuild;
import game.player.Player;
import game.player.inventory.Inventory;

/**
 * Temporary class for Deliverable 2.
 * This class demonstrates the creation and printing of a game board.
 * 
 */
public class Livrable2 {
    public static void help() {
        System.out.println("-- Help --");
        System.out.println("This program creates a game board and prints it to the console.");
        System.out.println("The board is created with the specified number of rows and columns.");
        System.out.println("- <rows>: the number of rows in the board, min 10");
        System.out.println("- <cols>: the number of columns in the board, min 10");
        System.exit(0);
    }

    public static void main(String[] args) {
        try {
            if (args.length < 2) {
                help();
            }

            int rows = Integer.parseInt(args[0]);
            int cols = Integer.parseInt(args[1]);
            if (cols < 10 || rows < 10) {
                help();
            }

            Random rand = new Random();
            Board board = new Board(rows, cols);
            board.printBoard();

            Build[] BuildsToBuild = {
                    new PortBuild(),
                    new FarmBuild(),
                    new ExploitationBuild(),
                    new ArmyBuild(),
                    new CampsBuild()
            };
            Player player1 = new Player("Clément", AresGame.class);
            Player player2 = new Player("Matéo" , AresGame.class);

            for (Build build : BuildsToBuild) {
                System.out.println(build.toString() + ", cost -> " + build.getRequiredResource().toString());
            }

            System.out.println("\n");
            int i = 0;
            List<Position> tilesPositions = new ArrayList<>();
            for (Build build : BuildsToBuild) {
                Position pos = new Position(rand.nextInt(rows), rand.nextInt(cols));
                Tile tile = board.getTile(pos);
                while (!(tile instanceof BuildableTile)
                        || (((BuildableTile) tile).hasBuild())) {
                    pos = new Position(rand.nextInt(rows), rand.nextInt(cols));
                    tile = board.getTile(pos);
                }
                ((BuildableTile) tile).setBuild(build, player2);
                ((BuildableTile) tile).setOwner((((i % 2) == 0) ? player1 : player2));
                tilesPositions.add(pos);
                System.out.println(build.toString() + " has been set on " + pos.toString());
                i++;
            }
            System.out.println("\n");

            for (Position pos : tilesPositions) {
                Tile tile = board.getTile(pos);
                Inventory harvested = ((BuildableTile) tile).harvest();
                System.out.println("Tile " + pos.toString() + " produces: " + harvested.toString());
            }

            System.out.println("\n");
            System.out.println("Board after setting builds and harvesting:");
            board.printBoard();
        } catch (NumberFormatException e) {
            System.err.println("Invalid input: all arguments must be integers.");
        } catch (Exception e) {
            System.err.println("Error creating the board: " + e.getMessage());
        }
    }
}
