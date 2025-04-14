package ares.board.tile.build;

import game.resource.OreResource;
import game.resource.WoodResource;

public class CampsBuild extends WarriorsContainingBuild {
    private int nbWariors = 5;

    /**
     * Constructs an ArmyBuild with the specified name and required resource.
     */
    public CampsBuild() {
        super();
        this.setNbWariors(nbWariors);
        this.upgraded = true;
        this.requiredRessource.addResource(new WoodResource(), 2);
        this.requiredRessource.addResource(new OreResource(), 3);
    }

    /**
     * Returns a string representation of the ArmyBuild object.
     * 
     * This method is used to represent a ArmyBuild instance as a single
     * character "A", which can be useful for displaying the object in a
     * game board or other visual representation.
     * 
     * @return the string "P" representing the ArmyBuild object
     */
    public String toString() {
        return "c";
    }

    public String getFullName() {
        return "camps";
    }

}