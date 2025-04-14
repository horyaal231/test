package ares.action;
import java.util.List;

import ares.board.tile.build.ArmyBuild;
import ares.board.tile.build.CampsBuild;
import ares.board.tile.build.WarriorsContainingBuild;
import ares.resource.WarriorResource;
import game.action.Action;
import game.action.NotProcessableActionException;
import game.action.ChoseOneTileAction;
import game.board.Board;
import game.board.position.Position;
import game.board.tile.BuildableTile;
import game.board.tile.Tile;
import game.board.tile.TileNotBuildableException;
import game.board.tile.TileNotOwnableException;
import game.player.Player;
import game.player.inventory.Inventory;
import game.resource.NonExistentResourceException;

/**
 * Cette action permet à un joueur de placer un soldat sur une tuile.
 * La tuile doit être une tuile construisible, sans construction existante.
 * Le placement d'un soldat est conditionné par la présence d'une armée ou d'un
 * camp.
 */
public class PlaceOneSoldierAction extends Action {

    private List<BuildableTile> tiles;
    private Player player;

    /**
     * Constructeur de l'action de placement d'un soldat.
     * 
     * @param board        Le plateau de jeu.
     * @param player       Le joueur effectuant l'action.
     */
    public PlaceOneSoldierAction(Board board, Player player) {
        this.player = player;
        this.tiles = board.getPlayerTiles(player, WarriorsContainingBuild.class);
    }

    /**
     * Traite le placement d'un soldat sur une tuile.
     * Cette action vérifie que la tuile sélectionnée est construisible et
     * contient déjà une armée ou un camps.
     *
     * @throws NonExistentResourceException  Si le joueur n'a pas les ressources
     *                                       nécessaires pour effectuer l'action.
     * @throws NotProcessableActionException Si l'action ne peut pas être traitée
     *                                       (pas de camp ou d'armée à proximité).
     * @throws TileNotOwnableException
     * @throws TileNotBuildableException 
     */
    public void process() throws NonExistentResourceException, NotProcessableActionException, TileNotOwnableException, TileNotBuildableException {
        if (tiles.isEmpty()) {
            throw new NotProcessableActionException("No tile to place a soldier on.");
        }
        ChoseOneTileAction tileSelector = new ChoseOneTileAction(tiles, "Choose a tile to place a soldier");
        tileSelector.process();
        BuildableTile selectedTile = tileSelector.getselectedTile();
        
        if (!(selectedTile instanceof BuildableTile) || !(((BuildableTile) selectedTile).getBuild() instanceof WarriorsContainingBuild)) {
            throw new NotProcessableActionException("This tile does not contain an army or a camp or is not buildable.");
        }
        this.player.getInventory().deleteResource(new WarriorResource());
        WarriorsContainingBuild build = (WarriorsContainingBuild) ((BuildableTile) selectedTile).getBuild();
        build.addWariors(1);
        build.dimension++;
        String buildName = build.getFullName();
        System.out.println("A soldier has been placed on the " + buildName + " for player: " + this.player.getName());
        if ((build.dimension == 6) && (build instanceof ArmyBuild)) {
            build = (CampsBuild) new CampsBuild();
            try {
                ((BuildableTile) selectedTile).setBuild(build, this.player);
                System.out.println("The army has been changed into a camps for player: " + this.player.getName());
            } catch (TileNotBuildableException e) {
            }
        }

    }

    
    public static boolean isProcessable(Player player, Board board) {
        Inventory inventory = player.getInventory();
        if (!inventory.hasResource(new WarriorResource())) {
            return false;
        }

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                Tile tile = board.getTile(new Position(row, col));
                if (tile instanceof BuildableTile) {
                    BuildableTile buildableTile = (BuildableTile) tile;
                    if (buildableTile.getOwner() == player && buildableTile.getBuild() instanceof WarriorsContainingBuild) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public String toString() {
        return "Place One Soldier";
    }

}
