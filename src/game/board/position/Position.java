package game.board.position;

import java.util.ArrayList;

/**
 * Position class - Represents a position on the board.
 */
public class Position {
    private int positionX;
    private int positionY;
    
    /**
     * Constructs a new Position object with the given x and y coordinates.
     * 
     * @param x the x-coordinate of the position
     * @param y the y-coordinate of the position
     */
    public Position(int x, int y) {
        this.positionX = x;
        this.positionY = y;
    }
    
    /**
     * Gets the x-coordinate of the position.
     * 
     * @return the x-coordinate
     */
    public int getPosX() {
        return this.positionX;
    }

    /**
     * Gets the y-coordinate of the position.
     * 
     * @return the y-coordinate
     */
    public int getPosY() {
        return this.positionY;
    }
    
    /**
     * Returns a list of neighbouring positions.
     * The neighbours are the positions directly above, below, to the left, and to
     * the right.
     * 
     * @return a list of neighbouring positions
     * @param maxX the rows length of the board
     * @param maxY the cols length of the board
     * 
     */
    public ArrayList<Position> getPosNeighbours(int maxX, int maxY) {
        ArrayList<Position> neighbours = new ArrayList<Position>();

        if (this.positionX > 0) {
            neighbours.add(new Position(this.positionX - 1, this.positionY));
        }
        if (this.positionX < maxX - 1) {
            neighbours.add(new Position(this.positionX + 1, this.positionY));
        }
        if (this.positionY > 0) {
            neighbours.add(new Position(this.positionX, this.positionY - 1));
        }
        if (this.positionY < maxY - 1) {
            neighbours.add(new Position(this.positionX, this.positionY + 1));
        }   
        
        return neighbours;
    }

    /**
     * Compares this position to another object for equality.
     * Two positions are equal if their x and y coordinates are the same.
     * 
     * @param obj the object to compare with
     * @return true if the positions are equal, false otherwise
     */
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        if (positionX != other.positionX)
            return false;
        if (positionY != other.positionY)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "(X=" + positionX + ", Y=" + positionY + ")";
    }
}
