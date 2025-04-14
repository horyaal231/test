package ares.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ares.board.tile.build.WarriorsContainingBuild;
import ares.resource.SecretWeaponsResource;
import game.action.Action;
import game.action.ChoseOptionsAction;
import game.action.NotProcessableActionException;
import game.action.ChoseOneTileAction;
import game.board.Board;
import game.board.position.Position;
import game.board.tile.BuildableTile;
import game.board.tile.NotTileException;
import game.board.tile.TileNotBuildableException;
import game.board.tile.TileNotOwnableException;
import game.board.tile.build.Build;
import game.player.Player;
import game.player.inventory.Inventory;
import game.resource.NonExistentResourceException;

public class AttackNeighborAction extends Action {

    public static final List<String> actions = new ArrayList<>();
    static {
        actions.add("Yes");
        actions.add("No");
    }
    private BuildableTile tileWithWhichAttack;
    private BuildableTile tileWantAttack;
    private Board board;
    private static final Random random = new Random();
    private Player attackingPlayer;
    private Player opponentPlayer;

    /**
     * Constructor for the AttackNeighborAction class.
     *
     * @param board  The game board
     * @param player The attacking player
     */
    public AttackNeighborAction(Board board, Player attackingPlayer) {
        this.attackingPlayer = attackingPlayer;
        this.board = board;
    }

    /**
     * Handles an attack between two players on neighboring tiles
     * 
     * @throws NonExistentResourceException  if the target tile is not a neighbor
     * @throws TileNotBuildableException     if the tile cannot have a build
     * @throws TileNotOwnableException       if the tile cannot be owned
     * @throws NotProcessableActionException
     * @throws NotTileException 
     */
    public void process() throws NonExistentResourceException, TileNotBuildableException, TileNotOwnableException,
                      NotProcessableActionException, NotTileException {
        ChoseOneTileAction attackingPlayerTile = new ChoseOneTileAction(
                board.getPlayerTiles(attackingPlayer, WarriorsContainingBuild.class), "Choose a tile where to launch the attack");
        attackingPlayerTile.process();
        this.tileWithWhichAttack = attackingPlayerTile.getselectedTile();
        List<BuildableTile> neighboorsTiles = board.getNeighboorsTiles(tileWithWhichAttack.getPosition());
        neighboorsTiles.removeIf(tile -> !(tile.getBuild() instanceof WarriorsContainingBuild));
       
        if (neighboorsTiles.isEmpty()) {
            throw new NotTileException("You don't have any neighboors tiles with warriors");
        }

        ChoseOneTileAction tileToBeAttacked = new ChoseOneTileAction(neighboorsTiles, "Choose a tile to attack");
        tileToBeAttacked.process();
        this.tileWantAttack = tileToBeAttacked.getselectedTile();
        this.opponentPlayer = tileWantAttack.getOwner();

        validateAttack(tileWithWhichAttack, tileWantAttack, board);

        Build attackerBuild = tileWithWhichAttack.getBuild();
        int attackWarrior = ((WarriorsContainingBuild) attackerBuild).getNbWarriors();
        Build defenderBuild = tileWantAttack.getBuild();
        int defendWarrior = ((WarriorsContainingBuild) defenderBuild).getNbWarriors();

        ChoseOptionsAction choseOptionsAction = new ChoseOptionsAction(actions,
                this.attackingPlayer.getName() + ",do you want to use a secret weapon?");
        choseOptionsAction.process();
        int selectedActionNumber = choseOptionsAction.getselectedActionNumber();
        boolean attackHasSecretWeapon = false;
        boolean defendHasSecretWeapon = false;
        if (selectedActionNumber == 0) {
            attackHasSecretWeapon = useSecretWeapon(this.attackingPlayer);
        }
        choseOptionsAction = new ChoseOptionsAction(actions,
                this.opponentPlayer.getName() + ",do you want to use a secret weapon?");
        choseOptionsAction.process();
        selectedActionNumber = choseOptionsAction.getselectedActionNumber();
        if (selectedActionNumber == 0) {
            defendHasSecretWeapon = useSecretWeapon(this.opponentPlayer);
        }
        int attackRoll = sumRollsDice(attackWarrior, attackHasSecretWeapon);
        int defendRoll = sumRollsDice(defendWarrior, defendHasSecretWeapon);
        System.out.println(
                "Une attaque a lieu entre " + this.attackingPlayer.getName() + " et " + opponentPlayer.getName());
        System.out.println("Lancons les dées pour voir qui gagne");
        System.out.println("L'attaquant " + this.attackingPlayer.getName() + " a " + attackRoll + "points ");
        System.out.println("Le defenseur " + opponentPlayer.getName() + " a " + defendRoll + "points ");
        if (attackRoll > defendRoll) {
            System.out.println("L'attaquant " + this.attackingPlayer.getName() + " a gagné ");
            deleteWarriorOrBuild(tileWantAttack, opponentPlayer);
            System.out.println("");
        } else {
            System.out.println("Le defenseur " + opponentPlayer.getName() + " a gagné ");
            deleteWarriorOrBuild(tileWithWhichAttack, this.attackingPlayer);
            System.out.println("");
        }
    }

