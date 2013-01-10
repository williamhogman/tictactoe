package se.whn.tictactoe;

/**
 * The line class represents a sequence of Squares.
 * Oftentimes these are the horizontal, vertical 
 * and diagonal lines of the grid, but necessarily.
 */
public class Line {
    private Square[] squares;

    public Line(Square[] squares) {
	this.squares = squares;
    }

    public Player getOwner() {
	Player owner = null;
	for(Square sq: squares) {
	    Player occupant = sq.getOccupant();
	    if(!sq.isOccupied()) {
		// line not entirely owned
		return null;
	    }

	    if (owner == null) {
		owner = occupant;
	    }

	    if(occupant != owner) {
		// the line is contested
		return null;
	    }
	}
	return owner;
    }


    public int[] getIndicies() {
	int[] indicies = new int[squares.length];
	for(int i = 0; i < squares.length; i++) {
	    indicies[i] = squares[i].getIndex();
	}
	return indicies;
    }
}
