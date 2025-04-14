package livrable;

import demeter.DemeterGame;
import game.Game;
import game.player.Player;
import ares.AresGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 */
public class Livrable4 {
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

            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("Choisissez le jeu auquel vous voulez jouer :");
                System.out.println("1: AresGame");
                System.out.println("2: DemeterGame");
                int gameChoice = scanner.nextInt();
                
                Class<? extends Game> gameClass;
                if (gameChoice == 1) {
                    gameClass = AresGame.class;
                } else if (gameChoice == 2) {
                    gameClass = DemeterGame.class;
                } else {
                    System.out.println("Choix invalide. Par défaut, AresGame sera utilisé.");
                    gameClass = AresGame.class;
                }

                System.out.println("Entrez le nombre de joueurs :");
                int numberOfPlayers = scanner.nextInt();
                while (numberOfPlayers < 2) {
                    System.out.println("Le nombre de joueurs doit être supérieur ou égal à 2.");
                    numberOfPlayers = scanner.nextInt();
                }
                scanner.nextLine(); 

                List<Player> players = new ArrayList<>();
                for (int i = 0; i < numberOfPlayers; i++) {
                    System.out.println("Entrez le nom du joueur " + (i + 1) + " :");
                    String playerName = scanner.nextLine();
                    players.add(new Player(playerName, gameClass));
                }


                if (gameClass == AresGame.class) {
                    AresGame game = new AresGame(players, rows, cols);
                    game.initGame();
                    game.startGame();
                    
                } else {
                    DemeterGame game = new DemeterGame(players, rows, cols);
                    game.initGame();
                    game.startGame();
                }

            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid input: all arguments must be integers.");
        } catch (Exception e) {
            System.err.println("Error creating the board: " + e.getMessage());
        }
    }
}

//TODO: mettre à jour les diagrammes UML ( cad enlever les classes qui prennent en paramètre les scanner qui ne sont plus utilisés )