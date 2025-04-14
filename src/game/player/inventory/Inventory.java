package game.player.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.resource.NonExistentResourceException;
import game.resource.Resource;

/**
 * Inventory class - Represents the inventory of a player.
 */
public class Inventory {

    // Amap storing resources and their quantities.
    private Map<Resource, Integer> resources = new HashMap<>();

    /**
     * Constructs an empty Inventory.
     */
    public Inventory() {
    }

    /**
     * Calculates the total number of resources in the inventory.
     *
     * @return the total count of all resources
     */
    public int size() {
        return resources.values().stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Retrieves the map of resources and their quantities.
     *
     * @return a map where keys are resource names and values are quantities
     */
    public Map<Resource, Integer> getResources() {
        return this.resources;
    }

    /**
     * Retrieves a resource and its quantities.
     * 
     * @param resource the resource to get
     * @return the resource quantities
     */
    public int getResource(Resource resource) {
        return resources.getOrDefault(resource, 0);
    }

    /**
     * Checks if the inventory has all the resources in the given inventory.
     * 
     * @param necessary the inventory containing the required resources
     * @return true if all resources are present in sufficient quantity
     */
    public boolean hasResources(Inventory necessary) {
        for (Map.Entry<Resource, Integer> entry : necessary.resources.entrySet()) {
            Resource resource = entry.getKey();
            int quantity = entry.getValue();
            if (!hasResources(resource, quantity)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Vérifie si l'inventaire contient au moins une unité de la ressource spécifiée
     * 
     * @param res la ressource à vérifier
     * @return true si la ressource est présente dans l'inventaire, false sinon
     */
    public boolean hasResource(Resource res) {
        return hasResources(res, 1);
    }

    /**
     * Vérifie si l'inventaire contient au moins la quantité spécifiée d'une ressource donnée
     * 
     * @param res la ressource à vérifier
     * @param number la quantité de ressource
     * @return true si la ressource est présente dans l'inventaire, false sinon
     */
    public boolean hasResources(Resource res, int number) {
        for (Map.Entry<Resource, Integer> entry : resources.entrySet()) {
            if (entry.getKey().getClass() == res.getClass()) {
                return entry.getValue() >= number;
            }
        }
        return false;
    }

    /**
     * Adds a resource to the inventory.
     *
     * @param resource the resource to be added
     */
    public void addResource(Resource resource) {
        addResource(resource, 1);
    }

    /**
     * Adds number resource to the inventory.
     *
     * @param resource the resource to be added
     * @param number   the number of resource
     */
    public void addResource(Resource resource, Integer number) {
        resources.put(resource, resources.getOrDefault(resource, 0) + number);
    }

    /**
     * Adds the resources from another inventory to this inventory.
     *
     * @param inventory the inventory to add resources from
     */
    public void addResources(Inventory inventory) {
        inventory.resources.forEach((resource, quantity) -> 
            addResource(resource, quantity));
    }

    /**
     * Removes a single instance of a resource from the inventory.
     *
     * @param resource the resource to be removed
     * @throws NonExistentResourceException if the resource does not exist in the
     *                                      inventory
     */
    public void deleteResource(Resource resource) throws NonExistentResourceException {
        if (!hasResources(resource, 1)) {
            throw new NonExistentResourceException("Resource not available: " + resource.getName());
        }
        addResource(resource, -1);
    }

    /**
     * Removes the specified quantities of resources from the inventory.
     *
     * @param inventory the inventory specifying which resources and how many to
     *                  remove
     * @throws NonExistentResourceException if any resource does not exist in the
     *                                      required quantity
     */
    public void deleteResources(Inventory inventory) throws NonExistentResourceException {
        if (!this.hasResources(inventory)) {
            throw new NonExistentResourceException("Not enough resources");
        }
        inventory.resources.forEach((resource, quantity) -> {
            try {
                for (int i = 0; i < quantity; i++) {
                    deleteResource(resource);
                }
            } catch (NonExistentResourceException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Compares this inventory to another object for equality.
     *
     * @param o the object to compare with
     * @return true if the other object is an Inventory with the same resources,
     *         false otherwise
     */
    public boolean equals(Object o) {
        if (o instanceof Inventory) {
            Inventory other = (Inventory) o;
            return this.resources.equals(other.getResources());
        }
        return false;
    }

    /**
     * Checks if the inventory contains at least one unit of a specific resource.
     *
     * @param resource the resource to check
     * @return true if the resource is present in the inventory, false otherwise
     */
    public boolean contains(Resource resource) {
        return resources.getOrDefault(resource, 0) > 0;
    }

    /**
     * Checks if the inventory contains at least one unit of a specific resource.
     *
     * @return true if the resource is present in the inventory, false otherwise
     */
    public String toString() {
        List<String> res = new ArrayList<>();
        for (Map.Entry<Resource, Integer> entry : resources.entrySet()) {
            res.add(entry.getKey().getName() + ": " + entry.getValue());
        }
        return res.toString();
    }

    /**
     * Converts the inventory to a list of resource names.
     *
     * @return a list of resource names
     */
    public List<String> toList() {
        List<String> res = new ArrayList<>();
        for (Map.Entry<Resource, Integer> entry : resources.entrySet()) {
            res.add(entry.getKey().getName());
        }
        return res;
    }

    /**
     * prints the inventory
     */
    public void print() {
        List<String> res = new ArrayList<>();
        resources.forEach((resource, quantity) -> 
            res.add(resource.getName() + ": " + quantity));
        System.out.println(res.toString());
    }

    /**
     * Retrieves the list of resources in the inventory.
     *
     * @return the list of resources
     */
    public List<Resource> getResourcesList() {
        return new ArrayList<>(resources.keySet());
    }

}
