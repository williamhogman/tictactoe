package se.whn.tictactoe;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit tests for {@link Grid}
 */
@RunWith(JUnit4.class)
public class GridTest
{

    private Grid mcGrid() {
	return new Grid();
    }

    private Grid oneSqGrid() {
	return new Grid(1);
    }

    private Player mcPlayer() {
	return new Player();
    }

    @Test
    public void testOccupySquare() {
	Grid g = mcGrid();
    }


    @Test
    public void testNotInitialyFull() {
	Grid g = mcGrid();
	assertFalse(g.isFull());
    }

    @Test
    public void testFilledGrid() {
	Grid g = oneSqGrid();
	g.occupy(0, mcPlayer());
	assertTrue(g.isFull());
    }

    @Test
    public void testGetUnoccupied() {
	Grid g = oneSqGrid();
	
	Square[] s = g.getUnoccupiedSquares();
	assertEquals("A one square gird should have exactly one unoccupied square",
		     1, s.length);
    }

    @Test
    public void testGetUnoccupiedNoSquaresLeft() {	
	Grid g = oneSqGrid();

	g.occupy(0, mcPlayer());
	Square[] s = g.getUnoccupiedSquares();

	assertEquals("Once all availiable squares are occupied there "+
		     "should 0 unoccupied squares", 0, s.length);
    }

    @Test
    public void testOccupy() {
	Grid g = mcGrid();
	assertTrue("Occupying in an empty grid should be successful",
		   g.occupy(0, 0, mcPlayer()));
    }

    @Test
    public void testOccupyFilled() {
	Grid g = mcGrid();
	
	g.occupy(0, 0, mcPlayer());
	
	assertFalse("Occupying an already occupied square should fail",
		    g.occupy(0, 0, mcPlayer()));
    }

    @Test
    public void testOccupyIsSymetrical() {
	Grid g = mcGrid();

	g.occupy(0, 0, mcPlayer());
	
	assertFalse("(0, 0) and 0 should result in the same square being occupied",
		    g.occupy(0, mcPlayer())
		    );
    }

    @Test
    public void testGetSquare() {
	Grid g = mcGrid();

	Square sq = g.getSquare(0);
    }

    @Test
    public void testGetSquareIsSymmetrical() {
	Grid g = mcGrid();

	for(int i = 0; i < 3; i++) {
	    for(int j = 0; j < 3; j++) {
		Square sq1 = g.getSquare(i * 3 + j);
		Square sq2 = g.getSquare(j, i);
		assertEquals("Get square should match coordinates to indexes",
			     sq1, sq2);
	    }
	}
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetSquareOutOfBounds() {
	Grid g = oneSqGrid();
        g.getSquare(1000);
    }

    @Test
    public void testGetVerticalLine() {
	Grid g = mcGrid();
	int[] expected = {0, 3, 6};
	int[] inds = g.getVerticalLine(0).getIndicies();
        assertArrayEquals("The first vertical line should i=(0,3,6)",
                          expected, inds);
    }

    @Test
    public void testGetHorizontalLine() {
	Grid g = mcGrid();
	int[] expected = {0, 1, 2};
	int[] inds = g.getHorizontalLine(0).getIndicies();
        assertArrayEquals("The first horizontal line should be i=0, 1, 2",
                          expected, inds);
    }

    @Test
    public void testGetFirstDiagonal() {
        Grid g = mcGrid();
        int[] expected = {0, 4, 8};
        int[] inds = g.getDiagonal(1).getIndicies();
        assertArrayEquals("The first diagonal line should be i=0, 4, 8",
                          expected, inds);
    }

    @Test
    public void testGetSecondDiagonal() {
        Grid g = mcGrid();
        int[] expected = {2, 4, 6};
        int[] inds = g.getDiagonal(2).getIndicies();
        assertArrayEquals("The second diagonal line should be i=6, 4, 2",
                          expected, inds);
    }

    @Test
    public void testGetHorizontalLines() {
        Grid g = mcGrid();
        Line[] lines = g.getHorizontalLines();

        assertEquals("There are three horizontal lines in a 3x3 grid",
                     3, lines.length);
    }

    @Test
    public void testGetVerticalLines() {
        Grid g = mcGrid();
        Line[] lines = g.getVerticalLines();

        assertEquals("There are three vertical lines in a 3x3 grid",
                     3, lines.length);

    }

    @Test
    public void testGetDiagonalLines() {
        Grid g = mcGrid();
        Line[] lines = g.getDiagonalLines();

        assertEquals("There are exactly two diagonals",
                     2, lines.length);

    }
}
