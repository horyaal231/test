package game.player;

import game.Game;
import game.board.Board;
import game.objective.Objective;
import game.player.inventory.Inventory;

/**
 * Represents a player in the game.
 */
public class Player {
    private String name;
    private Inventory inventory;
    private Objective objective;
    private Boolean hasPort;
    private Class<? extends Game> game;

    /**
     * Constructs a Player with the specified name.
     * The player starts with an empty inventory and no objective.
     * 
     * @param name the name of the player
     * @param game the game class
     */
    public Player(String name, Class<? extends Game> game) {
        this.name = name;
        this.inventory = new Inventory();
        this.objective = null;
        this.hasPort = false;
        this.game = game;
    }

    /**
     * Gets the player's name.
     * 
     * @return the name of the player
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the player's inventory.
     * 
     * @return the inventory of the player
     */
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     * Gets the player's objective.
     * 
     * @return the objective of the player, or null if none is set
     */
    public Objective getObjective() {
        return this.objective;
    }

    /**
     * Sets the player's objective.
     * 
     * @param objective the objective to assign to the player
     */
    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    /**
     * Checks if the player has a port.
     * 
     * @return true if the player has a port, false otherwise
     */
    public Boolean hasPort() {
        return this.hasPort;
    }

    /**
     * Sets whether the player has a port.
     * 
     * @param hasPort true if the player has a port, false otherwise
     */
    public void setHasPort(boolean hasPort) {
        this.hasPort = hasPort;
    }

    /**
     * Gives the player a port.
     */
    public void givePort() {
        this.hasPort = true;
    }


    /**
     * Returns a string representation of the player.
     * The string is the lowercase first letter of the player's name.
     * 
     * @return the lowercase first letter of the player's name
     */
    public String toString() {
        char n = this.name.charAt(0);
        return String.valueOf(Character.toLowerCase(n));
    }

    /**
     * Retrieves the game instance associated with the player.
     * 
     * @return the game instance
     */
    public Game getGame() {
        try {
            return this.game.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Unable to instantiate game class", e);
        }
    }

    public Class<? extends Game> getGamecClass() {
        return this.game;
    }

    public Boolean verifObjectif(Board board) {
        return this.objective.checkObjective(board, this);
    }
}
