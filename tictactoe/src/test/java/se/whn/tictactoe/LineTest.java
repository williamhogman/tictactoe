package se.whn.tictactoe;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
/**
 * Tests for the {@link Line} class.
 */
public class LineTest
{
    private Square mcSquare(int i) {
        return new Square(i);
    }

    private Square mcSquare() {
        return mcSquare(1);
    }

    private Square[] mcSquares(int n) {
        Square[] sqs = new Square[n];
        for(int i = 0; i < n; i++) {
            sqs[i] = mcSquare(i);
        }
        return sqs;
    }

    private Square[] mcSquares() {
        return mcSquares(3);
    }

 
    @Test
    public void testIndicies() {
        Line l  = new Line(mcSquares());

        int[] expected = {0, 1, 2};

        assertArrayEquals("Our test line should have the correct indicies and should have been reordered",
                          expected, l.getIndicies());
    }

    @Test
    public void testGetOwnerNullInitally() {
        Line l = new Line(mcSquares());
        assertNull("No one owns an entirely unonccupied line",
                   l.getOwner());
    }

    @Test
    public void testGetOwnerOneSqOccupiedNull() {
        Player p = new Player();
        Square[] squares = mcSquares();

        squares[0].occupy(p);

        Line l = new Line(squares);

        assertNull("After occupying a single square owner is still null",
                   l.getOwner());
    }

    @Test
    public void testGetOwnerTwoSqOccupiedNull() {
        Player p = new Player();
        Square[] squares = mcSquares();

        squares[0].occupy(p);
        squares[1].occupy(p);

        Line l = new Line(squares);
        
        assertNull("After occupying two squares owner should be null",
                   l.getOwner());
    }

    public void testGetOwnerEndpointOccupiedNull() {
        Player p = new Player();
        Square[] squares = mcSquares();

        squares[0].occupy(p);
        squares[2].occupy(p);

        Line l = new Line(squares);
        
        assertNull("After occupying the two endpoints owner should be null",
                   l.getOwner());
    }


    public void testGetAllThreeOccupied() {
        Player p = new Player();
        Square[] squares = mcSquares();

        squares[0].occupy(p);
        squares[1].occupy(p);
        squares[2].occupy(p);

        Line l = new Line(squares);
        
        assertEquals("After occupying all parts of a line we should have an owner",
                     p, l.getOwner());
        
    }

    @Test
    public void testIsOwnableInitially() {
        Square[] squares = mcSquares();
        Line l =  new Line(squares);

        assertTrue("A line is initially ownable",
                   l.isOwnable());
    }

    @Test
    public void testIsOwnableAfterOnePlayerPlaces() {
        Player p = new Player();
        Square[] squares = mcSquares();

        
        squares[0].occupy(p);

        Line l = new Line(squares);
        assertTrue("A line is ownable if a single player placed something there",
                   l.isOwnable());
    }

    @Test
    public void testIsNotOwnableAfterTwoDiffrentPlayersPlace() {
        Player p1 = new Player();
        Player p2 = new Player();

        Square[] squares = mcSquares();

        squares[0].occupy(p1);
        squares[2].occupy(p2);

        Line l = new Line(squares);

        assertFalse("A line is not ownable if two different players place pieces on it",
                    l.isOwnable());
    }

    
}
