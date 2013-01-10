package se.whn.tictactoe;

/**
 * A player is an entity within the game who performs actions and
 * thereby changes the game state. The player is, in turn controlled
 * by an actor that may or not be human.
 */
public class Player {
    private Actor actor;
    private Game roundPendingIn;
    private char renderChar;

    public Player() {

    }

    public Player(Actor actor) {
	setActor(actor);
    }

    public void setRenderChar(char renderChar) {
	this.renderChar = renderChar;
    }

    private void setActor(Actor actor) {
	this.actor = actor;
	if(roundPendingIn != null) {
	    takeTurn(roundPendingIn);
	    roundPendingIn = null;
	}

    }

    public void takeTurn(Game g) {
	if(actor != null) {
	    actor.takeTurn(g, this);
	} else {
	    roundPendingIn = g;
	}
    }

    public char getRenderChar() {
	return renderChar;
    }

    
    public String toString() {
	return "Player:" + getRenderChar();
    }
}
