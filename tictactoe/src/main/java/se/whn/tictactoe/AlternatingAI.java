package se.whn.tictactoe;

public class AlternatingAI extends Actor{
    private int ctr;
    private Actor[] actors;


    public AlternatingAI(Actor a1, Actor a2) {
	actors = new Actor[2];
	actors[0] = a1;
	actors[1] = a2;
    }


    public int selectMove(Game game, Player plr) {
	int i = ctr % actors.length;
	int  move = actors[i].selectMove(game, plr);
	ctr++;
	return move;
    }
}