    /**
     * Validates whether the attack is possible (neighboring tile and contains an
     * attackable build)
     * 
     * @param tileWithWhichAttack tile from which the attack is launched
     * @param tileWantAttack      tile being attacked
     * @param board               game board
     * @throws NonExistentResourceException if the target tile is not a neighbor
     */
    private void validateAttack(BuildableTile tileWithWhichAttack, BuildableTile tileWantAttack, Board board)
            throws NonExistentResourceException {
        Position tileWantAttackPos = tileWantAttack.getPosition();
        Position tileWithWhichAttackPos = tileWithWhichAttack.getPosition();
        if (!(tileWithWhichAttack.hasBuild() && isMilitaryBuild(tileWithWhichAttack.getBuild()))
                || !(tileWantAttack.hasBuild() && isMilitaryBuild(tileWantAttack.getBuild()))) {
            throw new NonExistentResourceException("Both tiles must have a military build to attack.");
        }
        if (!tileWithWhichAttackPos.getPosNeighbours(board.getRows(), board.getCols()).contains(tileWantAttackPos)) {
            throw new NonExistentResourceException("You can't attack this tile because it is not a neighbor.");
        }
    }

    /**
     * Checks if a build is either a camp or an army.
     *
     * @param build The build to verify
     * @return true if the build is a camp or an army, false otherwise
     */
    private boolean isMilitaryBuild(Build build) {
        return build instanceof WarriorsContainingBuild;
    }

    /**
     * Rolls dice based on the number of warriors and the presence of a secret
     * weapon.
     *
     * @param nbWarrior       The number of warriors
     * @param hasSecretWeapon Whether the player has a secret weapon
     * @return The sum of the dice rolls
     */
    private static int sumRollsDice(int nbWarrior, boolean hasSecretWeapon) {
        int nbDice = 0;
        if (nbWarrior <= 3) {
            nbDice = 1;
        } else if (nbWarrior <= 7) {
            nbDice = 2;
        } else {
            nbDice = 3;
        }
        if (hasSecretWeapon) {
            nbDice++;
        }
        int res = 0;
        for (int i = 0; i < nbDice; i++) {
            res += (random.nextInt(6) + 1);
        }
        return res;
    }

    /**
     * Checks if a player has a secret weapon and consumes it if available
     * 
     * @param player the player checked
     * @return boolean if a player has a secret weapon and consumes it if available
     * @throws NonExistentResourceException if a plauer has ressource
     */
    private boolean useSecretWeapon(Player player) throws NonExistentResourceException {
        Inventory inventory = player.getInventory();
        try {
            inventory.deleteResource(new SecretWeaponsResource());
            return true;
        } catch (NonExistentResourceException e) {
            return false;
        }
    }

    /**
     * Removes a warrior or destroys the build if no warriors are left.
     *
     * @param tile   The tile whose warrior or build is to be deleted
     * @param player The player owning the build
     * @throws TileNotBuildableException if the tile cannot have a build
     * @throws TileNotOwnableException   if the tile cannot be owned
     */
    private static void deleteWarriorOrBuild(BuildableTile tile, Player player)
            throws TileNotBuildableException, TileNotOwnableException {
        Build build = tile.getBuild();
        if (!(build instanceof WarriorsContainingBuild)) {
            throw new TileNotBuildableException("The build must be an army to have warriors.");
        }
        ((WarriorsContainingBuild) build).addWariors(-1);
        if (((WarriorsContainingBuild) build).getNbWarriors() <= 0) {
            tile.resetTile();
        }
    }

    public String toString() {
        return "Attack a Neighbor";
    }

    /**
     * Checks if an action is processable for a specific player on the current board.
     *
     * @param player The player attempting the action
     * @param board  The current game board state
     * @return true if the action can be processed, false otherwise
     */
    public static boolean isProcessable(Player player, Board board) {
        List<BuildableTile> playerTiles = board.getPlayerTiles(player);
        for (BuildableTile tile : playerTiles) {
            if (tile.getBuild() instanceof WarriorsContainingBuild) {
                List<BuildableTile> neighborTiles = board.getNeighboorsTiles(tile.getPosition());
                for (BuildableTile neighborTile : neighborTiles) {
                    if (neighborTile.getBuild() instanceof WarriorsContainingBuild) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
