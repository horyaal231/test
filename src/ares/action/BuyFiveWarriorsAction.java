package ares.action;

import ares.resource.WarriorResource;
import game.action.Action;
import game.player.Player;
import game.player.inventory.Inventory;
import game.resource.NonExistentResourceException;
import game.resource.OreResource;
import game.resource.WheatResource;
import game.resource.WoodResource;

public class BuyFiveWarriorsAction extends Action {

    private Inventory necessary;
    private Player player;

    /**
     * Constructor for the BuyFiveWarriorsAction class
     * @param player the player who wants to buy the warriors
     */
    public BuyFiveWarriorsAction(Player player) {
        this.player = player;
        this.necessary = new Inventory();
        this.necessary.addResource(new WoodResource(), 2);
        this.necessary.addResource(new WheatResource(), 2);
        this.necessary.addResource(new OreResource());
    }


    /**
     * Processes the buy five warriors action.
     */
    public void process() throws NonExistentResourceException {
        Inventory inventory = this.player.getInventory();
        inventory.deleteResources(this.necessary);
        inventory.addResource(new WarriorResource(), 5);
        System.out.printf("5 soldats ont été ajoutés à l'inventaire du joueur : " + this.player.getName());
    }

    public String toString() {
        return "Buy Five Warriors";
    }

    public static boolean isProcessable(Player player) {
        Inventory inventory = player.getInventory();
        Inventory necessary = new Inventory();
        necessary.addResource(new WoodResource(), 2);
        necessary.addResource(new WheatResource(), 2);
        necessary.addResource(new OreResource());
        if (!inventory.hasResources(necessary)) {
            return false;
        }
        return true;
    }

}
