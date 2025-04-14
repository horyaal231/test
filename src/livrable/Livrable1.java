package livrable;

import game.board.Board;

/**
 * Temporary class for Deliverable 1.
 * This class demonstrates the creation and printing of a game board.
 * ex : 
 * javac -sourcepath src -d classes src/game/board/*.java 
 * java -classpath classes game.board.BoardMain 6 7 2
 * 
 */
public class Livrable1 {
    public static void help() {
        System.out.println("-- Help --");
        System.out.println("This program creates a game board and prints it to the console.");
        System.out.println("The board is created with the specified number of rows and columns.");
        System.out.println("- <rows>: the number of rows in the board");
        System.out.println("- <cols>: the number of columns in the board");
        System.exit(0);
    }
    public static void main(String[] args) {
        try {
            if (args.length < 2) {
               help();
            }

            int rows = Integer.parseInt(args[0]);
            int cols = Integer.parseInt(args[1]);

            Board board = new Board(rows, cols);
            board.printBoard();
        } catch (NumberFormatException e) {
            System.err.println("Invalid input: all arguments must be integers.");
        } catch (Exception e) {
            System.err.println("Error creating the board: " + e.getMessage());
        }
    }
}
