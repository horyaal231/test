package demeter.action;

import java.util.ArrayList;
import java.util.List;

import game.action.Action;
import game.action.ChoseOptionsAction;
import game.action.NotProcessableActionException;
import game.player.Player;
import game.player.inventory.Inventory;
import game.resource.NonExistentResourceException;
import game.resource.Resource;
import demeter.DemeterGame;
import demeter.resource.NotEnoughThiefsException;
import demeter.resource.ThiefResource;

public class ExchangeWithAPortAction extends Action {

    private Inventory necessary;
    private List<Resource> exchangeableResources;
    private Resource resourceToExchange;
    private Resource resourceToReceive;
    private Player player;
    private DemeterGame game;

    /**
     * Constructor for the ExchangeThreeAgainstOneAction class.
     * 
     * @param player the player who wants to exchange resources
     */
    public ExchangeWithAPortAction(Player player) {
        this.player = player;
        if (player.getGamecClass() == DemeterGame.class) {
            this.game = (DemeterGame) player.getGame();
        }
    }


    public void process() throws NonExistentResourceException, NotProcessableActionException {
        this.necessary = new Inventory();
        Inventory inventory = this.player.getInventory();
        this.exchangeableResources = new ArrayList<>();
        for (Resource resource : inventory.getResources().keySet()) {
            if (inventory.getResource(resource) >= 2) {
                this.exchangeableResources.add(resource);
            }
        }

        if (exchangeableResources.isEmpty()) {
            throw new NonExistentResourceException("Le joueur n'a pas assez de ressources pour effectuer l'échange.");
        }


        List<String> exchangeableResourcesNameList = new ArrayList<>();
        for (Resource resource : this.exchangeableResources) {
            exchangeableResourcesNameList.add(resource.toString());
        }

        if (this.game != null && this.game.getNbThiefs() <= 0) {
            exchangeableResourcesNameList.removeIf(str -> str.equals(new ThiefResource().toString()));
        }
        
        
        ChoseOptionsAction choseExchangeAction = new ChoseOptionsAction(
                exchangeableResourcesNameList, "Choisissez une ressource à échanger parmi les suivantes :");
        choseExchangeAction.process();
        this.resourceToExchange = this.exchangeableResources.get(choseExchangeAction.getselectedActionNumber());
        this.necessary.addResource(resourceToExchange, 2);

        List<Resource> allResources = Resource.getAllResources(this.player.getGamecClass());

        List<String> allResourcesNames = new ArrayList<>();
        for (Resource resource : allResources) {
            allResourcesNames.add(resource.toString());
        }

        ChoseOptionsAction choseReceiveAction = new ChoseOptionsAction(allResourcesNames,
                "Choisissez une ressource à recevoir parmi les suivantes :");
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
        
        System.out.printf("Vous avez échangé 2 %s contre 1 %s%n", resourceToExchange, resourceToReceive);
    }

    public String toString() {
        return "Exchange with a Port";
    }

    public static boolean isProcessable(Player player) {
        if (!player.hasPort())
            return false;

        Inventory inventory = player.getInventory();
        for (Integer value : inventory.getResources().values()) {
            if (value >= 2) {
                return true;
            }
        }
        return false;

    }
}
