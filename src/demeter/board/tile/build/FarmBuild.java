package demeter.board.tile.build;

import game.board.tile.build.Build;
import game.resource.OreResource;
import game.resource.WoodResource;

public class FarmBuild extends Build {
    /**
     * Constructs an ArmyBuild with the specified name and required resource.
     */
    public FarmBuild() {
        super();
        this.requiredRessource.addResource(new WoodResource());
        this.requiredRessource.addResource(new OreResource());
    }

    /**
     * Returns a string representation of the FarmBuild object.
     * 
     * This method is used to represent a FarmBuild instance as a single
     * character "F", which can be useful for displaying the object in a
     * game board or other visual representation.
     * 
     * @return the string "P" representing the FarmBuild object
     */
    public String toString() {
        return "f";
    }

    public String getFullName() {
        return "farm";
    }
}