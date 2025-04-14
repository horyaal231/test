package game.board.tile.build;

import game.resource.SheepResource;
import game.resource.WoodResource;

/**
 * PortBuild class - Represents a port build on a tile.
 */
public class PortBuild extends Build {
    /**
     * Constructs an ArmyBuild with the specified name and required resource.
     */
    public PortBuild() {
        super();
        this.requiredRessource.addResource(new WoodResource());
        this.requiredRessource.addResource(new SheepResource(),2);
    }

    /**
     * Returns a string representation of the PortBuild object.
     * 
     * This method is used to represent a PortBuild instance as a single
     * character "P", which can be useful for displaying the object in a
     * game board or other visual representation.
     * 
     * @return the string "P" representing the PortBuild object
     */
    public String toString() {
        return "p";
    }

    public String getFullName() {
        return "port";
    }

}
