package game.action;

import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.board.Board;
import game.player.Player;

/**
 * Represents an action where the player selects an action from a list of
 * actions.
 */
public class ChoseOneAction extends Action {
    private Class<? extends Action> selectedAction;
    private List<Class<? extends Action>> actions;

    /**
     * Constructor for the choseOneAction class.
     * 
     * @param game    the game instance
     * @param player  the player making the choice
     * @param board   the game board
     */
    public ChoseOneAction(Game game, Player player, Board board) {
        this.actions = game.getPossibleActions(player, board);
        this.selectedAction = null;
    }

    /**
     * Asks the player for tile coordinates and retrieves the corresponding tile
     * from the board.
     */
    public void process(){

        List<String> StringActions = new ArrayList<String>();
        for (Class<? extends Action> action : actions) {
            StringActions.add(action.getSimpleName());
        }
        ChoseOptionsAction choseOptionsAction = new ChoseOptionsAction(StringActions, "Choisissez une action : ");
        choseOptionsAction.process();
        int selectedActionNumber = choseOptionsAction.getselectedActionNumber();
        this.selectedAction = actions.get(selectedActionNumber);
    }

    /**
     * Returns the selected action.
     * 
     * @return The selected action
     */
    public Class<? extends Action> getselectedAction() {
        return this.selectedAction;
    }

    public String toString() {
        return "Chose one action";
    }

}
