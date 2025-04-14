package demeter.action;

import java.util.List;

import demeter.board.tile.build.ExploitationBuild;
import demeter.board.tile.build.FarmBuild;
import game.action.Action;
import game.action.NotProcessableActionException;
import game.action.ChoseOneTileAction;
import game.board.Board;
import game.board.tile.BuildableTile;
import game.board.tile.TileNotBuildableException;
import game.board.tile.TileNotOwnableException;
import game.player.Player;
import game.player.inventory.Inventory;
import game.resource.NonExistentResourceException;

public class UpgradeFarmAction extends Action {
    private List<BuildableTile> tilesfarm;
    private Player player;

    public UpgradeFarmAction(Board board, Player player) throws TileNotBuildableException {
        this.player = player;
        this.tilesfarm = board.getPlayerTiles(player, FarmBuild.class);
    }
    
    public void process()
            throws NonExistentResourceException, TileNotBuildableException, TileNotOwnableException, NotProcessableActionException {
        
        System.out.println("\nDébut de l'amélioration d'une ferme en exploitation...");
        
        ChoseOneTileAction choseOneTileAction = new ChoseOneTileAction(this.tilesfarm, "Choisissez une ferme à améliorer en exploitation");
        choseOneTileAction.process();
        BuildableTile tilefarm = choseOneTileAction.getselectedTile();
        System.out.println("Ferme sélectionnée en position (" + tilefarm.getPosition().getPosX() + ", " + tilefarm.getPosition().getPosY() + ")");
        System.out.println("Construction de l'exploitation en cours...");

        ExploitationBuild exp = new ExploitationBuild();
        Inventory inv = player.getInventory();
        inv.deleteResources(exp.getRequiredResource());
        System.out.println("Ressources nécessaires retirées de l'inventaire du joueur : " + this.player.getName());
        tilefarm.upgradeBuild(exp);
        
        System.out.println("la ferm été améliorée en exploitation pour le joueur : " + this.player.getName());
    }

    public String toString() {
        return "Upgrade Farm to Exploitation";
    }

    public static boolean isProcessable(Player player, Board board) {
        Inventory inventory = player.getInventory();
        ExploitationBuild exp = new ExploitationBuild();

        if (!inventory.hasResources(exp.getRequiredResource())) {
            return false;
        }

        List<BuildableTile> playerTileswithFarm = board.getPlayerTiles(player, FarmBuild.class);
        if (playerTileswithFarm.isEmpty()) {
            return false;
        }

        return true;
    }

}
