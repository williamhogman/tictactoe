package se.whn.tictactoe;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit tests for grid class
 */
public class GridTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public GridTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( GridTest.class );
    }


    private Grid mcGrid() {
	return new Grid();
    }

    private Grid oneSqGrid() {
	return new Grid(1);
    }

    private Player mcPlayer() {
	return new Player();
    }

    public void testOccupySquare() {
	Grid g = mcGrid();
    }


    public void testNotInitialyFull() {
	Grid g = mcGrid();
	assertFalse(g.isFull());
    }

    public void testFilledGrid() {
	Grid g = oneSqGrid();
	g.occupy(0, mcPlayer());
	assertTrue(g.isFull());
    }

    public void testGetUnoccupied() {
	Grid g = oneSqGrid();
	
	Square[] s = g.getUnoccupiedSquares();
	assertEquals("A one square gird should have exactly one unoccupied square",
		     1, s.length);
    }

    public void testGetUnoccupiedNoSquaresLeft() {	

	Grid g = oneSqGrid();

	g.occupy(0, mcPlayer());
	Square[] s = g.getUnoccupiedSquares();

	assertEquals("Once all availiable squares are occupied there "+
		     "should 0 unoccupied squares", 0, s.length);
    }

    public void testOccupy() {
	Grid g = mcGrid();
	assertTrue("Occupying in an empty grid should be successful",
		   g.occupy(0, 0, mcPlayer()));
    }

    public void testOccupyFilled() {
	Grid g = mcGrid();
	
	g.occupy(0, 0, mcPlayer());
	
	assertFalse("Occupying an already occupied square should fail",
		    g.occupy(0, 0, mcPlayer()));
    }


    public void testOccupyIsSymetrical() {
	Grid g = mcGrid();

	g.occupy(0, 0, mcPlayer());
	
	assertFalse("(0, 0) and 0 should result in the same square being occupied",
		    g.occupy(0, mcPlayer())
		    );
    }


    public void testGetSquare() {
	Grid g = mcGrid();

	Square sq = g.getSquare(0);
    }

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

    public void testGetSquareOutOfBounds() {
	Grid g = oneSqGrid();
	try {
	    g.getSquare(1000);
	} catch(IndexOutOfBoundsException ex) {
	    assertNotNull(ex);
	}
    }

    public void testGetVerticalLine() {
	Grid g = mcGrid();
	int[] expected = {0, 3, 6};
	int[] inds = g.getVerticalLine(1).getIndicies();

	for(int i = 0; i < expected.length; i++) {
	    assertEquals("The indicies have to match with the expected ones",
			 expected[i], inds[i]);
	}
    }

    public void testGetHorizontalLine() {
	Grid g = mcGrid();
	int[] expected = {0, 1 ,2};
	int[] inds = g.getHorizontalLine(1).getIndicies();
	for(int i = 0; i < expected.length; i++) {
	    assertEquals("The indicies have to match with the expect ones",
			 expected[i], inds[i]);
	}
	
    }


}
