package demeter.action;

import demeter.DemeterGame;
import demeter.resource.NotEnoughThiefsException;
import demeter.resource.ThiefResource;
import game.action.Action;
import game.player.Player;
import game.player.inventory.Inventory;
import game.resource.NonExistentResourceException;
import game.resource.OreResource;
import game.resource.WheatResource;
import game.resource.WoodResource;

public class BuyAThiefAction extends Action{

    private Inventory necessary;
    private DemeterGame game;
    private Player player;

    public BuyAThiefAction(DemeterGame game,  Player player) {
        this.player = player;
        this.game = game;
        this.necessary = new Inventory();
        this.necessary.addResource(new OreResource());
        this.necessary.addResource(new WoodResource());
        this.necessary.addResource(new WheatResource());
    }

    public void process() throws NonExistentResourceException {
        Inventory inventory = this.player.getInventory();
        inventory.deleteResources(this.necessary);
        try {
            game.decrementeNbThiefs();
            inventory.addResource(new ThiefResource());
        } catch (NotEnoughThiefsException e) {
            System.out.println("No more thieves available in the game.");
        }
        System.out.printf("Vous avez acheté un voleur et cela a été ajouté a l'inventaire du joueur : " + this.player.getName());
    }

    public String toString() {
        return "Buy a Thief";
    }

    public static boolean isProcessable(Player player, DemeterGame game) {
        Inventory inventory = player.getInventory();
        Inventory necessary = new Inventory();
        necessary.addResource(new OreResource());
        necessary.addResource(new WoodResource());
        necessary.addResource(new WheatResource());
        if (!inventory.hasResources(necessary)){
            return false;
        }
        int nbThiefs = game.getNbThiefs() ;
        if (nbThiefs == 0){
            return false;
        }
        return true;
    }


}