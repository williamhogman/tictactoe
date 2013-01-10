package se.whn.tictactoe;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class PlayerTest extends TestCase {

    public PlayerTest(String testName) {
	super(testName);
    }

    public static Test suite() {
	return new TestSuite(PlayerTest.class);
    }

    private Player mcPlayer() {
	return new Player();
    }

    private MockActor mcActor() {
	return new MockActor();
    }

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
