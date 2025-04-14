package livrable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import ares.AresGame;
import ares.action.BuildArmyAction;
import ares.action.BuyFiveWarriorsAction;
import ares.action.BuySecretWeaponAction;
import ares.action.PlaceOneSoldierAction;
import ares.action.UpgradeArmyAction;
import ares.resource.WarriorResource;
import game.action.BuildPortAction;
import game.action.ExchangeThreeAgainstOneAction;
import game.board.position.Position;
import game.board.tile.BuildableTile;
import game.board.tile.FieldTile;
import game.player.Player;
import game.resource.OreResource;
import game.resource.SheepResource;
import game.resource.WheatResource;
import game.resource.WoodResource;

public class Livrable3Ares {
    public static void help() {
        System.out.println("-- Help --");
        System.out.println("This program creates a game board and prints it to the console.");
        System.out.println("The board is created with the specified number of rows and columns.");
        System.out.println("- <rows>: the number of rows in the board, min 10");
        System.out.println("- <cols>: the number of columns in the board, min 10");
        System.exit(0);
    }

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

            Player player1 = new Player("Clément", AresGame.class);
            player1.getInventory().addResource(new OreResource(), 10);
            player1.getInventory().addResource(new SheepResource(), 10);
            player1.getInventory().addResource(new WheatResource(), 10);
            player1.getInventory().addResource(new WoodResource(), 10);
            player1.getInventory().addResource(new WarriorResource(), 10);

            List<Player> players = new ArrayList<>();
            players.add(player1);

            AresGame game = new AresGame(players, rows, cols);
            game.getBoard().placeTile(new FieldTile(), new Position(0, 0));
            game.getBoard().placeTile(new FieldTile(), new Position(0, 1));
            game.getBoard().placeTile(new FieldTile(), new Position(0, 2));
            game.getBoard().placeTile(new FieldTile(), new Position(0, 3));
            game.getBoard().printBoard();
            player1.getInventory().print();

            BuildArmyAction army = new BuildArmyAction(game.getBoard(), player1);
            System.out.println("Building army for player 1");
            simulateInput("0");
            army.process(true);
            restoreSystemInOut();
            game.getBoard().printBoard();
            player1.getInventory().print();

            BuildArmyAction army2 = new BuildArmyAction(game.getBoard(), player1);
            System.out.println("Building second army for player 1");

            simulateInput("0");
            army2.process(true);
            restoreSystemInOut();
            game.getBoard().printBoard();
            player1.getInventory().print();

            PlaceOneSoldierAction soldier2 = new PlaceOneSoldierAction(game.getBoard(), player1);
            System.out.println("Placing soldier for player 1");
            simulateInput("0");
            soldier2.process();
            restoreSystemInOut();
            game.getBoard().printBoard();
            player1.getInventory().print();

            PlaceOneSoldierAction soldier3 = new PlaceOneSoldierAction(game.getBoard(), player1);
            System.out.println("Placing soldier for player 1");
            simulateInput("0");
            soldier3.process();
            restoreSystemInOut();
            game.getBoard().printBoard();
            player1.getInventory().print();

            PlaceOneSoldierAction soldier4 = new PlaceOneSoldierAction(game.getBoard(), player1);
            System.out.println("Placing soldier for player 1");
            simulateInput("0");
            soldier4.process();
            restoreSystemInOut();
            game.getBoard().printBoard();
            player1.getInventory().print();

            PlaceOneSoldierAction soldier5 = new PlaceOneSoldierAction(game.getBoard(), player1);
            System.out.println("Placing soldier for player 1");
            simulateInput("0");
            soldier5.process();
            restoreSystemInOut();
            game.getBoard().printBoard();
            player1.getInventory().print();

            UpgradeArmyAction upgrade = new UpgradeArmyAction(game.getBoard(), player1);
            System.out.println("Upgrading army for player 1");
            simulateInput("0");
            upgrade.process();
            restoreSystemInOut();
            game.getBoard().printBoard();
            player1.getInventory().print();

            BuyFiveWarriorsAction buy = new BuyFiveWarriorsAction(player1);
            System.out.println("Buying 5 warriors for player 1");
            simulateInput("0");
            buy.process();
            restoreSystemInOut();
            game.getBoard().printBoard();
            player1.getInventory().print();

            BuildPortAction port = new BuildPortAction(game.getBoard(), player1);
            System.out.println("Building port for player 1");
            simulateInput("0");
            port.process();
            restoreSystemInOut();
            game.getBoard().printBoard();
            player1.getInventory().print();

            ExchangeThreeAgainstOneAction exchange = new ExchangeThreeAgainstOneAction(player1);
            System.out.println("Exchanging 3 against 1 for player 1");
            simulateInput("0");
            exchange.process();
            restoreSystemInOut();
            game.getBoard().printBoard();
            player1.getInventory().print();

            BuySecretWeaponAction secret = new BuySecretWeaponAction(player1);
            System.out.println("Buying secret weapon for player 1");
            simulateInput("0");
            secret.process();
            restoreSystemInOut();
            game.getBoard().printBoard();
            player1.getInventory().print();

            List<BuildableTile> playerTiles = game.getBoard().getPlayerTiles(player1);
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
