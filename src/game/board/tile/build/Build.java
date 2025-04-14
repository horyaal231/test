package game.board.tile.build;

import game.board.tile.TileNotBuildableException;
import game.board.tile.TileNotUpgradableException;
import game.player.Player;
import game.player.inventory.Inventory;

/**
 * Represents a Build on the game board
 */
public abstract class Build {
    protected boolean upgraded = false;
    protected Inventory requiredRessource;
    public int dimension = 1;

    /**
     * Constructs a Build with the specified name and required resource.
     */
    public Build() {
        this.requiredRessource = new Inventory();
    }

    /**
     * Gets the required ressource for the build.
     * 
     * @return the required ressource(s)
     */
    public Inventory getRequiredResource() {
        return this.requiredRessource;
    }

    /**
     * Gets the upgraded status of the build.
     * 
     * @return true if the build is upgraded, false otherwise
     */
    public boolean isUpgraded() {
        return this.upgraded;
    }

    /**
     * Return true if the build can be constructed by p1. The specific build
     * behavior
     * 
     * @param p1 the player want build
     * @throws TileNotBuildableException if the tile is not buildable
     * @return true if the player can construct this build
     */
    public boolean canConstruct(Player p1) throws TileNotBuildableException {
        boolean hadRessource = p1.getInventory().hasResources(this.requiredRessource);
        if (!hadRessource)
            throw new TileNotBuildableException("Not buldable because no ressource");
        else {
            return true;
        }

    }

    /**
     * Returns a the full name of the object.
     * 
     * 
     * @return the string representing the object.
     */
    public abstract String getFullName();

    /**
     * Return true if the build can be upgraded by p1. The specific build behavior
     * 
     * @param p1 the player want build
     * @throws TileNotUpgradableException if the tile is not buildable
     * @return true if the player can upgrade this tile
     */
    public boolean canUpgrade(Player p1) throws TileNotUpgradableException {
        if (this.isUpgraded()) {
            throw new TileNotUpgradableException("Not upgrade because already upgraded");
        }
        boolean hadRessource = p1.getInventory().hasResources(this.requiredRessource);
        if (!hadRessource)
            throw new TileNotUpgradableException("Not upgrade because no ressource");
        else {
            return true;
        }
    }

    public abstract String toString();

}
