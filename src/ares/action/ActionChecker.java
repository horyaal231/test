package ares.action;

import game.Game;
import game.action.Action;
import game.action.*;
import game.board.Board;
import game.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * ActionChecker class - Manages and verifies available actions in the game.
 * This class is responsible for determining which actions are possible for a player
 * at any given moment in the game.
 */
public class ActionChecker {

    /**
     * Returns a list of possible actions for the player.
     * Filters through all available actions and returns only those that are currently processable.
     *
     * @param game   The current game instance
     * @param player The player for whom to check actions
     * @param board  The current game board state
     * @return A list of action classes that can be processed by the player
     */
    public static List<Class<? extends Action>> getPossibleActions(Game game, Player player, Board board) {
        List<Class<? extends Action>> possibleActions = new ArrayList<>();

        for (Class<? extends Action> actionClass : game.getAllActions()) {
            try {
                if (isActionProcessable(actionClass, player, board)) {
                    possibleActions.add(actionClass);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return possibleActions;
    }

    /**
     * Checks if an action is processable for a specific player on the current board.
     * Delegates to the specific action class's isProcessable method.
     *
     * @param actionClass The action class to check
     * @param player     The player attempting the action
     * @param board      The current game board state
     * @return true if the action can be processed, false otherwise
     */
    private static boolean isActionProcessable(Class<? extends Action> actionClass, Player player, Board board) {
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

            else if (actionClass == AttackNeighborAction.class) {
                return AttackNeighborAction.isProcessable(player, board);
            }
            else if (actionClass == BuildArmyAction.class) {
                return BuildArmyAction.isProcessable(player, board);
            }
            else if (actionClass == BuyFiveWarriorsAction.class) {
                return BuyFiveWarriorsAction.isProcessable(player);
            }
            else if (actionClass == BuySecretWeaponAction.class) {
                return BuySecretWeaponAction.isProcessable(player);
            }
            else if (actionClass == PlaceOneSoldierAction.class) {
                return PlaceOneSoldierAction.isProcessable(player, board);
            } 
            else if (actionClass == UpgradeArmyAction.class) {
                return UpgradeArmyAction.isProcessable(player, board);
            } 

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}