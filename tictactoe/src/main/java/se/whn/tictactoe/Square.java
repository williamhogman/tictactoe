package se.whn.tictactoe;


public class Square{
    private Player occupant;
    private int index;

    public Square(int index) {
	this.index = index;
    }

    public boolean occupy(Player plr) {
	if(isOccupied()) {
	    return false;
	}
	occupant = plr;
	return true;
    }

    public Player getOccupant() {
	return occupant;
    }

    public boolean isOccupied() {
	return occupant != null;
    }
    
    public OccupancyStatus viewFrom(Player viewer) {
	if(!isOccupied()) {
	    return OccupancyStatus.Empty;
	} else if(viewer == getOccupant()) {
	    return OccupancyStatus.Me;
	} else {
	    return OccupancyStatus.Opponent;
	}
    }


    public int getIndex() {
	return index;
    }

}
