package se.whn.tictactoe;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for the {@link Square} class.
 */
@RunWith(JUnit4.class)
public class SquareTest {

    private Square mcSquare() {
	return new Square(1);
    }


    @Test
    public void testEmptyOnCreate() {
	Square sq = mcSquare();
	assertFalse(sq.isOccupied());
    }

    @Test
    public void testOccupy() {
	Square sq = mcSquare();

	Player p = new Player();

	assertTrue(sq.occupy(p));

	assertEquals("Occupant should be player",
		     sq.getOccupant(),
		     p);
    }

    @Test
    public void testOccupyTwice() {
	Square sq = mcSquare();
	Player p = new Player();

	sq.occupy(p);

	// Occupying twice is to return false.
	assertFalse(sq.occupy(p));
    }

    @Test
    public void testViewFromMe() {
	Square sq = mcSquare();
	Player p = new Player();
	
	sq.occupy(p);

	assertEquals("Viewing a square occupied by player should give Me", 
		     sq.viewFrom(p),
		     OccupancyStatus.Me
		     );
    }

    @Test
    public void testViewEmpty() {
	Square sq = mcSquare();
	Player p = new Player();

	// sq defaults to unoccupied

	assertEquals("Viewing empty squares should give empty",
		     sq.viewFrom(p),
		     OccupancyStatus.Empty
		     );
    }

    @Test
    public void testViewFromOther() {
	Square sq = mcSquare();
	Player p1 = new Player();
	Player p2 = new Player();
	sq.occupy(p2);
	assertEquals("Viewing a square occupied by the Opponent gives Opponent", 
		     sq.viewFrom(p1),
		     OccupancyStatus.Opponent
		     );
    }

    @Test
    public void testIndexGetter() {
	Square sq = new Square(1);
	assertEquals("Square has the index 1",
		     sq.getIndex(),
		     1);

	Square sq2 = new Square(2);
	assertEquals("Square has the index 2",
		     sq2.getIndex(),
		     2);
    }


}
