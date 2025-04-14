package game;

import java.util.ArrayList;
import java.util.List;

import game.action.Action;
import game.action.ChoseOneAction;
import game.board.Board;
import game.board.tile.BuildableTile;
import game.objective.Objective;
import game.player.Player;
import game.player.inventory.Inventory;

/**
 * Game class - Represents a game of the game.
 */
public abstract class Game {
    /**
     * The game board.
     */
    protected Board board;

    /**
     * The actions available to the player.
     */
    protected final List<Class<? extends Action>> actions = new ArrayList<Class<? extends Action>>();

    /**
     * The players participating in the game.
     */
    protected List<Player> players;
    /**
     * The objectives of the game.
     */
    protected final List<Objective> objectives = new ArrayList<Objective>();

    /**
     * The player whose turn it is to play.
     */
    protected Player currentPlayer;



    /**
     * The current round number.
     */
    protected int roundNumber;

    /**
     * Constructs a game with the specified players and board dimensions.
     *
     * @param players the players participating in the game
     * @param boardX  the width of the board
     * @param boardY  the height of the board
     */
    public Game(List<Player> players, int boardX, int boardY) {
        this.board = new Board(boardX, boardY);
        this.players = players;
        this.currentPlayer = this.players.get((int) Math.floor(Math.random() * this.players.size()));
        this.roundNumber = 0;
    }

    

    /**
     * Initializes the game with the given objectives.
     */
    public abstract void initGame();

    /**
     * Starts the game.
     */
    public void startGame(){
        boolean finish = false;
        while (!finish) {
            finish = round();
        }
    
    }


    /**
     * Choose a random Objective
     * 
     * @return the objective choosen
     */
    protected Objective pickObjective() {
        return this.objectives.get((int) Math.floor(Math.random() * this.objectives.size()));

    }

    /**
     * get a player
     * 
     * @return the player
     */
    public List<Player> getPlayers() {
        return this.players;
    }

    /**
     * looks at all the player's tiles and gives the appropriate resources to his
     * tiles
     * 
     * @param player the player who owns his resources
     * @return the updated inventory
     */
    public Inventory harvest(Player player) {
        Inventory harversted = new Inventory();
        List<BuildableTile> tiles = board.getPlayerTiles(player);
        for (BuildableTile tile : tiles) {
            Inventory generatedResources = tile.harvest();
            player.getInventory().addResources(generatedResources);
            harversted.addResources(generatedResources);
        }
        return harversted;
    }

    /**
     * Returns a string representation of the actions available to the player.
     * @return a string representation of the actions
     */
    public String toStringActions() {
        String res = "[";
        int i = 0;

        for (Class<? extends Action> action : actions) {
            if (i > 0) {
                res += ", ";
            }
            res += i + " - " + action.toString();
            i++;
        }

        res += "]";
        return res;
    }

    /**
     * Switches the current player to the next player in the list of players.
     */
    public void switchCurrentPlayer() {
        if (this.players != null && !this.players.isEmpty()) {
            int currentIndex = this.players.indexOf(this.currentPlayer);
            this.currentPlayer = this.players.get((currentIndex + 1) % this.players.size());
        } else {
            System.out.println("Error: Player list is not correctly initialized or is empty.");
        }
    }

    /**
     * Inits the list actions, with all the actions available in the game
     */
    protected abstract void initAction();

    /**
     * Chooses an action based on the given number.
     * @param action the class of the action to be executed
     */
    protected abstract void pickAction(Class<? extends Action> action);
    
    /**
     * Play a round of the game
     */
    public abstract void firstRound();
    /**
     * Play a round of the game
     * @return true if a player has won the game, false otherwise
     */
    public Boolean round() {
        this.roundNumber++;
        /* Board print */
        // System.out.print("\033\143");
        System.out.println("Tour numéro : " + this.roundNumber);

        for (Player player : players) {
            this.board.printBoard();
            System.out.println("Ressources pour le joueur " + player.getName() + " :");
            Inventory inventory = this.harvest(player);
            inventory.print();
            System.out.println();
            System.out.println("Tour du joueur " + player.getName());
            System.out.println("Ressources pour le joueur " + player.getName() + " :");
            player.getInventory().print();
            System.out.println();



            /* chosi une action */
            ChoseOneAction choseActionAc = new ChoseOneAction(this, player, board);
            choseActionAc.process();
            Class<? extends Action> choseAction = choseActionAc.getselectedAction();

            /* envoie faire une fonction qui a un switch case et qui gère chaque action */
            this.pickAction(choseAction);

            if (player.verifObjectif(board)) {
                System.out.println("Le joueur " + player.getName() + " a gagné la partie !");
                return true;
            }

            this.switchCurrentPlayer();
        }
        return false;
    }

    /**
     * Returns all possible actions that can be performed by a player on the current board.
     * This method should be implemented by concrete game classes to define game-specific action rules.
     *
     * @param player the player for whom to get possible actions
     * @param board the current game board
     * @return a list of action classes that can be performed by the player
     */
    public abstract List<Class<? extends Action>> getPossibleActions(Player player, Board board);

    /**
     * Returns all actions available in the game, regardless of whether they are currently possible.
     * This method should be implemented by concrete game classes to define all game actions.
     *
     * @return a list of all action classes in the game
     */
    public abstract List<Class<? extends Action>> getAllActions();


    /**
     * Gets the current game board.
     *
     * @return the game board
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Gets the current player whose turn it is.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

}
