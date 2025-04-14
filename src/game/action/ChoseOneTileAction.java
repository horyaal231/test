package game.action;

import java.io.IOException;
import java.util.List;
import util.Input;
import game.board.tile.BuildableTile;
import game.board.tile.TileNotBuildableException;
import game.board.tile.TileNotOwnableException;
import game.resource.NonExistentResourceException;

public class ChoseOneTileAction extends Action {
    private BuildableTile selectedTile;
    private List<BuildableTile> Tiles;
    private String text;

    /**
     * Constructor for the choseOneTileAction class.
     * 
     * @param Tiles The list of Tiles to choose from
     * @param text  The text to display
     */
    public ChoseOneTileAction(List<BuildableTile> Tiles, String text) {
        this.text = text;
        this.Tiles = Tiles;
    }

    public void process() throws NonExistentResourceException, NotProcessableActionException, TileNotOwnableException,
            TileNotBuildableException {
        int res;
        boolean validChoice = false;
                
        System.out.println(text);
        for (int i = 0; i < this.Tiles.size(); i++) {
            System.out.println(i + " : " + this.Tiles.get(i).toString() + this.Tiles.get(i).getPosition().toString());
        }

        System.out.println("Choose a Tile : ");
        while (!validChoice) {
            try {
                res = Input.readInt();

                if (res >= 0 && res < this.Tiles.size()) {
                    validChoice = true;
                    this.selectedTile = this.Tiles.get(res);
                } else {
                    System.out.println("Please choose a number between 0 and " + (this.Tiles.size() - 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            } catch (IOException e) {
                System.out.println("Input error");
            }
        }
    }

    public BuildableTile getselectedTile() {
        return this.selectedTile;
    }

    public String toString() {
        return "Chose one Tile";
    }

}