package se.whn.tictactoe;

public class CLIGameRenderer{
    private Game game;
    //private Player perspective;

    public CLIGameRenderer(Game game) {
	this.game = game;
    }

    private void output(String s) {
	System.out.print(s);
    }

    private void newline() {
	output("\n");
    }

    public char renderSquare(Square sq) {
	if(sq.isOccupied()) {
	    return sq.getOccupant().getRenderChar();
	} else {
	    return ' ';
	}
    }

    private String generateGridString() {
	Grid g = game.getGrid();
	
	int side = g.getSide();

	// prealloc side chars + new line per line 
	StringBuilder bld = new StringBuilder((1 + side) * side);
	for(int i = 0; i < side; i++) {
	    for(int j = 0; j < side; j++) {
		Square sq = g.getSquare(j, i);
		bld.append(renderSquare(sq));
	    }
	    bld.append('\n');
	}
	return bld.toString();
    }

    public String generateStateString() {
	switch(game.getGameState()) {
	case Draw:
	    return "The game was a draw";
	case Winner:
	    return "The winner was " + game.getWinner().toString();
	default:
	    return null; // we shouldn't even be called if the game isn't over!
	}
    }

    public void render() {
	output(generateGridString());
	newline();
	if(game.isGameOver()) {
	    output(generateStateString());
	    newline();
	}
    }
}
