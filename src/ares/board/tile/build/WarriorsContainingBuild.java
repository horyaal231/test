package ares.board.tile.build;

import game.board.tile.build.Build;

public abstract class WarriorsContainingBuild extends Build {
    private int nbWariors;


    /**
     * Gets the number of warriors in this army.
     * 
     * @return the nbWarriors
     */
    public int getNbWarriors() {
        return this.nbWariors;
    }

    /**
     * add Warrior in NbWarrior
     * 
     * @param nbWariors the nb warrior to add
     */
    public void addWariors(int nbWariors) {
        this.nbWariors += nbWariors;
    }

    public void setNbWariors(int nbWariors) {
        this.nbWariors = nbWariors;
    }
}

