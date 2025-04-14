package game.resource;

import java.util.ArrayList;
import java.util.List;

import ares.AresGame;
import ares.resource.SecretWeaponsResource;
import ares.resource.WarriorResource;
import demeter.DemeterGame;
import demeter.resource.ThiefResource;

/**
 * Resource class - Represents a resource in the game.
 */
public abstract class Resource {
    /**
     * The name of the resource.
     */
    private final String name;

    /**
     * Constructs a resource with the specified name.
     *
     * @param name the name of the resource
     */
    protected Resource(String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of the resource.
     *
     * @return the resource name
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Resource resource = (Resource) obj;
        return name.equals(resource.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Retrieves a list of all resources available in the game.
     *
     * @param gameClass the class of the game (AresGame or DemeterGame)
     * @return a list of all resources
     */
    public static List<Resource> getAllResources(Class<?> gameClass) {
        List<Resource> allResources = new ArrayList<>();

        if (gameClass.equals(AresGame.class)) {
            allResources.add(new WarriorResource());
            allResources.add(new SecretWeaponsResource());
        } else if (gameClass.equals(DemeterGame.class)) {
            allResources.add(new ThiefResource());
        }

        allResources.add(new WheatResource());
        allResources.add(new OreResource());
        allResources.add(new WoodResource());
        allResources.add(new SheepResource());

        return allResources;
    }
}
