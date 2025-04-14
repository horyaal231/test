package game.action;

import java.io.IOException;
import java.util.List;

import util.Input;

//Classe utilisée pour choisir entre plusieurs options, oui ou non, ou une autre liste d'options. 
//Ca va servir pour ( par exemple ) AttackNeighborAction et demander au joueur s'il veut utiliser une arme secrète ou non.
public class ChoseOptionsAction {
    private int selectedActionNumber;
    private List<String> actions;
    private String text;

    /**
     * Constructor for the ChoseOptionsAction class.
     * 
     * @param actions The list of actions to choose from
     * @param text    The text to display
     */
    public ChoseOptionsAction(List<String> actions, String text) {
        this.actions = actions;
        this.text = text;
    }

    public void process() {
        int res;
        boolean validChoice = false;

        System.out.println(text);
        for (int index = 0; index < actions.size(); index++) {
            System.out.println(index + ": " + actions.get(index));
        }

        while (!validChoice) {
            try {
                res = Input.readInt();

                if (res > -1 && res < actions.size()) {
                    validChoice = true;
                    this.selectedActionNumber = res;
                } else {
                    System.out.println("Veuillez choisir un nombre entre 0 et " + (actions.size() - 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide");
            } catch (IOException e) {
                System.out.println("Erreur de saisie");
            }

        }
    }

    /**
     * Gets the selected Action Number.
     * 
     * @return The number of the action
     */
    public int getselectedActionNumber() {
        return this.selectedActionNumber;
    }

    public String toString() {
        return "Chose one action";
    }

    public boolean isProcessable() {
        return true;
    }

}
