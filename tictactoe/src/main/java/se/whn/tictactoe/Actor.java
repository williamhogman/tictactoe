package se.whn.tictactoe;

/** An actor is an entity that can take a turn as a given player
 */
public abstract class Actor {

    public void takeTurn(Game game, Player plr){
	int square = selectMove(game, plr);
	game.placePiece(plr, square);
    }

    protected int selectMove(Game game, Player plr) {
	return 0;
    }
}
