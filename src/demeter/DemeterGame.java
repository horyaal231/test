package demeter;

import java.util.List;
import game.Game;
import game.board.Board;
import game.board.tile.build.Build;
import game.player.Player;
import game.action.*;
import demeter.action.*;
import demeter.board.tile.build.FarmBuild;
import demeter.objective.PointsObjective;
import demeter.resource.NotEnoughThiefsException;


/**
 * DemeterGame class - Represents a game of the game.
 */
public class DemeterGame extends Game {

    /**
     * Constructor for the DemeterGame class.
     * 
     * @param players the players participating in the game.
     * @param boardX  The width of the board.
     * @param boardY  The height of the board.
     */
    public DemeterGame(List<Player> players, int boardX, int boardY) {
        super(players, boardX, boardY);
        this.objectives.add(new PointsObjective());
        this.initAction();
    }

    public void initGame(){
    
        for (Player player : this.players) {
            player.setObjective(this.pickObjective());
            player.getObjective().getName() ; 
        }
        this.firstRound();
        this.firstRound();
    }

    /**
     * The maximum number of thieves allowed in the game.
     */
    private static final int MAXTHIEF = 10;

    /**
     * The current number of thieves available in the game.
     */
    protected int nbThiefs = MAXTHIEF;
    /**
     * Retrieves the current number of thieves in the game.
     *
     * @return the number of available thieves
     */
    public int getNbThiefs() {
        return this.nbThiefs;
    }

    /**
     * Decreases the number of available thieves by one.
     *
     * @throws NotEnoughThiefsException if there are no thieves left to decrement
     */
    public void decrementeNbThiefs() throws NotEnoughThiefsException {
        if (!(this.nbThiefs > 0)) {
            throw new NotEnoughThiefsException("There is no more thief in the game");
        }
        this.nbThiefs--;
    }

    /**
     * Increases the number of available thieves by one.
     *
     * @throws NotEnoughThiefsException if the maximum number of thieves has been
     *                                   reached
     */
    public void incrementeNbThiefs() throws NotEnoughThiefsException {
        if (!(this.nbThiefs < MAXTHIEF)) {
            throw new NotEnoughThiefsException("There is no more thief in the game");
        }
        this.nbThiefs++;
    }

    /**
     * Initializes the actions of the game.
     */
    protected void initAction() {
        this.actions.add(NoneAction.class);
        this.actions.add(BuildFarmAction.class);
        this.actions.add(BuyAThiefAction.class);
        this.actions.add(ExchangeWithAPortAction.class);
        this.actions.add(PlayTheThiefAction.class);
        this.actions.add(UpgradeFarmAction.class);
        this.actions.add(BuildPortAction.class);
        this.actions.add(ExchangeThreeAgainstOneAction.class);
    }
    

    protected void pickAction(Class<? extends Action> actionClass) {
        try {
            Action action;
            if (actionClass == BuildPortAction.class) {
                action = new BuildPortAction(this.board, this.currentPlayer);
            } else if (actionClass == ExchangeThreeAgainstOneAction.class) {
                action = new ExchangeThreeAgainstOneAction(this.currentPlayer);
            } else if (actionClass == NoneAction.class) {
                action = new NoneAction();
            }

            else if (actionClass == BuildFarmAction.class) {
                action = new BuildFarmAction(this.board, this.currentPlayer);
            } 
            else if (actionClass == BuyAThiefAction.class) {
                action = new BuyAThiefAction(this, this.currentPlayer);
            } 
            else if (actionClass == ExchangeWithAPortAction.class) {
                action = new ExchangeWithAPortAction(this.currentPlayer);
            } 
            else if (actionClass == PlayTheThiefAction.class) {
                action = new PlayTheThiefAction(this.players, this.currentPlayer);
            } 
            else if (actionClass == UpgradeFarmAction.class) {
                action = new UpgradeFarmAction(this.board, this.currentPlayer);
            }
            else {
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
            System.out.println("Player " + player.getName() + " turn");
            
            Build farm = new FarmBuild();
            
            player.getInventory().addResources(farm.getRequiredResource());
        
            BuildFarmAction buildFarm = new BuildFarmAction(this.board, player);
            try {
                buildFarm.process();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
