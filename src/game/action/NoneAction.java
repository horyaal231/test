package game.action;

import game.resource.NonExistentResourceException;

/**
 * NoneAction class - Represents the none action.
 */
public class NoneAction extends Action {

    /**
     * Processes the none action.
     * 
     * @throws NonExistentResourceException If the resource does not exist.
     */
    public void process() {
        return;
    }

    /**
     * Returns a string representation of the NoneAction class.
     * 
     * @return String representation of the NoneAction class.
     */
    public String toString() {
        return "None";
    }

    /**
     * Returns whether the action is processable.
     * 
     * @return True if the action is processable, false otherwise.
     */
    public static boolean isProcessable() {
        return true;
    }

}
