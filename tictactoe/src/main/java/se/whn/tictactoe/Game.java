package se.whn.tictactoe;

public class Game {

    private Grid grid;

    private Player[] players;
    private int turn;
    private boolean waiting;
    private GameState state;

    public Game(Player p1, Player p2) {
	grid = new Grid();
	players = new Player[2];
	players[0] = p1;
	players[1] = p2;
	waiting = false;
	state = GameState.Active;
    }

    public Grid getGrid() {
	return grid;
    }

    private Player getActivePlayer() {
	return players[turn];
    }

    private void nextPlayer() {
	//turn = (turn + 1) % players.length;
	int next = turn + 1;

	// if the round is completed?
	if(next >= players.length) {
	    turn = 0;
	} else {
	    turn = next;
	}
    }

    public boolean isStartOfRound() {
	return turn == 0;
    }

    public void playTurn() {
	if(!ready() || isGameOver()) {
            throw new RuntimeException("Can't take a turn if the game is over");
	}

	waiting = true;
	Player plr = getActivePlayer();
	plr.takeTurn(this);	
    }

    public boolean ready() {
	return !waiting;
    }

    public boolean placePiece(Player p, int square) {
	if(getActivePlayer() != p) {
	    throw new RuntimeException("Not our turn");
	}

	if(grid.occupy(square, p)) {
	    waiting = false;
	    updateGameState();
	    nextPlayer();
	    return true;
	}
	return false;
    }

    

    private Player findWinner() {
	for(Line l : grid.getVerticalLines()) {
	    Player owner = l.getOwner();
	    if(owner != null) {
		return owner;
	    }
	}

	for(Line l : grid.getHorizontalLines()) {
	    Player owner = l.getOwner();
	    if(owner != null) {
		return owner;
	    }
	}

	for(Line l : grid.getDiagonalLines()) {
	    Player owner = l.getOwner();
	    if(owner != null) {
		return owner;
	    }
	}

	return null;
    }

    private GameState calculateGameState() {
	Player winner = findWinner();

	if(winner == null) {
	    if(grid.isFull()) {
		return GameState.Draw;
	    } else {
		return GameState.Active;
	    }
	} else {
	    return GameState.Winner;
	}
    }

    private void updateGameState() {
	state = calculateGameState();
    }


    public boolean isGameOver() {
	return state != GameState.Active;
    }

    public GameState getGameState() {
	return state;
    }

    public Player getWinner() {
	if(!isGameOver()) {
	    return null;
	}
	// I gladly trade a few CPU cycles to avoid having to store
	// the winner.
	return findWinner();
    }
}
