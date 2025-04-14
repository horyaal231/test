package demeter.action;

import java.util.List;

import demeter.resource.ThiefResource;
import game.action.Action;
import game.action.ChoseResource;
import game.player.Player;
import game.resource.NonExistentResourceException;
import game.resource.Resource;

public class PlayTheThiefAction extends Action{

    private List<Player> players;
    private Player player;


    public PlayTheThiefAction(List<Player> players, Player player){ 
        this.player = player;
        this.players = players;
    }


    public void process() throws NonExistentResourceException {
        List<Resource> resources = Resource.getAllResources(this.player.getGamecClass());
        ThiefResource thief = new ThiefResource();
        this.player.getInventory().deleteResource(thief);

        
        ChoseResource resourceChooser = new ChoseResource(resources, "Choose a resource to steal");
        resourceChooser.process();
        Resource resourceToSteal = resourceChooser.getSelectedResource();
        
        for (Player p : players){
            try {
                p.getInventory().deleteResource(resourceToSteal);
                this.player.getInventory().addResource(resourceToSteal);
            } catch (NonExistentResourceException e){}
        }
        System.out.println("Un voleur a été jouer pour le joueur : " + this.player.getName() + " il a volé " + resourceToSteal + " cela a été ajouté à l'inventaire");
    }

    public String toString() {
        return "Play the Thief";
    }

    public static boolean isProcessable(Player player) {
        return player.getInventory().hasResource(new ThiefResource());
    }

}
