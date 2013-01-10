package se.whn.tictactoe;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class GameTest extends TestCase {

    public GameTest(String testName) {
	super(testName);
    }


    public static Test suite() {
	return new TestSuite(GameTest.class);
    }

    private MockPlayer mcPlayer() {
	return new MockPlayer();
    }

    private Game mcGame() {
	return new Game(mcPlayer(), mcPlayer());
    }

    public void testPlayRoundCallsPlayer() {
	MockPlayer p1 = mcPlayer();
	MockPlayer p2 = mcPlayer();
	Game g = new Game(p1, p2);

	g.playTurn();

	assertEquals("Player should be called by game",
		     1, p1.turns);
		     
	assertEquals("Player called with game",
		     g, p1.game);

    }


    public void testPlayTwoRounds() {
	MockPlayer p1 = mcPlayer();
	MockPlayer p2 = mcPlayer();
	Game g = new Game(p1, p2);

	g.playTurn();

	//once player one has made this place ment P2 should be called
	p1.doPlace(1);

	g.playTurn();

	assertEquals("Player one should have played a single round",
		     1, p1.turns);
	assertEquals("Player two should have played a single round",
		     1, p2.turns);
    }

    public void testPlayTwoRoundsNoDuplicates() {
	MockPlayer p1 = mcPlayer();
	MockPlayer p2 = mcPlayer();
	Game g = new Game(p1, p2);

	g.playTurn();

	p1.doPlace(1);

	g.playTurn();
	
	p2.doPlace(1);

	assertFalse("If we have placed in the same square it is still our turn",
		   g.isStartOfRound());
    }


    public void testStartOfRoundAtGameStart() {
	Game g = mcGame();
	
	assertTrue("When the game begins it is the start of a new round",
		   g.isStartOfRound());
    }

    public void testRoundInProgress() {
	MockPlayer p1 = mcPlayer();
	MockPlayer p2 = mcPlayer();
	Game g = new Game(p1, p2);

	g.playTurn();

	p1.doPlace(1);
	
	assertFalse("Once a player has made a move we the round has started",
		    g.isStartOfRound());
    }

    
    public void testAfterTwoMovesStartNewRound() {
	MockPlayer p1 = mcPlayer();
	MockPlayer p2 = mcPlayer();
	Game g = new Game(p1, p2);

	g.playTurn();
	p1.doPlace(1);

	g.playTurn();
	p2.doPlace(2);

	assertTrue("Once both players have moved we are at the start of a round",
		   g.isStartOfRound());
    }


    public void testReadyInitialy() {
	Game g = mcGame();
	assertTrue("Once the game is created it should be ready",
		   g.ready());
    }

    public void testNotReadyWhileTakingTurn() {
	Game g = mcGame();

	g.playTurn();

	assertFalse("When a turn has begun and no piece placed we are not ready",
		    g.ready());
    }

    public void testReadyAfterPlaced() {
	MockPlayer p1 = mcPlayer();
	MockPlayer p2 = mcPlayer();
	Game g = new Game(p1, p2);

	g.playTurn();
	p1.doPlace(1);

	assertTrue("Once the player has placed his piece we are ready once again",
		   g.ready());
    }

}

class MockPlayer extends Player {
    public int turns = 0;
    public Game game;

    public void takeTurn(Game game) {
	turns += 1;
	this.game = game;
    }

    public boolean doPlace(int i) {
	return game.placePiece(this, i);
    }
}
