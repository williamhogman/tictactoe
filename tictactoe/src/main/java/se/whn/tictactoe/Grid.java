package se.whn.tictactoe;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Grid {
    private static final int DEFAULT_SIDE = 3;
    private int side;
    private Square[] squares;
    private Line[] lineCache;
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
        makeLines();
        occupied = 0;
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

    private void makeLines() {
        lineCache = new Line[side * 2 + 2];
        for(int i = 0; i < side; i++) {
            lineCache[i] = makeVerticalLine(i);
            lineCache[side + i] = makeHorizontalLine(i);
        }
        lineCache[lineCache.length - 1] = makeDiagonal(1);
        lineCache[lineCache.length - 2] = makeDiagonal(2);
    }

    private Line makeHorizontalLine(int y){
        Square[] line = new Square[side];
        for(int x = 0; x < side; x++) {
            line[x] = getSquare(x, y);
        }
        return new Line(line);
    }

    private Line makeVerticalLine(int x) {
        Square[] line = new Square[side];

        for(int y = 0; y < side; y++) {
            line[y] = getSquare(x, y);
        }
        return new Line(line);
    }

    private Line makeDiagonal(int d) {
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


    public Line getHorizontalLine(int i) {
        return lineCache[side + i];
    }

    public Line getVerticalLine(int i) {
        return lineCache[i];
    }

    public Line getDiagonal(int i) {
        return lineCache[lineCache.length - i];
    }

    public Line[] getVerticalLines() {
        return Arrays.copyOfRange(lineCache, 0, side);
    }

    public Line[] getHorizontalLines() {
        return Arrays.copyOfRange(lineCache, side, (side * 2));
    }

    public Line[] getDiagonalLines() {
        return Arrays.copyOfRange(lineCache, lineCache.length - 2, lineCache.length);
    }

    public Line[] getAllLines() {
        return lineCache;
    }

    public double[] generateMLVars(Player plr) {
        double[] out = new double[squares.length];
        for(int i = 0; i < out.length; i++) {
            Player occ = squares[i].getOccupant();
            if(occ == plr) {
                out[i] = 1.0;
            } else if(occ == null) {
                out[i] = 0.0;
            } else {
                out[i] = -1.0;
            }

        }
        return out;
    }
}
