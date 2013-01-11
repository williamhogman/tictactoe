package se.whn.tictactoe;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit tests for {@link Game}
 */
@RunWith(JUnit4.class)
public class GameTest {

    private MockPlayer mcPlayer() {
	return new MockPlayer();
    }

    private Game mcGame() {
	return new Game(mcPlayer(), mcPlayer());
    }

    @Test
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

    @Test
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

    @Test
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

    @Test
    public void testStartOfRoundAtGameStart() {
	Game g = mcGame();
	
	assertTrue("When the game begins it is the start of a new round",
		   g.isStartOfRound());
    }

    @Test
    public void testRoundInProgress() {
	MockPlayer p1 = mcPlayer();
	MockPlayer p2 = mcPlayer();
	Game g = new Game(p1, p2);

	g.playTurn();

	p1.doPlace(1);
	
	assertFalse("Once a player has made a move we the round has started",
		    g.isStartOfRound());
    }

    @Test
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


    @Test
    public void testReadyInitialy() {
	Game g = mcGame();
	assertTrue("Once the game is created it should be ready",
		   g.ready());
    }

    @Test
    public void testNotReadyWhileTakingTurn() {
	Game g = mcGame();

	g.playTurn();

	assertFalse("When a turn has begun and no piece placed we are not ready",
		    g.ready());
    }

    @Test
    public void testReadyAfterPlaced() {
	MockPlayer p1 = mcPlayer();
	MockPlayer p2 = mcPlayer();
	Game g = new Game(p1, p2);

	g.playTurn();
	p1.doPlace(1);

	assertTrue("Once the player has placed his piece we are ready once again",
		   g.ready());
    }


    private Game mcWonGame(MockPlayer winner) {
        MockPlayer loser = mcPlayer();
        Game g = new Game(winner, loser);

        int[] winnerMoves = {0, 1, 2};
        int[] loserMoves = {6, 7};

        for(int i = 0; i < 3; i++) {
            g.playTurn();
            winner.doPlace(winnerMoves[i]);
            if(i < 2) {
                g.playTurn();
                loser.doPlace(loserMoves[i]);
            }
        }
        return g;
    }

    @Test
    public void testGameIsntOverInitially() {
        Game g = mcGame();

        assertFalse("The game isn't over upon creation",
                    g.isGameOver());
    }

    @Test
    public void testGetWinner() {
        MockPlayer plr = mcPlayer();

        Game g = mcWonGame(plr);

        assertSame("We should get the correct winner",
                   plr, g.getWinner());

    }

    @Test
    public void testGetWinnerNullInitialy() {
        Game g = mcGame();
        assertNull("getWinner is null initially",
                   g.getWinner());
    }

    @Test(expected = RuntimeException.class)
    public void testPlayTurnFailsAfterGameOver() {
        mcWonGame(mcPlayer()).playTurn();
    }


    @Test(expected = RuntimeException.class)
    public void testPlacingNotOurTurnFails() {
        MockPlayer p1 = mcPlayer();
        MockPlayer p2 = mcPlayer();
        Game g = new Game(p1, p2);

        g.playTurn();

        // calling do place should result in the turn being advanced so
        p1.doPlace(0);

        // this should result in an exception
        p1.doPlace(1);
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
        assertNotNull("Calling doPlace cant be done prior to being asked to take a turn",
                      this.game);
	return game.placePiece(this, i);
    }
}
