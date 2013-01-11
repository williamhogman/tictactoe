package se.whn.tictactoe;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for the {@link Player} class.
 */
@RunWith(JUnit4.class)
public class PlayerTest {

    private Player mcPlayer() {
	return new Player();
    }

    private MockActor mcActor() {
	return new MockActor();
    }

    @Test
    public void testTurnTaking() {
	MockActor act = mcActor();
	Player p1 = new Player(act);
	
	// we can pass in null because our MockActor doesn't care
	// about the game at all!
	p1.takeTurn(null);
	assertEquals("takeTurn on the actor should be called when taking a turn",
		    1, act.turns);
    }
}

class MockActor extends Actor {
    public int turns = 0;

    public void takeTurn(Game game, Player plr) {
	selectMove(game, plr);
    }

    protected int selectMove(Game game, Player plr) {
	turns++;
	return 0;
    }
}
