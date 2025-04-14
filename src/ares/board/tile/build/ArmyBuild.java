package ares.board.tile.build;

import game.resource.SheepResource;
import game.resource.WheatResource;
import game.resource.WoodResource;

public class ArmyBuild extends WarriorsContainingBuild {
    /**
     * Constructs an ArmyBuild with the specified name and required resource.
     */
    public ArmyBuild() {
        super();
        this.requiredRessource.addResource(new WoodResource());
        this.requiredRessource.addResource(new WheatResource());
        this.requiredRessource.addResource(new SheepResource());
        this.setNbWariors(0);
    }

    public String getFullName() {
        return "army";
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
        return "a";
    }

}
