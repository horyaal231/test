package ares.action;

import ares.resource.SecretWeaponsResource;
import game.action.Action;
import game.player.Player;
import game.player.inventory.Inventory;
import game.resource.NonExistentResourceException;
import game.resource.OreResource;
import game.resource.WoodResource;

public class BuySecretWeaponAction extends Action {

    private Inventory necessary;
    private Player player;

    /**
     * Constructor for the BuySecretWeaponAction class
     * @param player the player who wants to buy the secret
     */
    public BuySecretWeaponAction(Player player) {
        this.player = player;
        this.necessary = new Inventory();
        this.necessary.addResource(new OreResource());
        this.necessary.addResource(new WoodResource());
    }

    public void process() throws NonExistentResourceException {
        Inventory inventory = this.player.getInventory();
        inventory.deleteResources(this.necessary);
        inventory.addResource(new SecretWeaponsResource());
        System.out.printf("Vous avez acheté une arme secrète et cela a été ajouté a l'inventaire du joueur : "
                + this.player.getName() + "\n");
    }

    public String toString() {
        return "Buy Secret Weapon";
    }
    

    /**
     * Checks if the player has the necessary resources
     * @param player the player who wants to buy the secret
     * @return true if the player has the necessary resources, false otherwise
     */ 
    
    public static boolean isProcessable(Player player) {
        Inventory inventory = player.getInventory();
        Inventory necessary = new Inventory();
        necessary.addResource(new OreResource());
        necessary.addResource(new WoodResource());
        if (!inventory.hasResources(necessary)) {
            return false;
        }
        return true;
    }

}