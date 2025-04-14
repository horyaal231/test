package game.action;

import game.player.Player;
import game.player.inventory.Inventory;
import game.resource.*;
import java.util.ArrayList;
import java.util.List;

import demeter.DemeterGame;
import demeter.resource.NotEnoughThiefsException;
import demeter.resource.ThiefResource;

/**
 * ExchangeThreeAgainstOneAction class - Represents the action of exchanging
 * three resources for one.
 */
public class ExchangeThreeAgainstOneAction extends Action {

    private Inventory necessary;
    private List<Resource> exchangeableResources;
    private Resource resourceToExchange;
    private Resource resourceToReceive;
    private Player player;
    private DemeterGame game; // Ajout du jeu

    /**
     * Constructor for the ExchangeThreeAgainstOneAction class.
     * 
     * @param player the player who wants to exchange resources
     */
    public ExchangeThreeAgainstOneAction(Player player) {
        this.player = player;
        if (player.getGamecClass() == DemeterGame.class) {
            this.game = (DemeterGame) player.getGame(); // Récupération du jeu
        }
        Inventory inventory = player.getInventory();

        this.exchangeableResources = new ArrayList<>();
        for (Resource resource : inventory.getResources().keySet()) {
            if (inventory.getResource(resource) >= 3) {
                this.exchangeableResources.add(resource);
            }
        } 
        
        if (exchangeableResources.isEmpty()) {
            throw new IllegalArgumentException("Le joueur n'a pas assez de ressources pour effectuer l'échange.");
        }
    }

    /**
     * Processes the action of exchanging three resources for one.
     * 
     * @throws NonExistentResourceException if the player does not have the required resources
     * @throws NotProcessableActionException if the action cannot be processed
     */
    public void process() throws NonExistentResourceException, NotProcessableActionException {
        this.necessary = new Inventory();
        Inventory inventory = this.player.getInventory();

        List<String> exchangeableResourcesNames = new ArrayList<>();
        for (Resource resource : this.exchangeableResources) {
            exchangeableResourcesNames.add(resource.toString());
        }

        if (this.game != null && this.game.getNbThiefs() <= 0) {
            exchangeableResourcesNames.removeIf(str -> str.equals(new ThiefResource().toString()));
        }
        

        ChoseOptionsAction choseExchangeAction = new ChoseOptionsAction(exchangeableResourcesNames, "Choisissez une ressource à échanger parmi les suivantes :");
        choseExchangeAction.process();
        this.resourceToExchange = this.exchangeableResources.get(choseExchangeAction.getselectedActionNumber());
        if (resourceToExchange instanceof ThiefResource && this.player.getInventory().getResource(resourceToExchange) > 10 ) {
            throw new NotProcessableActionException("Vous ne pouvez pas échanger un voleur.");
            
        }
        this.necessary.addResource(resourceToExchange, 3);

        List<Resource> allResources = Resource.getAllResources(this.player.getGamecClass());
        List<String> allResourcesNames = new ArrayList<>();
        for (Resource resource : allResources) {
            allResourcesNames.add(resource.toString());
        }

        ChoseOptionsAction choseReceiveAction = new ChoseOptionsAction(allResourcesNames, "Choisissez une ressource à recevoir parmi les suivantes :");
        choseReceiveAction.process();
        this.resourceToReceive = allResources.get(choseReceiveAction.getselectedActionNumber());

        // Vérification si on reçoit un voleur
        if (this.resourceToReceive instanceof ThiefResource) {
            try {
                this.game.decrementeNbThiefs();
            } catch (NotEnoughThiefsException e) {
                throw new NotProcessableActionException("Il n'y a plus de voleurs disponibles dans le jeu.");
            }
        }

        inventory.deleteResources(this.necessary);
        inventory.addResource(resourceToReceive);
        System.out.printf("Vous avez échangé 3 %s contre 1 %s%n", resourceToExchange, resourceToReceive);
    }

    public String toString() {
        return "Exchange Three Against One";
    }
    public static boolean isProcessable(Player player) {
        Inventory inventory = player.getInventory();
        for (Integer value : inventory.getResources().values()) {
            if (value >= 3) {
                return true;
            }
        }
        return false;
    }
}
