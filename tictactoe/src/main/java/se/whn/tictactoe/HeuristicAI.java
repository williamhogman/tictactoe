package se.whn.tictactoe;

import java.util.List;

public class HeuristicAI extends Actor {
    /*
     * All squares are part of atleast one vertical
     * and one horizontal line. (2)
     * 
     * The corners are, in addition to the aforementioned lines part
     * of one diagonal. (3)
     *
     * The middle square is part of both diagonals. (4)
     *
     * Converting them to an ordninal scale yields the following array
     */
    protected static final int[] SQUARE_VALUES = {
        2, 1, 2,
        1, 3, 1,
        2, 1, 2
    };

    protected Square byHeuristicScore(List<Square> squares){
        //Square[] sq = new Square[squares.size()];
        //return byHeuristicScore(squares.toArray(sq));
        /* 
         * Appearently this method is called a lot! 
         * It is better to duplicate the code than to call the other
         * function! 
         */
        Square bestSquare = null;
        int bestValue = 0;
        // Note that this is a heuristic that only works for a 3x3 grid
        for(Square s : squares) {
            int value = SQUARE_VALUES[s.getIndex()];
            if(value > bestValue) {
                bestValue = value;
                bestSquare = s;
            }
        }

        return bestSquare;
    }

    protected Square byHeuristicScore(Square[] squares) {
        Square bestSquare = null;
        int bestValue = 0;
        // Note that this is a heuristic that only works for a 3x3 grid
        for(Square s : squares) {
            int value = SQUARE_VALUES[s.getIndex()];
            if(value > bestValue) {
                bestValue = value;
                bestSquare = s;
            }
        }

        return bestSquare;
    }


    protected int selectMove(Game game, Player plr) {
        Grid g = game.getGrid();
        return byHeuristicScore(g.getUnoccupiedSquares()).getIndex();
    }
}
