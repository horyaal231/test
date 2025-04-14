package game.action;

import java.io.IOException;
import java.util.List;

import game.resource.Resource;
import util.Input;

/**
 * Class to handle resource selection
 */
public class ChoseResource {
    private Resource selectedResource;
    private List<Resource> resources;
    private String text;

    /**
     * Constructor for ChoseResource
     * @param resources The list of Tiles to choose from
     * @param text  The text to display
     */
    public ChoseResource(List<Resource> resources, String text) {
        this.text = text;
        this.resources = resources;
    }

    /**
     * Process the resource selection
     */
    public void process() {
        int res;
        boolean validChoice = false;
        System.out.println(text);
        for (int i = 0; i < this.resources.size(); i++) {
            System.out.printf("%d - %s%n", (i), resources.get(i));
        }

        System.out.println("Choose a number: ");
        while (!validChoice) {
            try {
                res = Input.readInt();
                if (res >= 0 && res < this.resources.size()) {
                    validChoice = true;
                    this.selectedResource = this.resources.get(res);
                } else {
                    System.out.println("Please choose a number between 0 and " + (this.resources.size() - 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            } catch (IOException e) {
                System.out.println("Input error");
            }
        }
    }

    /**
     * Get the selected resource
     * 
     * @return The selected resource
     */
    public Resource getSelectedResource() {
        return this.selectedResource;
    }
}
