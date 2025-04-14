package game.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import game.board.position.Position;
import game.board.tile.BuildableTile;
import game.board.tile.FieldTile;
import game.board.tile.ForestTile;
import game.board.tile.MountainTile;
import game.board.tile.PastureTile;
import game.board.tile.SeaTile;
import game.board.tile.Tile;
import game.board.tile.build.Build;
import game.player.Player;

/**
 * Represents a game board composed of tiles.
 * The board consists of various types of tiles such as sea, field, forest,
 * mountain, and pasture.
 */
public class Board {
    private Tile[][] board;
    private int rows;
    private int cols;
    private int totalTiles;
    private static final double SEATILEPERCENTAGE = 0.67;
    private static final String RESET = "\u001B[0m";
    private static final String BLUE = "\u001B[34m"; // Bleu pour la mer
    private static final String GREEN = "\u001B[32m"; // Vert pour la forêt et pâturages
    private static final String YELLOW = "\u001B[33m";// Jaune pour les champs
    private static final String PURPLE = "\u001B[35m";// Violet pour les montagnes
    private static final String RED = "\u001B[31m"; // Rouge pour le pâturage
    private static final String BORDER_COLOR = "\u001B[36m"; // Cyan pour la bordure

    /**
     * Constructs a Board with the specified dimensions and constraints.
     *
     * @param rows the number of rows in the board
     * @param cols the number of columns in the board
     */
    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.totalTiles = this.rows * this.cols;
        this.board = new Tile[rows][cols];
        generateBoard();
    }

    /**
     * Generates the board with random tiles while maintaining the sea tile
     * percentage.
     */
    private void generateBoard() {
        int seaTilesCount = (int) Math.floor(this.totalTiles * Board.SEATILEPERCENTAGE);
        List<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < seaTilesCount; i++) {
            tiles.add(new SeaTile());
        }

        for (int i = seaTilesCount; i < this.totalTiles; i++) {
            BuildableTile tile;
            int type = new Random().nextInt(4);
            switch (type) {
                case 0:
                    tile = new FieldTile();
                    break;
                case 1:
                    tile = new ForestTile();
                    break;
                case 2:
                    tile = new MountainTile();
                    break;
                default:
                    tile = new PastureTile();
                    break;
            }
            tiles.add(tile);
        }

        Collections.shuffle(tiles);

        int index = 0;
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                Tile tile = tiles.get(index);
                this.placeTile(tile, new Position(row, col));
                index++;
            }
        }

        ensureLandAdjacency();
    }

    public void placeTile(Tile tile, Position position) {
        if(position.getPosX() < 0 || position.getPosX() >= this.rows || position.getPosY() < 0 || position.getPosY() >= this.cols) {
            throw new IllegalArgumentException("Invalid position");
        }
        tile.setPosition(position);
        this.board[position.getPosX()][position.getPosY()] = tile;
    }

    /**
     * Return list tiles of owned tiles of the player, all these tiles contains
     * Builds
     * 
     * @param player the player
     * @return list tiles
     */
    public List<BuildableTile> getPlayerTiles(Player player) {
        List<BuildableTile> tiles = new ArrayList<>();
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < cols; col++) {
                Tile tile = this.getTile(new Position(row, col));
                if (tile instanceof BuildableTile && ((BuildableTile) tile).getOwner() == player) {
                    tiles.add((BuildableTile) tile);
                }
            }
        }
        return tiles;
    }

    /**
     * Return list tiles of owned tiles of the given type of the player, all these
     * tiles contains
     * Builds
     * 
     * @param player the player
     * @param type   the type of the tile
     * @return list tiles
     */
    public List<BuildableTile> getPlayerTiles(Player player, Class<? extends Build> type) {
        List<BuildableTile> tiles = new ArrayList<>();
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < cols; col++) {
                Tile tile = this.getTile(new Position(row, col));
                if (tile instanceof BuildableTile
                        && ((BuildableTile) tile).getOwner() == player
                        && type.isInstance(((BuildableTile) tile).getBuild())) {
                    tiles.add((BuildableTile) tile);
                }
            }
        }
        return tiles;
    }

    /**
     * Ensures that all non-sea tiles have at least one non-sea neighbor.
     */
    private void ensureLandAdjacency() {
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < cols; col++) {
                Tile tile = this.getTile(new Position(row, col));
                Position position = new Position(row, col);
                if (tile instanceof BuildableTile && !this.hasNonSeaNeighbor(position)) {
                    this.placeTile(new SeaTile(), position);
                }
            }
        }
    }

    /**
     * Checks if a tile at the specified position has any non-sea neighbors.
     *
     * @param row the row index of the tile
     * @param col the column index of the tile
     * @return true if there is at least one non-sea neighbor, false otherwise
     */
    private boolean hasNonSeaNeighbor(Position position) {
        for (Position neighbor : position.getPosNeighbours(this.rows, this.cols)) {
            int newRow = neighbor.getPosX();
            int newCol = neighbor.getPosY();
            if (newRow >= 0 && newRow < this.rows && newCol >= 0 && newCol < this.cols) {
                if (this.getTile(new Position(newRow, newCol)) instanceof BuildableTile) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Calculates and returns the percentage of `SeaTile` tiles on the current
     * board.
     *
     * This method iterates through the entire board (`board`) and counts the number
     * of tiles
     * that are instances of `SeaTile`. It then calculates the percentage of these
     * tiles
     * relative to the total number of tiles on the board.
     *
     * @return The percentage of `SeaTile` tiles on the board as a double value.
     */
    @SuppressWarnings("unused")
    private double verifSeaPercentage() {
        int seaTileCount = 0;
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                if (this.getTile(new Position(row, col)) instanceof SeaTile) {
                    seaTileCount++;
                }
            }
        }
        return (seaTileCount / (double) this.totalTiles);
    }

    /**
     * Retrieves the board.
     *
     * @return the 2D array representing the board
     */
    public Tile[][] getBoard() {
        return this.board;
    }

    /**
     * Retrieves the board.
     *
     * @param position the position of the tile
     * @return the tile at position
     */
    public Tile getTile(Position position) {
        return this.board[position.getPosX()][position.getPosY()];
    }


    /**
     * Prints the board to the console, displaying the type of each tile.
     */
    public void printBoard() {
        System.out.println("=== Plateau de Jeu ===");
        System.out.println();

        System.out.print("   ");
        for (int col = 0; col < this.cols; col++) {
            System.out.printf("|  %-2d ", col);
        }
        System.out.print("|");
        System.out.println();

        System.out.println(BORDER_COLOR + "-".repeat(this.cols * 6 + 4) + RESET);

        for (int row = 0; row < this.rows; row++) {
            System.out.printf("%2d | ", row);

            for (int col = 0; col < this.cols; col++) {
                Tile tile = this.getTile(new Position(row, col));

                String tileString = this.colorStringTile(tile);
                String formattedTile = String.format("%-3s", tileString);

                boolean nextIsSeaTile = (col < this.cols - 1) && (this.getTile(new Position(row, col+1))instanceof SeaTile);

                System.out.print(formattedTile);
                if (!nextIsSeaTile || !(tile instanceof SeaTile)) {
                    System.out.print(" | ");
                } else {
                    String str = BLUE + "~~~" + RESET;
                    System.out.print(str);
                }
            }
            System.out.println();

            if (row < this.rows - 1) {
                System.out.print("   |");
                for (int col = 0; col < this.cols; col++) {
                    boolean belowIsSeaTile = (this.getTile(new Position(row+1, col)) instanceof SeaTile);
                    boolean isSeaTile = (this.getTile(new Position(row, col)) instanceof SeaTile);

                    if ((!isSeaTile && !belowIsSeaTile) || !isSeaTile || !belowIsSeaTile) {
                        if (col == this.cols - 1) {
                            System.out.print("-----");
                        } else
                            System.out.print("----- ");

                    } else {
                        String str;
                        if (col == this.cols - 1) {
                            str = BLUE + "~~~~~" + RESET;
                        } else
                            str = BLUE + "~~~~~ " + RESET;

                        System.out.print(str);
                    }
                }
                System.out.print("|");
                System.out.println();
            }
        }
        System.out.println(BORDER_COLOR + "-".repeat(this.cols * 6 + 4) + RESET);
        System.out.println();

        this.boardLegend();
    }

    /**
     * Retrieves the number of rows in the board.
     * 
     * @return the number of rows in the board
     */
    public int getRows() {
        return rows;
    }

    /**
     * Retrieves the number of columns in the board.
     * 
     * @return the number of columns in the board
     */
    public int getCols() {
        return cols;
    }

    /**
     * Generates a colored representation of a tile based on its type.
     * Each tile type is associated with a specific color.
     *
     * @param tile The tile for which we want to get the colored representation.
     * @return A string representing the tile with the corresponding color.
     */
    protected String colorStringTile(Tile tile) {
        String tileString;
        if (tile instanceof FieldTile) {
            tileString = YELLOW + tile.toString() + RESET;
        } else if (tile instanceof ForestTile) {
            tileString = GREEN + tile.toString() + RESET;
        } else if (tile instanceof MountainTile) {
            tileString = PURPLE + tile.toString() + RESET;
        } else if (tile instanceof PastureTile) {
            tileString = RED + tile.toString() + RESET;
        } else if (tile instanceof SeaTile) {
            tileString = BLUE + tile.toString() + RESET;
        } else {
            tileString = tile.toString();
        }

        return tileString;
    }

    /**
     * Displays the legend for the tile types on the game board.
     * Each tile type is represented by a symbol and a color,
     * improving readability for players.
     */
    protected void boardLegend() {
        System.out.println();
        System.out.println("\n=== Tile Legend ===");
        System.out.println("Tile : [Type, PlayerInitial, Build]");
        System.out.println("Type : ");
        System.out.println(YELLOW + " F  : FieldTile (Field)" + RESET);
        System.out.println(GREEN + " T  : ForestTile (Forest/Tree)" + RESET);
        System.out.println(PURPLE + " M  : MountainTile (Mountain)" + RESET);
        System.out.println(RED + " P  : PastureTile (Pasture)" + RESET);
        System.out.println(BLUE + " ~~ : SeaTile (Sea/Ocean)" + RESET);
        System.out.println();
    }

    /**
     * Detects and returns a list of islands present on the board.
     * An island is defined as a group of adjacent buildable tiles.
     * 
     * @return A list of islands, where each island is represented as a list of
     *         {@link Position} objects.
     */
    public List<List<Position>> detectIslands() {
        boolean[][] visited = new boolean[rows][cols];
        List<List<Position>> islands = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!visited[i][j] && this.getTile(new Position(i, j))instanceof BuildableTile) {
                    List<Position> island = new ArrayList<>();
                    bfs(i, j, visited, island);
                    islands.add(island);
                }
            }
        }
        return islands;
    }

    /**
     * Performs a breadth-first search (BFS) to explore an island starting from a
     * given position.
     * 
     * @param startX  The x-coordinate of the starting position.
     * @param startY  The y-coordinate of the starting position.
     * @param visited A boolean 2D array to track visited positions.
     * @param island  The list to store positions belonging to the detected island.
     */
    private void bfs(int startX, int startY, boolean[][] visited, List<Position> island) {
        Queue<Position> queue = new LinkedList<>();
        queue.add(new Position(startX, startY));
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            Position pos = queue.poll();
            island.add(pos);
            for (Position neighbor : pos.getPosNeighbours(rows, cols)) {
                int nx = neighbor.getPosX();
                int ny = neighbor.getPosY();
                if (!visited[nx][ny] && this.getTile(new Position(nx, ny)) instanceof BuildableTile) {
                    visited[nx][ny] = true;
                    queue.add(neighbor);
                }
            }
        }
    }

    /**
     * Return the numbers islands of the player
     * 
     * @param player the player
     * @return int number islands
     */
    public int getNbPlayerIsland(Player player) {
        int number = 0;
        List<List<Position>> islands = this.detectIslands();

        for (List<Position> island : islands) {
            for (Position position : island) {
                Tile tile = this.getTile(position);
                if (((BuildableTile) tile).getOwner() == player) {
                    number++;
                    break;
                }

            }
        }

        return number;
    }

    /**
     * return free Tiles on all islands of the board
     * 
     * @return list of free tiles
     */
    public List<BuildableTile> getFreeTiles() {
        List<BuildableTile> tiles = new ArrayList<>();
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < cols; col++) {
                Tile tile = this.getTile(new Position(row, col));
                if (tile instanceof BuildableTile && (((BuildableTile) tile).getOwner() == null)) {
                    tiles.add((BuildableTile) tile);
                }
            }
        }
        return tiles;
    }

    /**
     * return free Tiles of the player
     * 
     * @param player the player
     * @param number the number of buildings we want on each island belonging to the player
     * @return list of free tiles
     */
    public List<BuildableTile> getPlayerFreeTiles(Player player, int number) {
        int count;
        List<BuildableTile> tiles = new ArrayList<>();
        List<List<Position>> islands = this.detectIslands();
        List<List<Position>> islandsOwn = new ArrayList<>();
        
        for (List<Position> island : islands) {
            count = 0;
            for (Position position : island) {
                Tile tile = this.getTile(position);
                if (((BuildableTile) tile).getOwner() == player) {
                    count++;
                }
                if (count >= number) {
                    islandsOwn.add(island);
                    break;       
                }
            }
        }
        for (List<Position> island : islandsOwn) {
            for (Position position : island) {
                Tile tile = this.getTile(position);
                if (((BuildableTile) tile).getOwner() == null) {
                    tiles.add((BuildableTile) tile);
                }
            }
        }
        return tiles;
    }

    public List<BuildableTile> getNeighboorsTiles(Position tilePosition) {
        List<BuildableTile> neighboors = new ArrayList<>();
        for (Position neighbor : tilePosition.getPosNeighbours(rows, cols)) {
            if (neighbor == tilePosition) {
                continue;
            }
            Tile tile = this.getTile(neighbor);
            if (tile instanceof BuildableTile) {
                neighboors.add((BuildableTile) tile);
            }
        }
        return neighboors;
    }

}