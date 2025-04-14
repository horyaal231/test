package livrable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import demeter.DemeterGame;
import demeter.action.*;
import game.action.*;
import game.board.position.Position;
import game.board.tile.BuildableTile;
import game.board.tile.FieldTile;
import game.player.Player;

import game.resource.*;
import demeter.resource.ThiefResource;

public class Livrable3Demeter {

    private static void changeSystemOut() {
        systemIn = System.in;
        systemOut = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        System.out.println("NE SERA PAS VISIBLE SUR LA SORTIE STANDARD");
    }

    private static InputStream systemIn;
    private static PrintStream systemOut;

    private static void restoreSystemInOut() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    public static void simulateInput(String input) {
        changeSystemOut();
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }

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

            List<Player> players = new ArrayList<>();
            Player player = new Player("Clément", DemeterGame.class);
            player.getInventory().addResource(new WheatResource(), 10);
            player.getInventory().addResource(new OreResource(), 10);
            player.getInventory().addResource(new WoodResource(), 10);
            player.getInventory().addResource(new SheepResource(), 10);
            player.getInventory().addResource(new ThiefResource(), 10);

            players.add(player);
            player.setHasPort(true);
            DemeterGame game = new DemeterGame(players, rows, cols);
            game.getBoard().placeTile(new FieldTile(), new Position(0, 0));
            game.getBoard().placeTile(new FieldTile(), new Position(0, 1));
            game.getBoard().placeTile(new FieldTile(), new Position(0, 2));
            game.getBoard().placeTile(new FieldTile(), new Position(0, 3));
            player.getInventory().print();

            game.getBoard().printBoard();
            BuildFarmAction farm = new BuildFarmAction(game.getBoard(), player);
            System.out.println("Build Farm Action");
            simulateInput("0");
            farm.process();
            restoreSystemInOut();
            player.getInventory().print();

            game.getBoard().printBoard();
            UpgradeFarmAction exp = new UpgradeFarmAction(game.getBoard(), player);
            System.out.println("Upgrade Farm Action");

            simulateInput("0");
            exp.process();
            restoreSystemInOut();
            player.getInventory().print();

            game.getBoard().printBoard();
            BuildPortAction port = new BuildPortAction(game.getBoard(), player);
            System.out.println("Build Port Action");
            simulateInput("0");
            port.process();
            restoreSystemInOut();

            player.getInventory().print();

            game.getBoard().printBoard();
            ExchangeThreeAgainstOneAction exchangeThreeAgainstOneAction = new ExchangeThreeAgainstOneAction(player);
            System.out.println("Exchange Three Against One Action");
            simulateInput("0");

            exchangeThreeAgainstOneAction.process();
            restoreSystemInOut();

            player.getInventory().print();

            game.getBoard().printBoard();
            ExchangeWithAPortAction exchangeWithAPortAction = new ExchangeWithAPortAction(player);
            System.out.println("Exchange With A Port Action");
            simulateInput("0");
            exchangeWithAPortAction.process();
            restoreSystemInOut();

            player.getInventory().print();

            game.getBoard().printBoard();
            BuyAThiefAction buyAThiefAction = new BuyAThiefAction(game, player);
            System.out.println("Buy A Thief Action");
            simulateInput("0");
            
            buyAThiefAction.process();
            restoreSystemInOut();
            
            player.getInventory().print();

            List<BuildableTile> playerTiles = game.getBoard().getPlayerTiles(player);
            for (BuildableTile tile : playerTiles) {
                tile.toString();
            }
            System.out.println("\n");
            System.out.println("Player 1 tiles: " + playerTiles.size() + " tiles");
            for (BuildableTile tile : playerTiles) {
                System.out.println(tile.toString() + " détenu par " + tile.getOwner().getName() + " sur la position " + tile.getPosition().toString());
            }

        } catch (NumberFormatException e) {
            System.err.println("Invalid input: all arguments must be integers.");
        } catch (Exception e) {
            System.err.println("Error creating the board: " + e.getMessage());
        }
    }
}
