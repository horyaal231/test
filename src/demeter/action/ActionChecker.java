package demeter.action;

import game.action.Action;
import game.action.BuildPortAction;
import game.action.ExchangeThreeAgainstOneAction;
import game.action.NoneAction;
import game.board.Board;
import game.player.Player;

import java.util.ArrayList;
import java.util.List;

import demeter.DemeterGame;


public class ActionChecker {

    /**
     * Returns a list of possible actions for the player.
     *
     * @param game   The game
     * @param player The player
     * @param board  The board
     * @return A list of possible actions for the player
     */
    public static List<Class<? extends Action>> getPossibleActions(DemeterGame game, Player player, Board board) {
        List<Class<? extends Action>> possibleActions = new ArrayList<>();

        for (Class<? extends Action> actionClass : game.getAllActions()) {
            try {
                if (isActionProcessable(actionClass, player, board, game)) {
                    possibleActions.add(actionClass);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return possibleActions;
    }

    /**
     * Checks if an action is processable.
     *
     * @param actionClass The action class
     * @param player      The player
     * @param board       The board
     * @return True if the action is processable, false otherwise
     */
    private static boolean isActionProcessable(Class<? extends Action> actionClass, Player player, Board board, DemeterGame game) {
        try {
            if (actionClass == BuildPortAction.class) {
                return BuildPortAction.isProcessable(player, board);
            }
            else if (actionClass == ExchangeThreeAgainstOneAction.class) {
                return ExchangeThreeAgainstOneAction.isProcessable(player);
            }
            else if (actionClass == NoneAction.class) {
                return NoneAction.isProcessable();
            }

            else if (actionClass == BuildFarmAction.class) {
                return BuildFarmAction.isProcessable(player,board);
            }
            else if (actionClass == BuyAThiefAction.class) {
                return BuyAThiefAction.isProcessable(player,game);
            }
            else if (actionClass == ExchangeWithAPortAction.class) {
                return ExchangeWithAPortAction.isProcessable(player);
            } 
            else if (actionClass == PlayTheThiefAction.class) {
                return PlayTheThiefAction.isProcessable(player);
            } 
            else if (actionClass == UpgradeFarmAction.class) {
                return UpgradeFarmAction.isProcessable(player, board);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}