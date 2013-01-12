package se.whn.tictactoe;

import java.util.List;
import java.util.ArrayList;

public class Grid {
    private static final int DEFAULT_SIDE = 3;
    private int side;
    private Square[] squares;
    private Line[] lineCache;
    private boolean allLinesInitialized;
    private int occupied;

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
        occupied = 0;
        lineCache = new Line[side * 2 + 2];
    }

    public Grid() {
	this(DEFAULT_SIDE);
    }

    public Square[] getUnoccupiedSquares() {
        if(occupied == 0) {
            return squares;
        }
        Square[] unocc = new Square[squares.length - occupied];
        int i = 0;
	for(Square sq: squares) {
	    if(!sq.isOccupied()) {
                unocc[i] = sq;
                i++;
	    }
	}
        return unocc;
    }

    public boolean isFull() {
        return occupied == squares.length;
           /*for(Square sq : squares) {
	    if(!sq.isOccupied()) {
		return false;
	    }
            }
	return true;*/
    }

    public boolean occupy(int index, Player plr) {
	boolean res =  getSquare(index).occupy(plr);
        if(res) {
            occupied++;
        }
        return res;
    }


    public boolean occupy(int x, int y, Player plr) {
	return occupy(side * x + y, plr);
    }


    public Square getSquare(int index) {
	return squares[index];
    }

    public Square getSquare(int x, int y) {
        return squares[side * y + x];
    }

    public int getSide() {
	return side;
    }


    public Line getHorizontalLine(int y){
        int cacheno = side + y;
        if(lineCache[cacheno] == null) {
            Square[] line = new Square[side];

            for(int x = 0; x < side; x++) {
                line[x] = getSquare(x, y);
            }
            lineCache[cacheno] = new Line(line);
        }
	return lineCache[cacheno];
    }

    public Line getVerticalLine(int x) {
        if(lineCache[x] == null) {
            Square[] line = new Square[side];

            for(int y = 0; y < side; y++) {
                line[y] = getSquare(x, y);
            }
            lineCache[x] = new Line(line);
        }
        return lineCache[x];
    }

    public Line getDiagonal(int d) {
	/* 
	 * Given a 3by3 grid
	 * The first diagonal is:
	 *  (0,0) (1,1) (2,2)
	 * The second diagonal is:
	 *  (2,0) (1, 1) (0, 2)
	 */
        int cacheno = lineCache.length - d;
        if(lineCache[cacheno] == null) {
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
            lineCache[cacheno] =  new Line(line);
        }
        return lineCache[cacheno];
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
        if(allLinesInitialized) {
            return lineCache;
        }
        Line[] lines = new Line[side * 2 + 2];
        int offset = side;

        for(int i = 0; i < side; i++) {
            lines[i] = getHorizontalLine(i);
            lines[offset + i] = getVerticalLine(i);
        }

        lines[lines.length - 1] = getDiagonal(1);
        lines[lines.length - 2] = getDiagonal(2);
        
        allLinesInitialized = true;
        return lines;
    }
}
