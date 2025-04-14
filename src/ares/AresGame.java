package ares;

import java.util.List;

import ares.action.*;
import ares.board.tile.build.ArmyBuild;
import ares.objective.*;
import ares.resource.WarriorResource;
import game.objective.TileObjective;
import game.action.*;
import game.board.Board;
import game.Game;
import game.player.Player;

public class AresGame extends Game {

    /**
     * The initial number of warriors in the game.
     */
    private static final int INITIAL_WARRIORS = 30;

    /**
     * The current number of warriors available in the game.
     */
    private int nbWarriors = INITIAL_WARRIORS;

    /**
     * Constructor for the AresGame class.
     * 
     * @param players the players participating in the game.
     * @param boardX  The width of the board.
     * @param boardY  The height of the board.
     */
    public AresGame(List<Player> players, int boardX, int boardY) {
        super(players, boardX, boardY);
        this.objectives.add(new WarriorObjective());
        this.objectives.add(new TileObjective());
        this.objectives.add(new IslandObjective());
        this.initAction();
    }

    /**
     * Initializes the game.
     */
    public void initGame() {
    
        for (Player player : this.players) {
            player.setObjective(this.pickObjective());
            player.getInventory().addResource(new WarriorResource(), nbWarriors);
        }
        this.firstRound();
        this.firstRound();
    
    }



    protected void initAction() {
        this.actions.add(NoneAction.class);
        this.actions.add(AttackNeighborAction.class);
        this.actions.add(BuildArmyAction.class);
        this.actions.add(BuyFiveWarriorsAction.class);
        this.actions.add(BuySecretWeaponAction.class);
        this.actions.add(PlaceOneSoldierAction.class);
        this.actions.add(UpgradeArmyAction.class);
        this.actions.add(BuildPortAction.class);
        this.actions.add(ExchangeThreeAgainstOneAction.class);;
    }

    protected void pickAction(Class<? extends Action> actionClass) {
        try {
            Action action;
            switch (actionClass.getSimpleName()) {
                case "NoneAction":
                    action = new NoneAction();
                    break;
                case "AttackNeighborAction":
                    action = new AttackNeighborAction(this.board, this.currentPlayer);
                    break;
                case "BuildArmyAction":
                    action = new BuildArmyAction(this.board, this.currentPlayer);
                    break;
                case "BuyFiveWarriorsAction":
                    action = new BuyFiveWarriorsAction(this.currentPlayer);
                    break;
                case "BuySecretWeaponAction":
                    action = new BuySecretWeaponAction(this.currentPlayer);
                    break;
                case "PlaceOneSoldierAction":
                    action = new PlaceOneSoldierAction(this.board, this.currentPlayer);
                    break;
                case "UpgradeArmyAction":
                    action = new UpgradeArmyAction(this.board, this.currentPlayer);
                    break;
                case "BuildPortAction":
                    action = new BuildPortAction(this.board, this.currentPlayer);
                    break;
                case "ExchangeThreeAgainstOneAction":
                    action = new ExchangeThreeAgainstOneAction(this.currentPlayer);
                    break;
                default:
                    System.out.println("Invalid action. Please choose a valid action.");
                    return;
            }

            action.process();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while processing the action.");
        }
    }


    public List<Class<? extends Action>> getPossibleActions(Player player, Board board) {
        return ActionChecker.getPossibleActions(this, player, board);
    }

    public List<Class<? extends Action>> getAllActions() {
        return this.actions;
    }
    
    public void firstRound() {
        for (Player player : this.players) {
            this.board.printBoard();
            System.out.println("Player " + player.getName() + " turn, time to build your first army !");
            ArmyBuild army = new ArmyBuild();
            player.getInventory().addResources(army.getRequiredResource());

            BuildArmyAction BuildArmy = new BuildArmyAction(this.board, player);
            try {
                BuildArmy.process();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
