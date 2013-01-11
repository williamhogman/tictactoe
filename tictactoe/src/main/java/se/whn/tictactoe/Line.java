package se.whn.tictactoe;

/**
 * The line class represents a sequence of Squares.
 * Oftentimes these are the horizontal, vertical 
 * and diagonal lines of the grid, but necessarily.
 */
public class Line {
    private Square[] squares;

    public Line(Square[] squares) {
        if(squares.length == 0) {
            throw new IllegalArgumentException();
        }
	this.squares = squares;
    }

    public Player getOwner() {
	Player owner = null;
	for(Square sq : squares) {
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

    public boolean isOwnable() {
        Player candidate = null;

        for(Square sq : squares) {
            Player occupant = sq.getOccupant();

            // Unoccupied squares are ignored, because they do not
            // prevent ownership.
            if(!sq.isOccupied()) {
                continue;
            }

            // If we haven't stored a candidate yet store one
            if(candidate == null) {
                candidate = occupant;
            }

            // If someone other than the candidate owns a square it is unownable.
            if(candidate != occupant) {
                return false;
            }
        }
        return true;
    }


    public int[] getIndicies() {
	int[] indicies = new int[squares.length];
	for(int i = 0; i < squares.length; i++) {
	    indicies[i] = squares[i].getIndex();
	}
	return indicies;
    }
}
