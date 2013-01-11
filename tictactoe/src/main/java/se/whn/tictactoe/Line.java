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

    /**
     * Method returning the player a player who can place a single
     * piece to own the row. If there is no such player then it
     * returns null.
     */
    public Player canPlaceToOwn() {
        if(!isOwnable()) {
            return null;
        }

        int missed = 0;
        Player candidate = null;

        for(Square sq : squares) {
            if(!sq.isOccupied()) {
                missed++;
            } else {
                candidate = sq.getOccupant();
            }
        }

        // if we miss exactly one we can place exactly one to own it.
        if(missed == 1) {
            return candidate;
        } else {
            return null;
        }
    }

    public int[] getIndicies() {
	int[] indicies = new int[squares.length];
	for(int i = 0; i < squares.length; i++) {
	    indicies[i] = squares[i].getIndex();
	}
	return indicies;
    }
}
