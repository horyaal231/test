package game.action;


import game.board.tile.NotTileException;
import game.board.tile.TileNotBuildableException;
import game.board.tile.TileNotOwnableException;
import game.resource.NonExistentResourceException;

/**
 * Class Action that represents the actions that players can do,
 * also used to verify if the user has the resources to build.
 */
public abstract class Action {

    /**
     * Processes the action.
     * 
     * @throws NonExistentResourceException  if the player does not have the
     *                                       required resources
     * @throws NotProcessableActionException if the action cannot be processed
     * @throws TileNotBuildableException     if the tile is not buildable
     * @throws TileNotBuildableException     if the tile is not buildable
     * @throws TileNotOwnableException       if the tile is not ownable
     * @throws NotTileException 
     */
    public abstract void process()
            throws NonExistentResourceException, NotProcessableActionException, TileNotOwnableException, TileNotBuildableException, NotTileException;

    /**
     * Returns the string representation of the action.
     * 
     * @return the string representation of the action
     */
    public abstract String toString();

    //TODO, il ne faudrait pas remettre le isProcessable ici ? Non, les sont des static independant de l'instance action 
}
