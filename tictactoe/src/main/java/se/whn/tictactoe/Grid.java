package se.whn.tictactoe;

import java.util.List;
import java.util.ArrayList;

public class Grid {
    private static final int DEFAULT_SIDE = 3;
    private int side;
    private Square[] squares;

    private void makeGrid() {
	// Allocate a side^2 array of squares
	int n = side*side;
	squares = new Square[n];
	for(int i = 0; i < n; i++) {
	    squares[i] = new Square(i);
	}
    }

    public Grid(int side) {
	this.side = side;
	makeGrid();
    }

    public Grid() {
	this(DEFAULT_SIDE);
    }

    public Square[] getUnoccupiedSquares() {
	List<Square> unocc = new ArrayList<Square>();
	for(Square sq: squares) {
	    if(!sq.isOccupied()) {
		unocc.add(sq);
	    }
	}
	Square[] rtn = new Square[unocc.size()];
	return unocc.toArray(rtn);
    }

    public boolean isFull() {
	for(Square sq : squares) {
	    if(!sq.isOccupied()) {
		return false;
	    }
	}
	return true;
    }

    private int coordsToIndex(int x, int y) {
	return side * y + x;
    }

    public boolean occupy(int index, Player plr) {
	return getSquare(index).occupy(plr);
    }


    public boolean occupy(int x, int y, Player plr) {
	return occupy(coordsToIndex(x, y), plr);
    }


    public Square getSquare(int index) {
	// TOOD: use a goddamn custom exception
	if(index < 0 && index >= squares.length) {
	    throw new IndexOutOfBoundsException("Square not in range");
	}
	return squares[index];
    }

    public Square getSquare(int x, int y) {
	return getSquare(coordsToIndex(x, y));
    }

    public int getSide() {
	return side;
    }


    public Line getHorizontalLine(int y){
	Square[] line = new Square[side];

	for(int x = 0; x < side; x++) {
	    line[x] = getSquare(x, y);
	}

	return new Line(line);
    }

    public Line getVerticalLine(int x) {
	Square[] line = new Square[side];

	for(int y = 0; y < side; y++) {
	    line[y] = getSquare(x, y);
	}

	return new Line(line);
    }

    public Line getDiagonal(int d) {
	/* 
	 * Given a 3by3 grid
	 * The first diagonal is:
	 *  (0,0) (1,1) (2,2)
	 * The second diagonal is:
	 *  (2,0) (1, 1) (0, 2)
	 */
	Square[] line = new Square[side];

	if (d == 1) {
	    for(int i = 0; i < side; i++) {
		line[i] = getSquare(i, i);
	    }
	} else if(d == 2) {
            int offset = side - 1;
	    for(int i = 0; i < side; i++) {
		line[i] = getSquare(offset - i, i);
	    }
	}
	return new Line(line);
    }

    public Line[] getVerticalLines() {
	Line[] lines = new Line[side];
	for(int i = 0; i < side; i++) {
	    lines[i] = getVerticalLine(i);
	}
	return lines;
    }

    public Line[] getHorizontalLines() {
	Line[] lines = new Line[side];
	for(int i = 0; i < side; i++) {
	    lines[i] = getHorizontalLine(i);
	}
	return lines;	
    }

    public Line[] getDiagonalLines() {
	Line[] lines = { getDiagonal(1), getDiagonal(2) };
	return lines;
    }

    public Line[] getAllLines() {
        Line[] lines = new Line[side * 2 + 2];
        int offset = side;

        for(int i = 0; i < side; i++) {
            lines[i] = getHorizontalLine(i);
            lines[offset + i] = getVerticalLine(i);
        }

        lines[lines.length - 1] = getDiagonal(1);
        lines[lines.length - 2] = getDiagonal(2);
        
        return lines;
    }
}
